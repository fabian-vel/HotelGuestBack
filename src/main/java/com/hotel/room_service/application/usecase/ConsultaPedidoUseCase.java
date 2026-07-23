package com.hotel.room_service.application.usecase;

import com.hotel.room_service.domain.model.ConsultaPedido;
import com.hotel.room_service.domain.port.ConsultaPedidoRepository;
import com.hotel.room_service.shared.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaPedidoUseCase {
    private final ConsultaPedidoRepository consultaPedidoRepository;
    private final TokenUtil tokenUtil;

    public Mono<List<ConsultaPedido>> consultarPedidoPorUsuario() {
        return tokenUtil.getHaacId()
                .flatMap(consultaPedidoRepository::consultarPedidoPorUsuario);
    }

    public Mono<List<ConsultaPedido>> consultarPedidoFechaActual() {
        return consultaPedidoRepository.consultarPedidoFechaActual();
    }
}
