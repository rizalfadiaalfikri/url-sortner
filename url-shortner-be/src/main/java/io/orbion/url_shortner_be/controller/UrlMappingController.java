package io.orbion.url_shortner_be.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.orbion.url_shortner_be.entity.model.User;
import io.orbion.url_shortner_be.entity.request.UrlMappingRequest;
import io.orbion.url_shortner_be.entity.response.ClickEventResponse;
import io.orbion.url_shortner_be.entity.response.UrlMappingResponse;
import io.orbion.url_shortner_be.service.UrlMappingService;
import io.orbion.url_shortner_be.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/urls")
@SecurityRequirement(name = "Bearer")
public class UrlMappingController {

    private final UrlMappingService urlMappingService;
    private final UserService userService;

    public UrlMappingController(UrlMappingService urlMappingService, UserService userService) {
        this.urlMappingService = urlMappingService;
        this.userService = userService;
    }

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingResponse> shorten(
            @RequestBody UrlMappingRequest request,
            Principal principal) {

        User user = userService.findByUsername(principal.getName());
        UrlMappingResponse response = urlMappingService.createShortUrl(request.getOriginalUrl(), user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myurls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlMappingResponse>> getUrls(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<UrlMappingResponse> response = urlMappingService.getUrlsByUser(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventResponse>> getAnalyticsShortUrl(
            @PathVariable String shortUrl,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);

        List<ClickEventResponse> response = urlMappingService.getAnalyticsByShortUrl(shortUrl, start, end);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/totalClicks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<LocalDate, Long>> getAnalyticsTotalClicks(
            Principal principal,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        User user = userService.findByUsername(principal.getName());
        Map<LocalDate, Long> response = urlMappingService.getTotalClicksByUser(user, start, end);
        return ResponseEntity.ok(response);
    }
}
