package com.hotel.room_service.application.transformer;

import com.hotel.room_service.controller.dto.ItemEspecialResponse;
import com.hotel.room_service.controller.dto.ItemsEspecialesResponse;
import com.hotel.room_service.domain.model.ItemEspecial;
import com.hotel.room_service.domain.model.ItemsEspeciales;

import java.util.List;

public class ItemsEspecialesTransformer {

    private ItemsEspecialesTransformer() {}

    public static ItemsEspecialesResponse toResponse(ItemsEspeciales domain) {
        return new ItemsEspecialesResponse(
                toItemEspecialResponseList(domain.getItemsMasPedidos()),
                toItemEspecialResponseList(domain.getItemsMasRecientes())
        );
    }

    private static List<ItemEspecialResponse> toItemEspecialResponseList(List<ItemEspecial> items) {
        return items.stream()
                .map(ItemsEspecialesTransformer::toItemEspecialResponse)
                .toList();
    }

    private static ItemEspecialResponse toItemEspecialResponse(ItemEspecial item) {
        return new ItemEspecialResponse(
                item.getMeitId(),
                item.getMeitNombre(),
                item.getMeitImagenUrl(),
                item.getMeitPrecio()
        );
    }
}
