package org.example.crudpruebafabi.dto;

import java.time.LocalDate;

public class PedidoDTO {
    private Long idPedido;
    private Long idCliente;
    private LocalDate fechaPedido;
    private boolean estadoPedido;
    private double subtotal;

    // Constructores
    public PedidoDTO() {
    }

    public PedidoDTO(Long idPedido, Long idCliente, LocalDate fechaPedido, boolean estadoPedido, double subtotal) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fechaPedido = fechaPedido;
        this.estadoPedido = estadoPedido;
        this.subtotal = subtotal;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
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

}
