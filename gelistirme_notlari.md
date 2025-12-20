### UNO Proje Geliştirme Raporu - 20 Aralık 2025

### 1. Karşılaşılan Temel Sorunlar ve Hatalar
Port Bağımsızlığı Sorunu: Başlangıçta 8080 ve 8081 portları üzerinden iki ayrı sunucu açılıyordu. Bu durum, oyuncuların farklı bellek alanlarında (RAM) olmasına ve birbirlerini asla görememelerine neden oluyordu.

Senkronizasyon (Sıra) Hatası: Her iki oyuncunun ekranında aynı anda "SIRA SENDE" yazması, sunucunun oyuncu ID'lerini doğru ayrıştıramamasından ve WebSocket mesajlarının yanlış zamanda iletilmesinden kaynaklanıyordu.

Derleme (Compilation) Hataları: Oda sistemine geçiş yaparken, eski kart sınıflarının (ActionCard, NumberCard vb.) yeni GameService metodlarını bulamaması sonucu "cannot find symbol" hataları alındı.

Kütüphane Yükleme Hataları: JavaScript tarafında SockJS ve Stomp kütüphaneleri tam yüklenmeden bağlantı kurulmaya çalışıldığı için null pointer hataları oluştu.

### 2. Yapılan İşlemler ve Çözümler
Oda (Room) Mimarisine Geçiş: Proje, tek bir oyun yerine sınırsız oda oluşturulabilen bir yapıya taşındı. Map<String, GameRoom> yapısı kullanılarak her oyunun kendi destesi, oyuncuları ve yerdeki kartı izole edildi.

Giriş Ekranı (Lobi) Tasarımı: Kullanıcıların doğrudan oyuna düşmesi yerine, bir isim ve oda numarası girerek dahil olabileceği interaktif bir giriş ekranı (login-screen) eklendi.

WebSocket Senkronizasyonu: Mesaj kanalları /topic/game-state/{roomId}/{playerId} şeklinde özelleştirildi. Bu sayede sadece ilgili odadaki, ilgili oyuncuya özel veri gönderilerek veri güvenliği ve doğru senkronizasyon sağlandı.

JavaScript Güçlendirmesi: window.onload ve setTimeout kullanılarak, kütüphanelerin tam yüklenmesi ve bağlantı sonrası oyun durumunun sunucudan güvenli bir şekilde "çekilmesi" (requestState) sağlandı.

Hata Temizliği: Projedeki derlemeyi engelleyen atıl dosyalar (TestRunner.java gibi) temizlendi ve kart sınıfları oda mantığına uygun şekilde parametre alacak hale getirildi.

### 3. Mevcut Durum
Sunucu 8080 portunda tek bir instance olarak çalışıyor.

Oyuncular aynı oda numarasını (örn: 1001) girerek aynı masaya oturabiliyor.

İkinci oyuncu geldiğinde oyun otomatik başlıyor ve her iki tarafa aynı yerdeki kart yansıtılıyor.