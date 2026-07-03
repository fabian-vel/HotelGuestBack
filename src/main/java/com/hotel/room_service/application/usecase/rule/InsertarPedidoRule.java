package com.hotel.room_service.application.usecase.rule;

import com.hotel.room_service.application.rule.ReglaNegocio;
import com.hotel.room_service.domain.exception.BusinessException;
import com.hotel.room_service.domain.model.PedidoContexto;
import com.hotel.room_service.domain.port.InsertarPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InsertarPedidoRule implements ReglaNegocio<PedidoContexto> {
    private final InsertarPedidoRepository insertarPedidoRepository;

    @Override
    public String getRuleId() {
        return "INSERTAR_PEDIDO";
    }

    @Override
    public Mono<Void> validar(PedidoContexto contexto) {

        if (contexto.getRequest().getPedido() == null) {
            return Mono.error(new BusinessException("El pedido no puede ser nulo"));
        }

        return insertarPedido(contexto);
    }

    private Mono<Void> insertarPedido(PedidoContexto contexto) {
        return Mono.defer(() ->
                insertarPedidoRepository.insertarPedido(
                        contexto.getRequest().getPedido()
                ))
                .doOnNext(pedidoId -> contexto.getResponse().setPedidoId(pedidoId))
                .then();
    }
}
