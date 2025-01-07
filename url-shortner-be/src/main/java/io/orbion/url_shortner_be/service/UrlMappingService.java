package io.orbion.url_shortner_be.service;

import java.util.List;

import io.orbion.url_shortner_be.entity.model.User;
import io.orbion.url_shortner_be.entity.response.UrlMappingResponse;

public interface UrlMappingService {

    UrlMappingResponse createShortUrl(String originalUrl, User user);

    List<UrlMappingResponse> getUrlsByUser(User user);
}
