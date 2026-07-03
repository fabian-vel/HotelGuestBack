package com.hotel.room_service.infrastructure.mapper;

import com.hotel.room_service.domain.model.DatosPedido;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DatosPedidosRowMapper {

    public DatosPedido mapRow(Row row) {
        return DatosPedido.builder()
                .haacHabitacion(row.get("haac_habitacion", String.class))
                .haacClienteNombre(row.get("haac_cliente_nombre", String.class))
                .meitId(row.get("meit_id", Short.class))
                .meitPrecio(row.get("meit_precio", BigDecimal.class))
                .estadoPedidoId(row.get("estado_pedido_id", Short.class))
                .build();
    }
}
