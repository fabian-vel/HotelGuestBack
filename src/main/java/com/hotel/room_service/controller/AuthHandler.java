package com.hotel.room_service.controller;

import com.hotel.room_service.application.LoginUseCase;
import com.hotel.room_service.controller.dto.LoginRequest;
import com.hotel.room_service.shared.constant.SuccessMessages;
import com.hotel.room_service.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthHandler {

    private final LoginUseCase loginUseCase;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(loginUseCase::ejecutar)
                .doOnNext(token -> log.info("Token generado: {}", token))
                .flatMap(token -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ApiResponse.ok(
                                SuccessMessages.LOGIN_EXITOSO.getMessage(),
                                token
                        ))
                );
    }
}

