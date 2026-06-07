package com.hotel.room_service.shared.util;

import com.hotel.room_service.domain.exception.SqlLoadException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class SqlLoaderUtil {
    public static String load(String path) {
        return Optional.of(path)
                .map(ClassPathResource::new)
                .map(resource -> {
                    try {
                        return StreamUtils.copyToString(
                                resource.getInputStream(),
                                StandardCharsets.UTF_8
                        );
                    } catch (IOException e) {
                        throw new SqlLoadException(path, e);
                    }
                })
                .orElseThrow(() -> new SqlLoadException(path, null));
    }
}
