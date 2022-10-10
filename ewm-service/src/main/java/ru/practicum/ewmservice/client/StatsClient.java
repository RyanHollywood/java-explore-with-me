package ru.practicum.ewmservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.model.Hit;

import java.time.LocalDateTime;

//make it using WebClient
@Component
public class StatsClient {

    private final String serverPort;
    private final String app;

    public StatsClient(@Value("${stats.port}") String serverPort,
                       @Value("${stats.app}") String app) {
        this.serverPort = serverPort;
        this.app = app;
    }

    public void sendHit(String uri, String ip, LocalDateTime timestamp) {
        Hit info = Hit.builder()
                .app(app)
                .uri(uri)
                .ip(ip)
                .timestamp(timestamp)
                .build();
    }
}