package com.hotel.room_service.application;

import com.hotel.room_service.domain.model.MenuCategoria;
import com.hotel.room_service.domain.port.MenuCategoriaRepository;
import com.hotel.room_service.infrastructure.model.MenuCategoriaRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ConsultarCategoriasUseCase {

    private final MenuCategoriaRepository menuCategoriaRepository;

    public Mono<List<MenuCategoria>> ejecutar() {
        return menuCategoriaRepository.consultarCategorias()
                .map(this::agruparCategorias);
    }

    private List<MenuCategoria> agruparCategorias(List<MenuCategoriaRow> rows) {

        Map<Short, MenuCategoria> padresMap = new LinkedHashMap<>();

        for (MenuCategoriaRow row : rows) {

            MenuCategoria padre =
                    padresMap.computeIfAbsent(
                            row.getPadreId(),
                            id -> MenuCategoria.builder()
                                    .mecaId(id)
                                    .mecaNombre(row.getPadreNombre())
                                    .mecaDescripcion(row.getPadreDescripcion())
                                    .mecaImagenUrl(row.getPadreImagenUrl())
                                    .mecaParentId(null)
                                    .subCategorias(new LinkedList<>())
                                    .build()
                    );

            if (row.getHijoId() != null) {

                padre.getSubCategorias().add(
                        MenuCategoria.builder()
                                .mecaId(row.getHijoId())
                                .mecaNombre(row.getHijoNombre())
                                .mecaDescripcion(row.getHijoDescripcion())
                                .mecaImagenUrl(row.getHijoImagenUrl())
                                .mecaParentId(row.getPadreId())
                                .subCategorias(new LinkedList<>())
                                .build()
                );
            }
        }

        return new ArrayList<>(padresMap.values());
    }
}
