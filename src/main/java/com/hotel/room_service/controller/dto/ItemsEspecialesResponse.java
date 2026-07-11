package com.hotel.room_service.controller.dto;

import java.util.List;

public record ItemsEspecialesResponse(
        List<ItemEspecialResponse> itemsMasPedidos,
        List<ItemEspecialResponse> itemsMasRecientes
) {}
