# Uno Game

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

  
  
  
  

## Kurulum
### Depoyu klonlayın:
```
git clone [https://github.com/ahsenzuhal/UNO-Game-with-java](https://github.com/ahsenzuhal/UNO-Game-with-java) 
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