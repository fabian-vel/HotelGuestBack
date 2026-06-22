package com.hotel.room_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtiquetaRequest {
    private Short mecaId;
    private Short meitId;
    private boolean consultaPorCategoria;
}
