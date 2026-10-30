package com.nexpoint.demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venta {
    private Integer idVenta;
    private Usuario usuario;
    private String metodoPago;
    private Date fechaVenta;
    private Double montoTotal;
    private String estadoVenta;
    private List<DetalleVenta> detalles = new ArrayList<>();

    public Venta() {
        this.fechaVenta = new Date();
        this.montoTotal = 0.0;
    }

    public Venta(Integer idVenta, Usuario usuario, String metodoPago, Date fechaVenta, Double montoTotal, String estadoVenta) {
        this.idVenta = idVenta;
        this.usuario = usuario;
        this.metodoPago = metodoPago;
        this.fechaVenta = fechaVenta;
        this.montoTotal = montoTotal;
        this.estadoVenta = estadoVenta;
    }

    public Integer getIdVenta() { return idVenta; }
    public void setIdVenta(Integer idVenta) { this.idVenta = idVenta; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public Date getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(Date fechaVenta) { this.fechaVenta = fechaVenta; }

    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }

    public String getEstadoVenta() { return estadoVenta; }
    public void setEstadoVenta(String estadoVenta) { this.estadoVenta = estadoVenta; }

    public List<DetalleVenta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }
}