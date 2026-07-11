package com.hotel.room_service.controller.dto;

import java.math.BigDecimal;

public record ItemEspecialResponse(
        Short meitId,
        String meitNombre,
        String meitImagenUrl,
        BigDecimal meitPrecio
) {}
