package com.hotel.room_service.application.usecase;

import com.hotel.room_service.domain.model.ItemEspecial;
import com.hotel.room_service.domain.model.ItemsEspeciales;
import com.hotel.room_service.domain.port.ItemsEspecialesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemsEspecialesUseCase {
    private final ItemsEspecialesRepository itemsEspecialesRepository;

    public Mono<ItemsEspeciales> consultarItemsEspeciales() {
        int cantidad = 5;

        return Mono.zip(
                consultarItemMasPedido(cantidad),
                consultarItemMasReciente(cantidad)
        ).map(tuple -> ItemsEspeciales.builder()
                .itemsMasPedidos(tuple.getT1())
                .itemsMasRecientes(tuple.getT2())
                .build()
        );
    }

    private Mono<List<ItemEspecial>> consultarItemMasPedido(int cantidad) {
        return itemsEspecialesRepository.consultarItemsMasPedidos(cantidad);
    }

    private Mono<List<ItemEspecial>> consultarItemMasReciente(int cantidad) {
        return itemsEspecialesRepository.consultarItemsMasRecientes(cantidad);
    }
}
