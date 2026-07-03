package com.hotel.room_service.application.engine;

import com.hotel.room_service.application.rule.ReglaNegocio;
import com.hotel.room_service.domain.exception.BusinessException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MotorReglas<T> {
    private final Map<String, ReglaNegocio<T>> reglasMap;

    public MotorReglas(List<ReglaNegocio<T>> reglas) {
        this.reglasMap = reglas.stream()
                .collect(Collectors.toMap(
                        ReglaNegocio::getRuleId,
                        regla -> regla
                ));
    }

    /**
     * Ejecuta una regla específica por id
     */
    public Mono<Void> aplicar(String ruleId, T contexto) {
        ReglaNegocio<T> regla = reglasMap.get(ruleId);
        if (regla == null) return Mono.error(
                new BusinessException("Regla no encontrada: " + ruleId)
        );
        return regla.validar(contexto);
    }

    /**
     * Ejecuta una lista ordenada de reglas
     */
    public Mono<Void> aplicar(List<String> ruleIds, T contexto) {
        return Flux.fromIterable(ruleIds)
                .concatMap(ruleId -> aplicar(ruleId, contexto))
                .then();
    }
}
