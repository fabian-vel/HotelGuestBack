package com.hotel.room_service.application.usecase.rule;

import com.hotel.room_service.application.rule.ReglaNegocio;
import com.hotel.room_service.domain.exception.BusinessException;
import com.hotel.room_service.domain.model.PedidoContexto;
import com.hotel.room_service.domain.port.InferirDatosPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InferirDatosPedidoRule implements ReglaNegocio<PedidoContexto> {
    private final InferirDatosPedidoRepository inferirDatosPedidoRepository;

    @Override
    public String getRuleId() {
        return "INFERIR_DATOS_PEDIDO";
    }

    @Override
    public Mono<Void> validar(PedidoContexto contexto) {
        Short haacId = contexto.getRequest().getHaacId();
        List<Short> listItemId = contexto.getRequest().getListItemId();

        if (listItemId.isEmpty() || haacId == null) {
            return Mono.error( new BusinessException("Datos insuficientes para inferir información de pedido"));
        }

        return inferirDatosPedidos(contexto);
    }

    private Mono<Void> inferirDatosPedidos(PedidoContexto contexto) {
        return Mono.defer(() ->
                        inferirDatosPedidoRepository.inferirDatosPedidos(
                                contexto.getRequest().getHaacId(),
                                contexto.getRequest().getListItemId()
                        ))
                .doOnNext(datos -> contexto.getResponse().setListaDatosPedidos(datos))
                .then();
    }
}
