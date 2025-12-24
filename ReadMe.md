# UNO Online - Spring Boot & WebSocket 

Bu proje; Java Spring Boot backend ve modern JavaScript frontend mimarisi kullanılarak geliştirilmiş, gerçek zamanlı (real-time) ve çok oyunculu bir kart oyunudur. Oyuncular aynı oda numarasıyla eşleşerek UNO heyecanını tarayıcı üzerinden yaşayabilirler.

## Temel Özellikler
* **WebSocket İletişimi:** Hamlelerin tüm oyunculara milisaniyeler içinde iletilmesi için STOMP protokolü kullanıldı.
* **Oda Sistemi:** Dinamik oda oluşturma ve oyuncu eşleştirme mantığı.
* **Gelişmiş Oyun Mekaniği:**
    * **Skip (Engelleme):** 2 kişilik oyun kurallarına göre sıranın atan kişide kalması sağlandı.
    * **Wild (Joker) & +4:** Oyuncuya renk seçimi yaptıran dinamik modal yapı.
    * **Otomatik Deste:** Kartlar bittiğinde destenin yeniden karılması ve dağıtılması.
* **Modern UI/UX:** Karanlık tema, animasyonlu kartlar ve özel semboller (`⊘`, `+2`, `✪`).

## Teknik Mimari
### Backend
* **Spring Boot:** Uygulama iskeleti.
* **SockJS & STOMP:** Çift taraflı (Full-duplex) iletişim.
* **Jackson (ObjectMapper):** Polimorfik kart tiplerinin (Number, Action, Wild) JSON üzerinden hatasız işlenmesi.

### Frontend
* **Vanilla JavaScript:** Hafif ve hızlı istemci mantığı.
* **CSS Gradients:** Kartlara derinlik katan modern tasarım.

## Geliştirme Aşamaları

### 1. Temel Motorun Kurulması
İlk aşamada kart sınıfları (`Card`, `NumberCard`, `ActionCard`, `WildCard`) ve oyunun beyni olan `GameService` yazıldı. Deste yönetimi ve temel kart atma kuralları backend üzerinde simüle edildi.

### 2. İletişim Altyapısı
Spring WebSocket yapılandırması tamamlanarak `/app` (istek) ve `/topic` (yayın) kanalları kuruldu. Oyuncuların odaya katılması ve oyun durumunun (`GameState`) anlık olarak tüm abonelere basılması sağlandı.

### 3. Arayüz ve Tasarım
Statik HTML sayfası, dinamik bir oyun alanına dönüştürüldü. Kartların üzerine gelince büyümesi ve yerdeki kartın parlaması gibi görsel detaylar eklendi.

### 4. Mantık Hatalarının Giderilmesi (Final)
* Renk seçimi sonrası oyunun kilitlenmesi sorunu, `activeColor` takibiyle çözüldü.
* "Oyun Bitti" ekranının yanlış zamanlarda çıkması, durum bazlı mesaj yönetimiyle (`BEKLENİYOR`, `AKTİF`, `TAMAMLANDI`) düzeltildi.

## Kurulum ve Çalıştırma
1. Projeyi klonlayın.
2. Maven bağımlılıklarını yükleyin: `mvn install`
3. Uygulamayı başlatın: `mvn spring-boot:run`
4. Tarayıcıdan `localhost:8080` adresine gidin.
