package com.nexpoint.demo.dto;

public class ProductoResumenDTO {

    private Integer idProducto;
    private String nombreProducto;
    private Double precioVenta;
    private Integer stockActual;
    private String nombreCategoria;

    public ProductoResumenDTO(Integer idProducto, String nombreProducto, Double precioVenta, Integer stockActual, String nombreCategoria) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioVenta = precioVenta;
        this.stockActual = stockActual;
        this.nombreCategoria = nombreCategoria;
    }

    public Integer getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public Double getPrecioVenta() { return precioVenta; }
    public Integer getStockActual() { return stockActual; }
    public String getNombreCategoria() { return nombreCategoria; }
}