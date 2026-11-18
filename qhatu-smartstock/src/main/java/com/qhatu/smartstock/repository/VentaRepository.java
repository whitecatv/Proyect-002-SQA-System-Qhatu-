package com.qhatu.smartstock.repository;

import com.qhatu.smartstock.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT v FROM Venta v ORDER BY v.fecha DESC")
    List<Venta> findUltimasVentas();

    @Query("SELECT DISTINCT v FROM Venta v " +
           "LEFT JOIN FETCH v.detalles d " +
           "LEFT JOIN FETCH d.producto " +
           "WHERE v.id = :id")
    Optional<Venta> findByIdWithDetalles(@Param("id") Long id);
}