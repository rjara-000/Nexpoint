package com.nexpoint.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @NotNull(message = "La categoría es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    private String subtitulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El precio de venta es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(name = "precio_venta")
    private Double precioVenta;

    @NotNull(message = "El stock actual es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock_actual")
    private Integer stockActual;

    private String imagen;

    @Column(name = "numero_rareza")
    private String numeroRareza;

    @Column(name = "tipo_hp_fase")
    private String tipoHpFase;

    @Column(name = "card_text", columnDefinition = "TEXT")
    private String cardText;

    private String ataque1;

    @Column(name = "desc_ataque1", columnDefinition = "TEXT")
    private String descAtaque1;

    private String ataque2;

    @Column(name = "debilidad_resistencia")
    private String debilidadResistencia;

    private String ilustrador;
    private String condicion;

    public Producto() {}

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSubtitulo() { return subtitulo; }
    public void setSubtitulo(String subtitulo) { this.subtitulo = subtitulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(Double precioVenta) { this.precioVenta = precioVenta; }

    public Integer getStockActual() { return stockActual; }
    public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public String getNumeroRareza() { return numeroRareza; }
    public void setNumeroRareza(String numeroRareza) { this.numeroRareza = numeroRareza; }

    public String getTipoHpFase() { return tipoHpFase; }
    public void setTipoHpFase(String tipoHpFase) { this.tipoHpFase = tipoHpFase; }

    public String getCardText() { return cardText; }
    public void setCardText(String cardText) { this.cardText = cardText; }

    public String getAtaque1() { return ataque1; }
    public void setAtaque1(String ataque1) { this.ataque1 = ataque1; }

    public String getDescAtaque1() { return descAtaque1; }
    public void setDescAtaque1(String descAtaque1) { this.descAtaque1 = descAtaque1; }

    public String getAtaque2() { return ataque2; }
    public void setAtaque2(String ataque2) { this.ataque2 = ataque2; }

    public String getDebilidadResistencia() { return debilidadResistencia; }
    public void setDebilidadResistencia(String debilidadResistencia) { this.debilidadResistencia = debilidadResistencia; }

    public String getIlustrador() { return ilustrador; }
    public void setIlustrador(String ilustrador) { this.ilustrador = ilustrador; }

    public String getCondicion() { return condicion; }
    public void setCondicion(String condicion) { this.condicion = condicion; }
}