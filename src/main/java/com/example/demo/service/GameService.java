package com.example.demo.service;

import com.example.demo.dto.GameStatusResponse;
import com.example.demo.model.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
    private final Map<String, GameRoom> rooms = new ConcurrentHashMap<>(); //Bu sayede binlerce oda olsa bile ID üzerinden ilgili odayı saniyeler içinde bulabiliriz.

    public String joinOrCreateRoom(String roomId, String playerId, String playerName) {  //Bu metot, bir oyuncu "Oyuna Katıl" dediğinde çalışır.
        GameRoom room = rooms.computeIfAbsent(roomId, id -> { 
            GameRoom newRoom = new GameRoom();
            newRoom.setRoomId(id);
            return newRoom;
        });  

        if (room.getPlayers().size() < 2) { //Eğer oyunda iki kullanıcıdana z kullanıcı varsa kullanıcıyı oyuna alır.
            room.addPlayer(new Player(playerId, playerName));
            if (room.getPlayers().size() == 2) setupGame(roomId); 
            return roomId;
        }
        return null;
    }

    public void setupGame(String roomId) { //Oyun başladığında yapılan hazırlık aşaması (deste ile alakalı)
        GameRoom room = rooms.get(roomId);
        if (room == null) return;
        room.setDeck(new Deck()); 
        
        for (Player p : room.getPlayers()) { // Desteden 7'şer kart verir.
            p.getHand().clear(); //Her oyuncunun elini temizler.
            for (int i = 0; i < 7; i++) p.getHand().add(room.getDeck().drawCard());
        }
        
        Card firstCard = room.getDeck().drawCard();   //Yere bir kart açar.
        while(firstCard.getColor() == Color.WILD) firstCard = room.getDeck().drawCard(); //Eğer yere açılan ilk kart bir Joker (WILD) ise, "geçersiz" sayıp düz bir kart gelene kadar desteden yeni kart çeker.
        room.setTopCard(firstCard);
        room.setCurrentActiveColor(firstCard.getColor());
        room.setGameStarted(true); //Oyun başladı durumunu true yapar.
    }


    // playCard metodu: Önce kural kontrol ediliyor, sonra kart elden çıkarılıyır, en son sırayı devrediliyor

    public boolean playCard(String roomId, String playerId, Card card, Color chosenColor) {
        GameRoom room = rooms.get(roomId);
        if (room == null || !room.isGameStarted()) return false; //eğer room yoksa ya da oyun başlatılmadıysa bu metot false döndürür.

        Player currentPlayer = room.getPlayers().get(room.getCurrentPlayerIndex()); //Kartı atan kişi, gerçekten o an sırası olan kişi mi?
        if (!currentPlayer.getId().equals(playerId)) return false; //eğer ID ler uyuşmuyorsa kart atamaz.

        // Kartın geçerlilik kontrolü (Yerdeki aktif renge göre)
        if (!card.canPlayOn(room.getTopCard()) && card.getColor() != room.getCurrentActiveColor() && card.getColor() != Color.WILD) {
             // Eğer özel bir canPlayOn mantığın yoksa rengi de kontrol etmelisin
        }

        room.setTopCard(card);
        //Eğer oyuncu Joker attıysa ve bir renk seçtiyse, oyunun aktif rengini o seçilen renk yapar.
        if (card instanceof WildCard && chosenColor != null) {
            room.setCurrentActiveColor(chosenColor);
        } else {
            room.setCurrentActiveColor(card.getColor());
        }

        currentPlayer.getHand().removeIf(c -> c.getColor() == card.getColor() && c.getValue() == card.getValue()); //Kartı oyuncunun listesinden (hand) siler.
        card.applyEffect(this, roomId); //Kartın özel gücü (+2, Pas vb.) varsa applyEffect ile tetikler.

        // Sıra Yönetimi
        if (!(card instanceof WildCard && card.getValue() == 13) && !(card instanceof ActionCard && card.getValue() == 10)) { //eğer kart skip ya da wild kart değilse sıra karşıya geçer.
            moveToNextPlayer(roomId);
        }

        if (currentPlayer.getHand().isEmpty()) room.setGameStarted(false); 
        return true;
    }
    //Bu metot her oyuncuya "farklı" bir görünüm sunar. Örneğin, sen sadece kendi elini görebilirsin ama yerdeki kartı herkes ortak görür.
    public GameStatusResponse getGameState(String roomId, String playerId) {
        GameRoom room = rooms.get(roomId);
        if (room == null) return null;

        Player viewer = room.getPlayers().stream().filter(p -> p.getId().equals(playerId)).findFirst().orElse(null); 
        Player currentTurnPlayer = room.getPlayers().get(room.getCurrentPlayerIndex());

        String status = !room.isGameStarted() ? (room.getPlayers().size() < 2 ? "BEKLENİYOR" : "TAMAMLANDI") : "AKTİF";

        return GameStatusResponse.builder()
                .currentPlayerId(currentTurnPlayer.getId())
                .currentPlayerName(currentTurnPlayer.getName())
                .topCard(room.getTopCard())
                .activeColor(room.getCurrentActiveColor())
                .playerHand(viewer != null ? viewer.getHand() : new ArrayList<>())
                .statusMessage(status)
                .build();
    }

    //Oyuncunun yerdeki kapalı desteden kart çekme eylemidir.
    public void drawCardForPlayer(String roomId, String playerId) {
        GameRoom room = rooms.get(roomId);
        if (room == null || !room.isGameStarted()) return;
        Player current = room.getPlayers().get(room.getCurrentPlayerIndex());
        if (current.getId().equals(playerId)) { //Kart çekmek isteyen kişi, gerçekten sırası olan kişi mi?
            current.getHand().add(room.getDeck().drawCard()); //Eğer sıra ondaysa, room.getDeck().drawCard() ile destenin en üstündeki kartı alır ve oyuncunun hand listesine ekler.
            moveToNextPlayer(roomId);
        }
    }

    //Sıra karşıdaki oyuncuya geçiyor.
    public void moveToNextPlayer(String roomId) { 
        GameRoom room = rooms.get(roomId);
        if (room != null) room.setCurrentPlayerIndex((room.getCurrentPlayerIndex() + 1) % room.getPlayers().size()); //mod aldık. Eğer 1. oyuncuysa 0, 2. oyuncuysa 1 değeri döner.
    }

    public void skipTurn(String roomId) { moveToNextPlayer(roomId); }

    //Bu metot "kurban" oyuncuyu bulur ve desteden kart çekip eline ekler.
    public void penaltyDraw(String roomId, int count) {
        GameRoom room = rooms.get(roomId);
        Player victim = room.getPlayers().get((room.getCurrentPlayerIndex() + 1) % room.getPlayers().size()); //karşıdaki oyuncuyu buldu (kurban olanı)
        for (int i = 0; i < count; i++) victim.getHand().add(room.getDeck().drawCard());
    }

    public GameRoom getRoom(String roomId) { return rooms.get(roomId); }
}