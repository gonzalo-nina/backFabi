package org.example.crudpruebafabi.dto;

import java.util.Date;
import java.util.List;

public class PedidoDTO {
    private Long idPedido;
    private Long idCliente;
    private String nombreCliente;
    private Date fechaPedido;
    private boolean estadoPedido;
    private double subtotal;
    private List<ProductoPedidoDTO> productos;

    // Constructores
    public PedidoDTO() {}

    public PedidoDTO(Long idPedido, Long idCliente, String nombreCliente, Date fechaPedido, boolean estadoPedido, double subtotal, List<ProductoPedidoDTO> productos) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.fechaPedido = fechaPedido;
        this.estadoPedido = estadoPedido;
        this.subtotal = subtotal;
        this.productos = productos;
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

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
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

    public List<ProductoPedidoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoPedidoDTO> productos) {
        this.productos = productos;
    }
}
