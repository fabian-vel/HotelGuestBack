package com.hotel.room_service.application.usecase.rule;

import com.hotel.room_service.application.rule.ReglaNegocio;
import com.hotel.room_service.domain.exception.BusinessException;
import com.hotel.room_service.domain.model.DetallePedido;
import com.hotel.room_service.domain.model.PedidoContexto;
import com.hotel.room_service.domain.port.InsertarDetallePedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InsertarDetallePedidoRule implements ReglaNegocio<PedidoContexto> {
    private final InsertarDetallePedidoRepository insertarDetallePedidoRepository;


    @Override
    public String getRuleId() {
        return "INSERTAR_DETALLE_PEDIDO";
    }

    @Override
    public Mono<Void> validar(PedidoContexto contexto) {
        if (contexto.getRequest().getDetallePedidoList() == null || contexto.getRequest().getDetallePedidoList().isEmpty()) {
            return Mono.error(new BusinessException("La lista de detalles de pedido no puede ser nula o vacía"));
        }

        Long pediId = contexto.getResponse().getPedidoId();
        List<DetallePedido> detalles = contexto.getRequest().getDetallePedidoList()
                .stream()
                .map(detalle -> detalle.toBuilder()
                        .pediId(pediId)
                        .build())
                .toList();

        return insertarDetallePedido(detalles);
    }

    private Mono<Void> insertarDetallePedido(List<DetallePedido> detalles) {
        return Mono.defer(() ->
                insertarDetallePedidoRepository.insertarDetallePedido(detalles))
                .then();
    }
}
