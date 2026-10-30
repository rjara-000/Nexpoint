package com.nexpoint.demo.model;

public class DetalleVenta {
    private Integer idDetalleVenta;
    private Producto producto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;

    public DetalleVenta() {}

    public DetalleVenta(Integer idDetalleVenta, Producto producto, Integer cantidad, Double precioUnitario) {
        this.idDetalleVenta = idDetalleVenta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    public Integer getIdDetalleVenta() { return idDetalleVenta; }
    public void setIdDetalleVenta(Integer idDetalleVenta) { this.idDetalleVenta = idDetalleVenta; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { 
        this.cantidad = cantidad; 
        if (this.precioUnitario != null) this.subtotal = cantidad * this.precioUnitario;
    }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { 
        this.precioUnitario = precioUnitario; 
        if (this.cantidad != null) this.subtotal = this.cantidad * precioUnitario;
    }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
}
