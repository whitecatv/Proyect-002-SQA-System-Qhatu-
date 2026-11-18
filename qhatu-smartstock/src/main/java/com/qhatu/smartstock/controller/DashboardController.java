package com.qhatu.smartstock.controller;

import com.qhatu.smartstock.entity.Producto;
import com.qhatu.smartstock.entity.Venta;
import com.qhatu.smartstock.service.ProductoService;
import com.qhatu.smartstock.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final ProductoService productoService;
    private final VentaService ventaService;

    public DashboardController(ProductoService productoService, VentaService ventaService) {
        this.productoService = productoService;
        this.ventaService = ventaService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Venta> ventasHoy = ventaService.ventasDelDia();
        List<Producto> criticos = productoService.stockCritico();

        model.addAttribute("active", "dashboard");
        model.addAttribute("totalProductos", productoService.listar().size());
        model.addAttribute("totalVentasHoy", ventasHoy.size());
        model.addAttribute("ingresosHoy", ventaService.totalIngresosHoy());
        model.addAttribute("totalCriticos", criticos.size());
        model.addAttribute("stockCritico", criticos);
        model.addAttribute("ventasHoy", ventasHoy);

        return "dashboard";
    }
}