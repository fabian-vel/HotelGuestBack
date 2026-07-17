package com.hotel.room_service.application.usecase;

import com.hotel.room_service.domain.model.ItemEspecial;
import com.hotel.room_service.domain.model.ItemsEspeciales;
import com.hotel.room_service.domain.port.ConstantesRepository;
import com.hotel.room_service.domain.port.ItemsEspecialesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.hotel.room_service.shared.util.ConstanteUtil.getAsShort;

@Service
@RequiredArgsConstructor
public class ItemsEspecialesUseCase {
    private final ItemsEspecialesRepository itemsEspecialesRepository;
    private final ConstantesRepository constantesRepository;

    public Mono<ItemsEspeciales> consultarItemsEspeciales() {
        int cantidad = 5;

        return Mono.zip(
                consultarItemMasPedido(cantidad),
                consultarItemMasReciente(cantidad),
                consultarItemRecomendado(cantidad)
        ).map(tuple -> ItemsEspeciales.builder()
                .itemsMasPedidos(tuple.getT1())
                .itemsMasRecientes(tuple.getT2())
                .itemsRecomendados(tuple.getT3())
                .build()
        );
    }

    private Mono<List<ItemEspecial>> consultarItemMasPedido(int cantidad) {
        return itemsEspecialesRepository.consultarItemsMasPedidos(cantidad);
    }

    private Mono<List<ItemEspecial>> consultarItemMasReciente(int cantidad) {
        return itemsEspecialesRepository.consultarItemsMasRecientes(cantidad);
    }

    private Mono<List<ItemEspecial>> consultarItemRecomendado(int cantidad) {
        List<String> llaves = new ArrayList<>(List.of("CONST_ETIQ_RECOM_CHEF"));
        return constantesRepository.consultaConstantes(llaves)
                .flatMap(constantes -> {
                    Short etiqueta = getAsShort(constantes, "CONST_ETIQ_RECOM_CHEF");
                    return itemsEspecialesRepository.consultarItemsRecomendadosChef(cantidad, etiqueta);
                });
    }
}
