package io.orbion.url_shortner_be.service;

import io.orbion.url_shortner_be.config.JwtAuthenticationResponse;
import io.orbion.url_shortner_be.entity.model.User;
import io.orbion.url_shortner_be.entity.request.LoginRequest;

public interface UserService {

    User register(User user);

    JwtAuthenticationResponse login(LoginRequest request);

    User findByUsername(String username);
}
