package com.hotel.room_service.shared.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        if (path.startsWith("/api/v1/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        log.info("Path: {}, AuthHeader presente: {}", path, authHeader != null);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Sin token o formato inválido");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        return jwtService.validarToken(token)
                .flatMap(authentication ->
                        chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder
                                        .withAuthentication(authentication))
                )
                .onErrorResume(ex -> {
                    log.error("Error validando token: {}", ex.getMessage(), ex); // ← clave
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }
}