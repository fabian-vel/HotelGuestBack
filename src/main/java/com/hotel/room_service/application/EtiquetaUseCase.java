package com.hotel.room_service.application;

import com.hotel.room_service.domain.exception.BusinessException;
import com.hotel.room_service.domain.model.Etiqueta;
import com.hotel.room_service.domain.model.EtiquetaRequest;
import com.hotel.room_service.domain.port.EtiquetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtiquetaUseCase {
    private final EtiquetaRepository etiquetaRepository;

    public Mono<List<Etiqueta>> consultaEtiqueta(EtiquetaRequest request) {
        if (request.isConsultaPorCategoria() && request.getMecaId() == null) {
            return Mono.error(new BusinessException("El id de la categoría es obligatorio"));
        } else if (!request.isConsultaPorCategoria() && request.getMeitId() == null) {
            return Mono.error(new BusinessException("El id del item del menú es obligatorio"));
        }

        return etiquetaRepository.consultaEtiqueta(request);
    }
}
