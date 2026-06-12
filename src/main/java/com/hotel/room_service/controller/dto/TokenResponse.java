package com.hotel.room_service.controller.dto;

public record TokenResponse(
        String token,
        String expiracion
) {}
