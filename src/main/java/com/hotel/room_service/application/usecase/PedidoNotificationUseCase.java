package com.hotel.room_service.application.usecase;

import com.hotel.room_service.domain.model.PedidoEvent;
import com.hotel.room_service.shared.constant.PedidoEventType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.LocalDateTime;

@Service
public class PedidoNotificationUseCase {

    private final Sinks.Many<PedidoEvent<?>> sink = Sinks.many().multicast().directBestEffort();

    public Flux<PedidoEvent<?>> events() {
        return sink.asFlux();
    }

    public void publish(PedidoEvent<?> event) {
        sink.tryEmitNext(event);
    }

    public void publishCreated(Long pedidoId) {

        publish(
                PedidoEvent.builder()
                        .type(PedidoEventType.CREATED)
                        .pedidoId(pedidoId)
                        .eventDate(LocalDateTime.now())
                        .build()
        );

    }
}
