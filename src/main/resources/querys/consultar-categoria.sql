SELECT
    meca_id,
    meca_nombre,
    meca_descripcion,
    meca_imagen_url,
    meca_parent_id
FROM mst_menu_categorias
WHERE meca_estado = 'A'
  AND (
    (CAST(? AS VARCHAR) IS NULL AND meca_parent_id IS NULL)
        OR
    (CAST(? AS VARCHAR) IS NOT NULL AND meca_parent_id = ANY(string_to_array(?, ',')::SMALLINT[]))
    )
ORDER BY meca_nombre