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
public class ItemsEspeciales {
    List<ItemEspecial> itemsMasPedidos = new ArrayList<>();
    List<ItemEspecial> itemsMasRecientes = new ArrayList<>();
    List<ItemEspecial> itemsRecomendados = new ArrayList<>();
}
