package com.qhatu.smartstock.service;

import com.qhatu.smartstock.entity.Producto;
import com.qhatu.smartstock.repository.ProductoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto guardar(Producto p) {
        return repo.save(p);
    }

    public Producto buscar(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public boolean eliminar(Long id) {
        try {
            repo.deleteById(id);
            return true;
        } catch (DataIntegrityViolationException e) {
            // FK constraint: producto tiene ventas asociadas
            return false;
        }
    }

    public List<Producto> stockCritico() {
        return repo.findStockCritico();
    }
}