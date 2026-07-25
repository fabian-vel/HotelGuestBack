package com.hotel.room_service.controller.dto;

public record EmpleadoLoginResponse(
        String token,
        String nombre,
        String rol
) {}
