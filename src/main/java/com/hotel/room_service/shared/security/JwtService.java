package com.hotel.room_service.shared.security;

import com.hotel.room_service.domain.model.Empleado;
import com.hotel.room_service.domain.model.HabitacionAcceso;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String generarToken(HabitacionAcceso acceso) {
        return Jwts.builder()
                .subject(acceso.getHaacHabitacion())
                .claim("clienteNombre", acceso.getHaacClienteNombre())
                .claim("accesoId", acceso.getHaacId())
                .issuedAt(new Date())
                .expiration(Date.from(acceso.getHaacFechaFin()
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Mono<Authentication> validarToken(String token) {
        return Mono.fromCallable(() -> {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String habitacion = claims.getSubject();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    habitacion, null, List.of()
            );
            auth.setDetails(claims); // ← guarda claims completos para TokenUtil
            return auth;
        });
    }

    public String generarTokenEmpleado(Empleado empleado) {
        return Jwts.builder()
                .subject(empleado.getEmplUsuario())
                .claim("nombre", empleado.getEmplNombre())
                .claim("rol", empleado.getEmplRol())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(8, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}
