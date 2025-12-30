<<<<<<< HEAD
# Uno Game
<img width="980" height="828" alt="image" src="https://github.com/user-attachments/assets/71a8f7b0-5e5a-4a25-aefe-c7cec5451681" />


Bu proje, Spring Boot framework'ü kullanılarak geliştirilmiş, Nesne Yönelimli Programlama (OOP) prensiplerini temel alan çok oyunculu bir Uno oyunudur. Gerçek zamanlı oyun akışı için WebSocket (STOMP) mimarisi kullanılmıştır.
 
## Özellikler

*  **Gerçek Zamanlı İletişim:** WebSocket ve SockJS desteğiyle anlık kart atma ve durum güncellemeleri.

*  **Gelişmiş OOP Mimarisi:** Abstraction (Soyutlama), Inheritance (Kalıtım) ve Polymorphism (Çok Biçimlilik) prensiplerine dayalı kart sistemi.

*  **Katmanlı Mimari:** Sorumlulukların ayrıştırıldığı Controller-Service-Model-DTO yapısı.

*  **Oda Yönetimi:**  `ConcurrentHashMap` tabanlı, aynı anda birden fazla oyun odasını yönetebilen yapı.

  

## Kullanılan Teknolojiler

*  **Backend:** Java 17+, Spring Boot 3.x

*  **İleti Yönetimi:** Spring Messaging (STOMP & WebSocket)

*  **Kütüphaneler:** Lombok (Kod temizliği), Jackson (JSON işleme)

*  **Derleme Aracı:** Maven

  

## Proje Yapısı

*  **`config/`**: WebSocket bağlantı ve mesaj broker ayarları.

*  **`controller/`**: Kullanıcı hamlelerini karşılayan ve dağıtan katman.

*  **`service/`**: Oyun kurallarını (business logic) yöneten ana motor.

*  **`model/`**: Kart, Oyuncu, Deste ve Oda gibi temel nesneler.

*  **`dto/`**: Sunucu ve istemci arasındaki veri taşıma paketleri.

  

## Yazılım Mimarisi (UML)
Projenin sınıf hiyerarşisi ve ilişkileri aşağıdaki UML diyagramında özetlenmiştir:

  <img width="6548" height="7615" alt="UML" src="https://github.com/user-attachments/assets/a4e11bc7-7afb-4c93-9e92-6b46f714538c" />

  
  
  

## Kurulum
### Depoyu klonlayın:
```
git clone [https://github.com/ahsenzuhal/UNO-Game-with-java]
```

### Maven ile derleyin:
```
mvn clean install
```
### Uygulamayı çalıştırın:
```
mvn spring-boot:run
```
### Başlatma
Uygulama http://localhost:8080 adresinde hazır olacaktır.
=======
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
>>>>>>> f549f37a6cb6661e4d69d6aae2c0d46c8930d0a1
