package com.hotel.room_service.application.usecase.rule;

import com.hotel.room_service.application.rule.ReglaNegocio;
import com.hotel.room_service.domain.exception.BusinessException;
import com.hotel.room_service.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CrearPedidoRule implements ReglaNegocio<PedidoContexto> {

    @Override
    public String getRuleId() {
        return "CREAR_PEDIDO";
    }

    @Override
    public Mono<Void> validar(PedidoContexto contexto) {
        List<DatosPedido> datosPedidos = contexto.getResponse().getListaDatosPedidos();
        List<PedidoCriterio.ItemPedido> items = contexto.getRequest().getPedidoCriterio().getItems();

        if (datosPedidos.isEmpty()) {
            return Mono.error(new BusinessException("No se encontraron datos para crear el pedido"));
        }
        if (items.isEmpty()) {
            return Mono.error(new BusinessException("El pedido debe tener al menos un item"));
        }
        if (datosPedidos.stream().anyMatch(d -> d.getMeitPrecio() == null)) {
            return Mono.error(new BusinessException("Uno o más items no tienen precio registrado"));
        }

        return crearPedido(contexto)
                .then(crearDetallePedido(contexto));
    }

    private Mono<Void> crearPedido(PedidoContexto contexto) {
        List<DatosPedido> datosPedidos = contexto.getResponse().getListaDatosPedidos();

        Pedido pedido = Pedido.builder()
                .haacId(contexto.getRequest().getHaacId())
                .habitacion(datosPedidos.getFirst().getHaacHabitacion())
                .clienteNombre(datosPedidos.getFirst().getHaacClienteNombre())
                .estadoPedidoId(datosPedidos.getFirst().getEstadoPedidoId())
                .observacion(contexto.getRequest().getPedidoCriterio().getObservacion())
                .total(calcularTotal(contexto))
                .build();

        contexto.getRequest().setPedido(pedido);
        return Mono.empty();
    }

    private Mono<Void> crearDetallePedido(PedidoContexto contexto) {
        List<DatosPedido> datosPedidos = contexto.getResponse().getListaDatosPedidos();
        List<PedidoCriterio.ItemPedido> items = contexto.getRequest().getPedidoCriterio().getItems();

        List<DetallePedido> detalles = items.stream()
                .map(item -> {
                    BigDecimal precioUnitario = buscarPrecio(datosPedidos, item.getMeitId());
                    return DetallePedido.builder()

                            .meitId(item.getMeitId())
                            .cantidad(item.getCantidad())
                            .precioUnitario(precioUnitario)
                            .subtotal(calcularSubtotal(precioUnitario, item.getCantidad()))
                            .observacion(item.getObservacion())
                            .build();
                })
                .toList();

        contexto.getRequest().setDetallePedidoList(detalles);
        return Mono.empty();
    }

    private BigDecimal calcularTotal(PedidoContexto contexto) {
        List<DatosPedido> datosPedidos = contexto.getResponse().getListaDatosPedidos();
        return contexto.getRequest().getPedidoCriterio().getItems().stream()
                .map(item -> calcularSubtotal(
                        buscarPrecio(datosPedidos, item.getMeitId()),
                        item.getCantidad()
                ))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calcularSubtotal(BigDecimal precioUnitario, Short cantidad) {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    private BigDecimal buscarPrecio(List<DatosPedido> datosPedidos, Short meitId) {
        return datosPedidos.stream()
                .filter(dato -> dato.getMeitId().equals(meitId))
                .map(DatosPedido::getMeitPrecio)
                .findFirst()
                .orElse(BigDecimal.ZERO);
    }
}
