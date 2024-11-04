package org.example.crudpruebafabi.model;

import jakarta.persistence.*;

@Entity
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_detalle_pedido")
    private Long idDetallePedido;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "precio_unitario")
    private double precioUnitario;

    @Column(name = "subtotal")
    private double subtotal;

    // Constructores, Getters, y Setters
    public DetallePedido() {}

    public DetallePedido(Pedido pedido, Long idProducto, int cantidad, double precioUnitario) {
        this.pedido = pedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    public Long getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(Long idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
