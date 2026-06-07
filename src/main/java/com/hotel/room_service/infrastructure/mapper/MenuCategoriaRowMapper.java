package com.hotel.room_service.infrastructure.mapper;

import com.hotel.room_service.domain.model.MenuCategoria;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class MenuCategoriaRowMapper implements RowMapper<MenuCategoria> {

    private final MenuCategoriaMapper mapper;

    @Override
    public MenuCategoria mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapper.toDomain(
                rs.getShort("meca_id"),
                rs.getString("meca_nombre"),
                rs.getString("meca_descripcion"),
                rs.getString("meca_imagen_url"),
                rs.getObject("meca_parent_id", Short.class)
        );
    }
}

