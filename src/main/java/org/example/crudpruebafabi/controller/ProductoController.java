package org.example.crudpruebafabi.controller;

import org.example.crudpruebafabi.model.Producto;
import org.example.crudpruebafabi.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        if (!productoService.obtenerProductoPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        producto.setIdProducto(id);
        return ResponseEntity.ok(productoService.actualizarProducto(producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (!productoService.obtenerProductoPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
