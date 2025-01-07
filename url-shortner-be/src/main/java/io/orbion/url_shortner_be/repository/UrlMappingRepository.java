package io.orbion.url_shortner_be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.orbion.url_shortner_be.entity.model.UrlMapping;
import io.orbion.url_shortner_be.entity.model.User;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByShortUrl(String shortUrl);

    List<UrlMapping> findByUser(User user);

}
