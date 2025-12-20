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

