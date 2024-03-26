package com.ar.vgmsistemas.database.dao.entity.view;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

@DatabaseView(
        value = "SELECT  MIN(prioridad) as prioridad, " +
                "        id_grupo_clie,id_articulos, " +
                "        MAX(rg_limite_inf) as limiteInf, " +
                "        MAX(rg_limite_sup) as limiteSup," +
                "        MIN(ta_dto) as taDto " +
                "FROM ( " +
                "SELECT 1 as prioridad,id_grupo_clie as id_grupo_clie," +
                "       articulos.id_articulos as id_articulos,  " +
                "       acciones_com_detalle.rg_limite_inf as rg_limite_inf, " +
                "       acciones_com_detalle.rg_limite_sup as rg_limite_sup, " +
                "       acciones_com_detalle.ta_dto as ta_dto " +
                "FROM   acciones_com_detalle, acciones_com, acciones_grupos, articulos " +
                "WHERE  acciones_com_detalle.id_acciones_com = acciones_com.id_acciones_com " +
                "       AND acciones_com.id_acciones_com = acciones_grupos.id_acciones_com " +
                "       AND acciones_com_detalle.id_articulos = articulos.id_articulos " +
                "       AND acciones_com.ti_origen = 'E' " +
                " UNION " +
                "SELECT 2," +
                "       acciones_grupos.id_grupo_clie, " +
                "       articulos.id_articulos,  " +
                "       acciones_com_detalle.rg_limite_inf, " +
                "       acciones_com_detalle.rg_limite_sup, " +
                "       acciones_com_detalle.ta_dto " +
                "FROM   acciones_com_detalle, acciones_com, acciones_grupos, articulos " +
                "WHERE  acciones_com_detalle.id_acciones_com = acciones_com.id_acciones_com " +
                "       AND acciones_com.id_acciones_com = acciones_grupos.id_acciones_com " +
                "       AND acciones_com_detalle.id_subrubro = articulos.id_subrubro " +
                "       AND acciones_com.ti_origen = 'E' " +
                "UNION " +
                "SELECT 3," +
                "       acciones_grupos.id_grupo_clie, " +
                "       articulos.id_articulos,  " +
                "       acciones_com_detalle.rg_limite_inf, " +
                "       acciones_com_detalle.rg_limite_sup, " +
                "       acciones_com_detalle.ta_dto " +
                "FROM   acciones_com_detalle, acciones_com, acciones_grupos, articulos " +
                "WHERE  acciones_com_detalle.id_acciones_com = acciones_com.id_acciones_com " +
                "       AND acciones_com.id_acciones_com = acciones_grupos.id_acciones_com " +
                "       AND acciones_com_detalle.id_segmento = articulos.id_segmento " +
                "       AND acciones_com.ti_origen = 'E' " +
                "UNION " +
                "SELECT 4, " +
                "       acciones_grupos.id_grupo_clie, " +
                "       articulos.id_articulos,  " +
                "       acciones_com_detalle.rg_limite_inf, " +
                "       acciones_com_detalle.rg_limite_sup, " +
                "       acciones_com_detalle.ta_dto " +
                "FROM   acciones_com_detalle, acciones_com, acciones_grupos, articulos " +
                "WHERE  acciones_com_detalle.id_acciones_com = acciones_com.id_acciones_com " +
                "       AND acciones_com.id_acciones_com = acciones_grupos.id_acciones_com " +
                "       AND acciones_com_detalle.id_negocio = articulos.id_negocio " +
                "       AND acciones_com.ti_origen = 'E' " +
                "UNION " +
                "SELECT 5, " +
                "       acciones_grupos.id_grupo_clie, " +
                "       articulos.id_articulos,  " +
                "       acciones_com_detalle.rg_limite_inf, " +
                "       acciones_com_detalle.rg_limite_sup, " +
                "       acciones_com_detalle.ta_dto " +
                "FROM   acciones_com_detalle, acciones_com, acciones_grupos, articulos " +
                "WHERE  acciones_com_detalle.id_acciones_com = acciones_com.id_acciones_com " +
                "       AND acciones_com.id_acciones_com = acciones_grupos.id_acciones_com " +
                "       AND acciones_com_detalle.id_linea = articulos.id_linea " +
                "       AND acciones_com.ti_origen = 'E'   ) t " +
                "GROUP BY id_grupo_clie,id_articulos",
        viewName = "v_accionesComMax_Emp"
)
//@Entity(tableName = "v_accionesComMax_Emp", primaryKeys = {"id_grupo_clie","id_articulos"})
public class AccionesComMaxEmpViewBd {
    @ColumnInfo(name = "prioridad")
    public int prioridad;

    @ColumnInfo(name = "id_grupo_clie")
    public int idGrupoClie;

    @ColumnInfo(name = "id_articulos")
    public int idArticulos;

    @ColumnInfo(name = "limiteInf")
    public double limiteInf;

    @ColumnInfo(name = "limiteSup")
    public double limiteSup;

    @ColumnInfo(name = "taDto")
    public double taDto;

}
