package io.orbion.url_shortner_be.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.orbion.url_shortner_be.entity.model.UrlMapping;
import io.orbion.url_shortner_be.entity.model.User;
import io.orbion.url_shortner_be.entity.response.UrlMappingResponse;
import io.orbion.url_shortner_be.repository.UrlMappingRepository;
import io.orbion.url_shortner_be.service.UrlMappingService;
import io.orbion.url_shortner_be.util.Utils;

@Service
public class UrlMappingServiceImpl implements UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;

    public UrlMappingServiceImpl(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
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

}
