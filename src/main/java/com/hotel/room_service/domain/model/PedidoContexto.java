package com.hotel.room_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PedidoContexto {
    PedidoRequest request;
    @Builder.Default
    PedidoResponse response =  new PedidoResponse();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PedidoRequest{
        PedidoCriterio pedidoCriterio;
        Short haacId;
        List<Short> listItemId;
        Pedido pedido;
        List<DetallePedido> detallePedidoList;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PedidoResponse{
        List<DatosPedido> listaDatosPedidos = new ArrayList<>();
        Long pedidoId;
    }
}
