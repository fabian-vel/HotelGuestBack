package com.hotel.room_service.application.usecase;

import com.hotel.room_service.controller.dto.EmpleadoLoginResponse;
import com.hotel.room_service.domain.exception.BusinessException;
import com.hotel.room_service.domain.exception.NotFoundException;
import com.hotel.room_service.domain.model.EmpleadoLoginRequest;
import com.hotel.room_service.domain.port.EmpleadoRepository;
import com.hotel.room_service.shared.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LoginEmpleadoUseCase {

    private final EmpleadoRepository empleadoRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public Mono<EmpleadoLoginResponse> ejecutar(EmpleadoLoginRequest request) {
        return empleadoRepository.findByUsuario(request.getUsuario())
                .switchIfEmpty(Mono.error(new NotFoundException("Credenciales inválidas")))
                .flatMap(empleado -> {
                    if (!passwordEncoder.matches(request.getPassword(), empleado.getEmplPassword())) {
                        return Mono.error(new BusinessException("Credenciales inválidas"));
                    }
                    return Mono.just(new EmpleadoLoginResponse(
                            jwtService.generarTokenEmpleado(empleado),
                            empleado.getEmplNombre(),
                            empleado.getEmplRol()
                    ));
                });
    }
}
