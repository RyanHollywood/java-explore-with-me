package ru.practicum.ewmservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.ewmservice.mapper.HitMapper;
import ru.practicum.ewmservice.model.Hit;

import java.time.LocalDateTime;

@Component
public class StatsClient {

    private final WebClient webClient;
    private final String serverPort;
    private final String app;

    public StatsClient(@Value("${stats.port}") String serverPort,
                       @Value("${stats.app}") String app) {
        this.webClient = WebClient.create();
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
        webClient
                .post()
                .uri("http://localhost:{serverPort}/hit", serverPort)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(HitMapper.toHitDto(info))
                .retrieve();
    }
}