package com.hotel.room_service.application.transformer;

import com.hotel.room_service.controller.dto.ConsultaPedidoResponse;
import com.hotel.room_service.domain.model.ConsultaPedido;

public class ConsultaPedidoTransformer {
    private ConsultaPedidoTransformer() {}

    public static ConsultaPedidoResponse toResponse(ConsultaPedido domain) {
        return new ConsultaPedidoResponse(
                domain.getPediId(),
                domain.getPediHabitacion(),
                domain.getPediTotal(),
                domain.getPediFechaCreacion(),
                domain.getPediObservacion(),
                domain.getEspeId(),
                domain.getEspeNombre(),
                domain.getDetallePedidoList().stream()
                        .map(ConsultaPedidoTransformer::toDetalleResponse)
                        .toList()
        );
    }

    private static ConsultaPedidoResponse.PedidoDetalle toDetalleResponse(
            ConsultaPedido.PedidoDetalle detalle) {
        return new ConsultaPedidoResponse.PedidoDetalle(
                detalle.getMeitId(),
                detalle.getMeitNombre(),
                detalle.getPedeCantidad(),
                detalle.getPedeSubtotal()
        );
    }
}
