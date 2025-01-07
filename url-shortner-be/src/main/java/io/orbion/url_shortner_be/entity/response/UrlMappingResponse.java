package io.orbion.url_shortner_be.entity.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlMappingResponse {

    private Long id;

    private String originalUrl;

    private String shortUrl;

    private int clickCount;

    private LocalDateTime createdAt;

    private String username;
}
