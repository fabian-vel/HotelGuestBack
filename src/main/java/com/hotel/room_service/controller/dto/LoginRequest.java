package com.hotel.room_service.controller.dto;

public record LoginRequest(
        String habitacion,
        String codigo
) {}
