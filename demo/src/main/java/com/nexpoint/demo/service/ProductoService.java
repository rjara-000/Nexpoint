package com.nexpoint.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nexpoint.demo.model.Categoria;
import com.nexpoint.demo.model.Producto;
import com.nexpoint.demo.repository.CategoriaRepository;
import com.nexpoint.demo.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    // Método con paginación para el catálogo
    public Page<Producto> listarPaginado(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    public Page<Producto> buscarPorNombrePaginado(String nombre, Pageable pageable) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre, pageable);
    }

    public Page<Producto> buscarPorCategoriaPaginado(Integer idCategoria, Pageable pageable) {
        return productoRepository.findByCategoria_IdCategoria(idCategoria, pageable);
    }

    public Optional<Producto> buscarPorId(Integer id) {
        return productoRepository.findById(id);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarCategoriaPorId(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Page<Producto> buscarPorCategoriaIdPaginado(Integer idCategoria, Pageable pageable) {
    return productoRepository.findByCategoria_IdCategoria(idCategoria, pageable);
    }
}