package com.nexpoint.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nexpoint.demo.dto.ProductoResumenDTO;
import com.nexpoint.demo.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("SELECT new com.nexpoint.demo.dto.ProductoResumenDTO(p.idProducto, p.nombre, p.precioVenta, p.stockActual, c.nombre) " +
           "FROM Producto p JOIN p.categoria c")
    List<ProductoResumenDTO> obtenerResumenMultitabla();

    @Query("SELECT p FROM Producto p WHERE p.categoria.idCategoria = :idCategoria")
    List<Producto> buscarPorCategoria(@Param("idCategoria") Integer idCategoria);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    // Nuevos métodos para Paginación
    Page<Producto> findAll(Pageable pageable);
    
    Page<Producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    
    Page<Producto> findByCategoria_IdCategoria(Integer idCategoria, Pageable pageable);
}
