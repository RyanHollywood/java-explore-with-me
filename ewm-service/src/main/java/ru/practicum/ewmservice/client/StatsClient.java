package ru.practicum.ewmservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmservice.mapper.HitMapper;
import ru.practicum.ewmservice.model.Hit;

import java.time.LocalDateTime;

@Slf4j
@Component
public class StatsClient {

    private final RestTemplate restTemplate;
    private final String serverPort;
    private final String app;
    private final String pattern;

    public StatsClient(@Value("${stats.port}") String serverPort, @Value("${stats.app}") String app,
                       @Value("${date.time.pattern}") String pattern) {
        this.restTemplate = new RestTemplate();
        this.pattern = pattern;
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
        restTemplate.postForLocation("http://stats-server:" + serverPort + "/hit", HitMapper.toHitDto(info, pattern));
        log.debug("Hit from {} to {} was send.", app, uri);
    }
}