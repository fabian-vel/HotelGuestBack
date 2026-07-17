package com.hotel.room_service.shared.util;

import com.hotel.room_service.domain.exception.BusinessException;

import java.util.Map;

public class ConstanteUtil {

    private ConstanteUtil() {}

    public static String get(Map<String, String> constantes, String llave) {
        String valor = constantes.get(llave);
        if (valor == null) {
            throw new BusinessException("Constante no encontrada: " + llave);
        }
        return valor;
    }

    public static Short getAsShort(Map<String, String> constantes, String llave) {
        return Short.valueOf(get(constantes, llave));
    }

    public static Integer getAsInteger(Map<String, String> constantes, String llave) {
        return Integer.valueOf(get(constantes, llave));
    }
}