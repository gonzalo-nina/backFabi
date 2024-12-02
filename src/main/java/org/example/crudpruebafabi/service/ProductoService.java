package org.example.crudpruebafabi.service;

import org.example.crudpruebafabi.model.Producto;
import org.example.crudpruebafabi.model.Catalogo;
import org.example.crudpruebafabi.repository.ProductoRepository;
import org.example.crudpruebafabi.repository.CatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CatalogoRepository catalogoRepository;

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerProductoPorId(Long idProducto) {
        return productoRepository.findById(idProducto);
    }

    @Transactional
    public Producto guardarProducto(Producto producto) {
        Optional<Catalogo> catalogo = catalogoRepository.findById(producto.getIdCatalogo());
        if (!catalogo.isPresent()) {
            throw new IllegalArgumentException("El catálogo con ID " + producto.getIdCatalogo() + " no existe.");
        }
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizarProducto(Producto producto) {
        Optional<Catalogo> catalogo = catalogoRepository.findById(producto.getIdCatalogo());
        if (!catalogo.isPresent()) {
            throw new IllegalArgumentException("El catálogo con ID " + producto.getIdCatalogo() + " no existe.");
        }
        return productoRepository.save(producto);
    }

    @Transactional
    public void eliminarProducto(Long idProducto) {
        productoRepository.deleteById(idProducto);
    }
}
