package com.hotel.room_service.application;

import com.hotel.room_service.controller.dto.LoginRequest;
import com.hotel.room_service.controller.dto.TokenResponse;
import com.hotel.room_service.domain.exception.BusinessException;
import com.hotel.room_service.domain.exception.NotFoundException;
import com.hotel.room_service.domain.model.HabitacionAcceso;
import com.hotel.room_service.domain.port.HabitacionAccesoRepository;
import com.hotel.room_service.shared.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final HabitacionAccesoRepository accesoRepository;
    private final JwtService jwtService;

    public Mono<TokenResponse> ejecutar(LoginRequest request) {
        return accesoRepository.findByHabitacionAndCodigo(
                        request.habitacion(),
                        request.codigo()
                )
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Habitación o código inválidos")
                ))
                .flatMap(this::validar)
                .map(acceso -> new TokenResponse(
                        jwtService.generarToken(acceso),
                        acceso.getHaacFechaFin().toString()
                ));
    }

    private Mono<HabitacionAcceso> validar(HabitacionAcceso acceso) {
        if (!"A".equals(acceso.getHaacEstado())) {
            return Mono.error(new BusinessException("El acceso está inactivo"));
        }
        if (LocalDateTime.now().isBefore(acceso.getHaacFechaInicio())) {
            return Mono.error(new BusinessException("El acceso aún no está vigente"));
        }
        if (LocalDateTime.now().isAfter(acceso.getHaacFechaFin())) {
            return Mono.error(new BusinessException("El acceso ha expirado"));
        }
        return Mono.just(acceso);
    }
}
