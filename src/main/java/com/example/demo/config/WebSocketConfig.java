package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//config dosyaları bir projenin hangi kanallardan veri göndereceğini belirler. Uygulama başladığında ilk okunan dosya budur.

@Configuration  //bu sınıf bir anayasa. Program çalıştığında ilk burası okunacak ve nasıl çalışması gerektiğine dair kurallar okunacak.
@EnableWebSocketMessageBroker //bu bir mesaj uygulaması. Yani düz okunan bir site değil. Karşılklı veri alışverişi yapılan bir site yapıyoruz.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) { // Sunucu ve oyuncular arasında hangi yol ile (path) iletişim kuracağımızı belirledik
        // Sunucudan istemciye giden mesajlar için (topic)
        config.enableSimpleBroker("/topic");
        // İstemciden sunucuya gelen mesajlar için ön ek
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Oyuncuların bağlanacağı uç nokta, frontend sunucuya ilk buradan bağlanır
        registry.addEndpoint("/uno-game").withSockJS(); //withSockJS() : eğer WebSocket desteklenmiyorsa bağlantı kopmasın diye alternatif iletişim yöntemleri kullandık.
    }
}