package io.orbion.url_shortner_be.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import io.orbion.url_shortner_be.entity.model.User;
import io.orbion.url_shortner_be.entity.response.ClickEventResponse;
import io.orbion.url_shortner_be.entity.response.UrlMappingResponse;

public interface UrlMappingService {

    UrlMappingResponse createShortUrl(String originalUrl, User user);

    List<UrlMappingResponse> getUrlsByUser(User user);

    List<ClickEventResponse> getAnalyticsByShortUrl(String shortUrl, LocalDateTime start, LocalDateTime end);

    Map<LocalDate, Long> getTotalClicksByUser(User user, LocalDate start, LocalDate end);
}
