package org.example.crudpruebafabi.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_pedido")
    private Long idPedido;

    @ManyToOne // Relación Many-to-One con Cliente
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detallesPedido = new ArrayList<>();

    @Column(name = "fecha_pedido")
    @Temporal(TemporalType.DATE)
    private Date fechaPedido;

    @Column(name = "estado_pedido", columnDefinition = "BIT")
    private boolean estadoPedido;

    @Column(name = "subtotal")
    private double subtotal; // Nuevo campo

    public Pedido() {}

    public Pedido(Cliente cliente, Date fechaPedido, boolean estadoPedido) {
        this.cliente = cliente;
        this.fechaPedido = fechaPedido;
        this.estadoPedido = estadoPedido;
    }

    // Getters y setters
    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public boolean isEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(boolean estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    // Getters y Setters para detallesPedido
    public List<DetallePedido> getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(List<DetallePedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

}
