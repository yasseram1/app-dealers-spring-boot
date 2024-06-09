package com.app.appdealers.repository;

import com.app.appdealers.entity.Visita;
import com.app.appdealers.util.enums.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Integer> {

    @Query("SELECT v FROM Visita v WHERE v.comercio.id = :idComercio AND v.grupo.id = :idGrupo")
    Optional<Visita> findVisitByCommerceIdAndGroupId(@Param("idComercio") Integer idComercio, @Param("idGrupo") Integer idGrupo);

    @Query(value = "SELECT AVG(visitas_por_dia) AS promedio_visitas_por_dia " +
            "FROM ( " +
            "    SELECT fecha_visita, COUNT(*) AS visitas_por_dia " +
            "    FROM visitas v " +
            "    INNER JOIN usuarios u ON u.id = v.fk_usuario " +
            "    WHERE u.id = :idDealer AND v.estado = 'VISITADO' " +
            "    GROUP BY fecha_visita " +
            ") AS subconsulta",
            nativeQuery = true)
    Double obtenerPromedioComerciosVisitadosPorDia(@Param("idDealer") Integer idDealer);

    @Query(value = "SELECT COUNT(*) " +
            "FROM visitas v " +
            "WHERE v.estado = 'VISITADO' AND v.fk_usuario = :idUsuario",
            nativeQuery = true)
    Double obtenerCantidadComerciosVisitados(@Param("idUsuario") Integer idUsuario);

    @Query(value = "SELECT AVG(visitas_por_dia) AS promedio_visitas_por_dia " +
            "FROM ( " +
            "    SELECT fecha_visita, COUNT(*) AS visitas_por_dia " +
            "    FROM visitas v " +
            "    INNER JOIN usuarios u ON u.id = v.fk_usuario " +
            "    WHERE u.id = :idDealer AND v.respuesta = 'NUEVO_TRATO' " +
            "    GROUP BY fecha_visita " +
            ") AS subconsulta",
            nativeQuery = true)
    Double obtenerPromedioComercioAfiliados(@Param("idDealer") Integer idDealer);

    @Query(value = "SELECT COUNT(*) " +
            "FROM visitas v " +
            "WHERE v.respuesta = 'NUEVO_TRATO' AND v.fk_usuario = :idUsuario",
            nativeQuery = true)
    Double obtenerCantidadComerciosAfiliados(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT v FROM Visita v WHERE v.usuario.id = :idUsuario")
    List<Visita> obtenerHistorialVisitasParaDealer(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT v FROM Visita v WHERE v.usuario.id = :idUsuario AND v.respuesta = :respuesta")
    List<Visita> obtenerHistorialVisitasParaDealerPorRespuesta(@Param("idUsuario") Integer idUsuario, @Param("respuesta") Respuesta respuesta);
}
