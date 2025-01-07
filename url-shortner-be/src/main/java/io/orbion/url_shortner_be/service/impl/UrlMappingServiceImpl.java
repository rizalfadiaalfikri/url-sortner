package io.orbion.url_shortner_be.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.orbion.url_shortner_be.entity.model.ClickEvent;
import io.orbion.url_shortner_be.entity.model.UrlMapping;
import io.orbion.url_shortner_be.entity.model.User;
import io.orbion.url_shortner_be.entity.response.ClickEventResponse;
import io.orbion.url_shortner_be.entity.response.UrlMappingResponse;
import io.orbion.url_shortner_be.repository.ClickEventRepository;
import io.orbion.url_shortner_be.repository.UrlMappingRepository;
import io.orbion.url_shortner_be.service.UrlMappingService;
import io.orbion.url_shortner_be.util.Utils;

@Service
public class UrlMappingServiceImpl implements UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final ClickEventRepository clickEventRepository;

    public UrlMappingServiceImpl(UrlMappingRepository urlMappingRepository, ClickEventRepository clickEventRepository) {
        this.urlMappingRepository = urlMappingRepository;
        this.clickEventRepository = clickEventRepository;
    }

    @Override
    public UrlMappingResponse createShortUrl(String originalUrl, User user) {
        String shortUrl = Utils.generateShortUrl();
        UrlMapping urlMapping = UrlMapping.builder()
                .originalUrl(originalUrl)
                .shortUrl(shortUrl)
                .user(user)
                .build();

        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
        return UrlMappingResponse.builder()
                .id(savedUrlMapping.getId())
                .originalUrl(savedUrlMapping.getOriginalUrl())
                .shortUrl(savedUrlMapping.getShortUrl())
                .clickCount(savedUrlMapping.getClickCount())
                .createdAt(savedUrlMapping.getCreatedAt())
                .username(savedUrlMapping.getUser().getUsername())
                .build();
    }

    @Override
    public List<UrlMappingResponse> getUrlsByUser(User user) {
        List<UrlMapping> urlMappings = urlMappingRepository.findByUser(user);
        return urlMappings.stream()
                .map(urlMapping -> UrlMappingResponse.builder()
                        .id(urlMapping.getId())
                        .originalUrl(urlMapping.getOriginalUrl())
                        .shortUrl(urlMapping.getShortUrl())
                        .clickCount(urlMapping.getClickCount())
                        .createdAt(urlMapping.getCreatedAt())
                        .username(urlMapping.getUser().getUsername())
                        .build())
                .toList();
    }

    @Override
    public List<ClickEventResponse> getAnalyticsByShortUrl(String shortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("Short url not found"));

        if (urlMapping != null) {
            return clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping, start, end).stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> ClickEventResponse.builder()
                            .clickDate(entry.getKey())
                            .count(entry.getValue())
                            .build())
                    .toList();
        }
        return null;
    }

    @Override
    public Map<LocalDate, Long> getTotalClicksByUser(User user, LocalDate start, LocalDate end) {
        List<UrlMapping> urlMappings = urlMappingRepository.findByUser(user);
        List<ClickEvent> clickEvents = clickEventRepository.findByUrlMappingInAndClickDateBetween(urlMappings,
                start.atStartOfDay(), end.plusDays(1).atStartOfDay());
        return clickEvents.stream()
                .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()));

    }

}
