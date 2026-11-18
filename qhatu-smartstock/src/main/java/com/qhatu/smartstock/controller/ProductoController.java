package com.qhatu.smartstock.controller;

import com.qhatu.smartstock.entity.Producto;
import com.qhatu.smartstock.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("active", "productos");
        model.addAttribute("productos", service.listar());
        return "productos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("active", "productos");
        model.addAttribute("producto", new Producto());
        return "producto-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto, RedirectAttributes ra) {
        service.guardar(producto);
        ra.addFlashAttribute("success", "Producto guardado correctamente.");
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("active", "productos");
        model.addAttribute("producto", service.buscar(id));
        return "producto-form";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        boolean ok = service.eliminar(id);
        if (ok) {
            ra.addFlashAttribute("success", "Producto eliminado correctamente.");
        } else {
            ra.addFlashAttribute("error",
                "No se puede eliminar el producto porque tiene ventas registradas.");
        }
        return "redirect:/productos";
    }
}