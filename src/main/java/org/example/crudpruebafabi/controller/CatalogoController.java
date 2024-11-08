package org.example.crudpruebafabi.controller;

import org.example.crudpruebafabi.model.Catalogo;
import org.example.crudpruebafabi.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/admin/catalogos")
public class CatalogoController {
    @Autowired

    private CatalogoService catalogoService;
    @GetMapping
    public List<Catalogo> listarCatalogos() {
        return catalogoService.listarCatalogos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Catalogo> buscarCatalogoPorId(@PathVariable Long id) {
        return catalogoService.obtenerCatalogoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Catalogo guardarCatalogo(@RequestBody Catalogo catalogo) {
        return catalogoService.guardarCatalogo(catalogo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Catalogo> actualizarCatalogo(@PathVariable Long id, @RequestBody Catalogo catalogo) {
        if (!catalogoService.obtenerCatalogoPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        catalogo.setIdCatalogo(id);
        return ResponseEntity.ok(catalogoService.actualizarCatalogo(catalogo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCatalogo(@PathVariable Long id) {
        if (!catalogoService.obtenerCatalogoPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        catalogoService.eliminarCatalogo(id);
        return ResponseEntity.noContent().build();
    }

}
