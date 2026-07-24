package com.hotel.room_service.domain.model;

import com.hotel.room_service.shared.constant.PedidoEventType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEvent<T> {
    private PedidoEventType type;
    private Long pedidoId;
    private Short haacId;
    private LocalDateTime eventDate;
    private T payload;
}
