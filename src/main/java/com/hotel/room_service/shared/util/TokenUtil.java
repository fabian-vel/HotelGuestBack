package com.hotel.room_service.shared.util;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TokenUtil {
    public Mono<Short> getHaacId() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> (UsernamePasswordAuthenticationToken) ctx.getAuthentication())
                .map(auth -> {
                    Claims claims = (Claims) auth.getDetails();
                    return claims.get("accesoId", Integer.class).shortValue();
                });
    }

    public Mono<String> getHabitacion() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getName()); // subject del JWT
    }

    public Mono<String> getClienteNombre() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> (UsernamePasswordAuthenticationToken) ctx.getAuthentication())
                .map(auth -> {
                    Claims claims = (Claims) auth.getDetails();
                    return claims.get("clienteNombre", String.class);
                });
    }
}
