package com.qhatu.smartstock.service;

import com.qhatu.smartstock.entity.*;
import com.qhatu.smartstock.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class VentaService {

    private final VentaRepository ventaRepo;
    private final DetalleVentaRepository detalleRepo;
    private final ProductoRepository productoRepo;

    public VentaService(VentaRepository ventaRepo,
                        DetalleVentaRepository detalleRepo,
                        ProductoRepository productoRepo) {
        this.ventaRepo = ventaRepo;
        this.detalleRepo = detalleRepo;
        this.productoRepo = productoRepo;
    }

    @Transactional
    public Venta registrarVenta(Map<Long, Integer> carrito) {
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());

        double total = 0;
        List<DetalleVenta> detalles = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : carrito.entrySet()) {
            Producto p = productoRepo.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            int cant = entry.getValue();

            if (p.getStock() < cant) {
                throw new RuntimeException("Stock insuficiente para " + p.getNombre());
            }

            p.setStock(p.getStock() - cant);
            productoRepo.save(p);

            DetalleVenta d = new DetalleVenta();
            d.setVenta(venta);
            d.setProducto(p);
            d.setCantidad(cant);
            d.setSubtotal(p.getPrecio() * cant);

            total += d.getSubtotal();
            detalles.add(d);
        }

        venta.setTotal(total);
        venta.setDetalles(detalles);
        return ventaRepo.save(venta);
    }

    public List<Venta> ventasDelDia() {
        LocalDateTime inicio = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fin    = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return ventaRepo.findByFechaBetween(inicio, fin);
    }

    public double totalIngresosHoy() {
        return ventasDelDia().stream().mapToDouble(Venta::getTotal).sum();
    }

    public Venta buscarPorId(Long id) {
        return ventaRepo.findByIdWithDetalles(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada: " + id));
    }
}