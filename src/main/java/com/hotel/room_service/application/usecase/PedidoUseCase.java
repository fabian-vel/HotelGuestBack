package com.hotel.room_service.application.usecase;

import com.hotel.room_service.application.engine.MotorReglas;
import com.hotel.room_service.domain.model.PedidoContexto;
import com.hotel.room_service.domain.model.PedidoCriterio;
import com.hotel.room_service.shared.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoUseCase {

    private final MotorReglas<PedidoContexto> motorReglas;
    private final TokenUtil tokenUtil;
    private final PedidoNotificationUseCase pedidoNotificationUseCase;

    private static final List<String> REGLAS = List.of(
            "INFERIR_DATOS_PEDIDO",
            "CREAR_PEDIDO",
            "INSERTAR_PEDIDO",
            "INSERTAR_DETALLE_PEDIDO"
    );

    public Mono<String> crearPedido(PedidoCriterio criterio) {

        List<Short> listItemId = criterio.getItems().stream()
                .map(PedidoCriterio.ItemPedido::getMeitId)
                .toList();

        return tokenUtil.getHaacId()
                .map(haacId -> PedidoContexto.builder()
                        .request(PedidoContexto.PedidoRequest.builder()
                                .pedidoCriterio(criterio)
                                .listItemId(listItemId)
                                .haacId(haacId)
                                .build())
                        .build()
                )
                .flatMap(contexto ->
                        motorReglas.aplicar(REGLAS, contexto)
                                .then(Mono.fromRunnable(() ->
                                                pedidoNotificationUseCase.publishCreated(
                                                        contexto.getResponse().getPedidoId()
                                                )
                                        )
                                ))
                .thenReturn("Pedido realizado con éxito");
    }
}