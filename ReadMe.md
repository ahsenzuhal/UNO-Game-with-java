Bu proje, Java Nesne Yönelimli Programlama (OOP) prensipleri ve Spring Boot WebSocket teknolojisi kullanılarak geliştirilmiş, çok oyunculu bir UNO oyunu uygulamasıdır. 

## 🚀 Projenin Bu Versiyonundaki Yenilikler (v3)
- **Multi-Port Desteği:** İki farklı uygulama instance'ı (8080 ve 8081 portları) üzerinden eşzamanlı oyun.
- **WebSocket (STOMP):** Hamlelerin anlık olarak her iki oyuncuya senkronize edilmesi.
- **Gelişmiş OOP Mimarisi:** Kart etkilerinin (Skip, Draw Two, Wild) Polimorfizm ile yönetilmesi.

## 🛠 Teknik Mimari

### OOP Prensipleri
- **Inheritance (Kalıtım):** `Card` abstract sınıfından türetilen `NumberCard`, `ActionCard` ve `WildCard`.
- **Polymorphism (Çok Biçimlilik):** Her kartın kendi `applyEffect()` ve `canPlayOn()` mantığını taşıması.
- **Encapsulation (Kapsülleme):** Veri güvenliği için DTO (Data Transfer Object) kullanımı.

### Teknolojiler
- **Backend:** Spring Boot 3.x, Maven, Lombok.
- **Communication:** WebSocket (STOMP & SockJS).
- **Frontend:** HTML5, CSS3, Vanilla JavaScript.

## 💻 Kurulum ve Çalıştırma

### 1. Oyuncu (Sunucu) Başlatma
Terminali açın ve ana dizinde şu komutu çalıştırın:
```bash
mvn spring-boot:run
```
Bu oyuncu varsayılan olarak http://localhost:8080 adresinden bağlanır.

### 2. Oyuncu Başlatma
Yeni bir terminal sekmesi açın ve şu komutu çalıştırın:

```bash
mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"
```
Bu oyuncu http://localhost:8081 adresinden bağlanır.

### 📁 Klasör Yapısı

src/main/java/com/example/demo/
├── config/     # WebSocket Configuration
├── controller/ # WebSocket Message Mapping
├── dto/        # Data Transfer Objects (Request/Response)
├── model/      # OOP Domain Models (Card, Player, Deck)
└── service/    # Game Logic (GameService)


1. Projenin Amacı
Bu proje; Java Spring Boot ve Modern JavaScript teknolojilerini kullanarak, web üzerinden gerçek zamanlı (real-time) oynanabilen, oda tabanlı, çok oyunculu bir UNO kart oyunu geliştirmeyi hedefler.

2. Kullanılan Teknolojiler
Backend: Java 17, Spring Boot, Spring WebSocket (STOMP & SockJS).

Frontend: HTML5, CSS3 (Modern Tasarım), Vanilla JavaScript.

Veri Formatı: JSON (Lombok ile optimize edildi).

3. Geliştirme Aşamaları
Aşama 1: Oyun Mantığı ve Veri Modeli
Kartların (Sayı, Aksiyon, Joker) sınıfları oluşturuldu.

Deste (Deck) yönetimi, kart çekme ve karıştırma algoritmaları yazıldı.

GameRoom yapısı ile oyunun aynı anda birden fazla odada oynanabilmesi sağlandı.

Aşama 2: WebSocket ve Gerçek Zamanlı İletişim
Oyuncuların hamlelerini sunucuya iletmesi için STOMP protokolü yapılandırıldı.

Oyunun durumu (GameState), her hamleden sonra odadaki tüm oyunculara anlık olarak yayınlanacak (broadcast) şekilde tasarlandı.

Aşama 3: Frontend ve Kullanıcı Deneyimi (UX)
Karanlık tema (Dark Mode) ve modern kart tasarımları yapıldı.

Sayı kartları dışında kalan özel kartlar için semboller (+2, ⊘, ✪) eklendi.

Oyunun başında "Bekleniyor", oyun sonunda "Tebrikler" ekranları kurgulandı.

Aşama 4: Kritik Kural ve Hata Çözümleri
Renk Seçimi: Joker kartı atıldığında oyuncuya renk seçtiren modal yapı eklendi ve kilitlenme sorunları giderildi.

Sıra Yönetimi: 2 kişilik oyunlarda "Atla/Skip" kartının sırayı rakibe geçirmeme (atan kişide bırakma) kuralı uygulandı.

Görsel Senkronizasyon: Siyah (Wild) kartların seçilen renge bürünmesi sağlanarak oyun akışı hatasız hale getirildi.

4. Sonuç
Proje; temel UNO kurallarının tamamını (kart eşleştirme, özel kart etkileri, cezalar ve kazanma kontrolü) destekleyen, performanslı ve kullanıcı dostu bir web uygulaması olarak başarıyla tamamlanmıştır.

