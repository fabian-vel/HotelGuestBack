SELECT MEIT.meit_id,
       MEIT.meit_nombre,
       MEIT.meit_descripcion,
       MEIT.meit_precio,
       MEIT.meit_imagen_url,
       MEIT.meca_id,
       MEIT.meca_parent_id
FROM TBL_MENU_ITEMS MEIT
WHERE MEIT.meca_id = :MECA_ID
  AND MEIT.meit_estado = 'A'