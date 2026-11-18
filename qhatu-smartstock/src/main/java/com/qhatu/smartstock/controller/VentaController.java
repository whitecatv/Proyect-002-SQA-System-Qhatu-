package com.qhatu.smartstock.controller;

import com.qhatu.smartstock.entity.Venta;
import com.qhatu.smartstock.service.ProductoService;
import com.qhatu.smartstock.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final ProductoService productoService;
    private final VentaService ventaService;

    public VentaController(ProductoService productoService, VentaService ventaService) {
        this.productoService = productoService;
        this.ventaService = ventaService;
    }

    @GetMapping
    public String ventas(Model model) {
        model.addAttribute("active", "ventas");
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("ventasHoy", ventaService.ventasDelDia());
        return "ventas";
    }

    @PostMapping("/registrar")
    public String registrar(@RequestParam Map<String, String> params) {
        Map<Long, Integer> carrito = new HashMap<>();
        for (Map.Entry<String, String> e : params.entrySet()) {
            if (e.getKey().startsWith("cant_")) {
                Long id = Long.parseLong(e.getKey().substring(5));
                int cant = Integer.parseInt(e.getValue());
                if (cant > 0) carrito.put(id, cant);
            }
        }
        if (!carrito.isEmpty()) {
            Venta venta = ventaService.registrarVenta(carrito);
            // Ir directo al comprobante para imprimir
            return "redirect:/ventas/comprobante/" + venta.getId();
        }
        return "redirect:/ventas";
    }

    @GetMapping("/comprobante/{id}")
    public String comprobante(@PathVariable Long id, Model model) {
        model.addAttribute("active", "ventas");
        model.addAttribute("venta", ventaService.buscarPorId(id));
        return "comprobante";
    }
}