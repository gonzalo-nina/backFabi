package org.example.crudpruebafabi.model;

import jakarta.persistence.*;

@Entity
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_catalogo")
    private Long idCatalogo;

    @Column(name = "nombre_catalogo")
    private String nombreCatalogo;

    @Column(name = "descripcion_catalogo")
    private String descripcionCatalogo;

    public Catalogo() {}

    public Catalogo(String nombreCatalogo, String descripcionCatalogo) {
        this.nombreCatalogo = nombreCatalogo;
        this.descripcionCatalogo = descripcionCatalogo;
    }

    public Long getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Long idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getNombreCatalogo() {
        return nombreCatalogo;
    }

    public void setNombreCatalogo(String nombreCatalogo) {
        this.nombreCatalogo = nombreCatalogo;
    }

    public String getDescripcionCatalogo() {
        return descripcionCatalogo;
    }

    public void setDescripcionCatalogo(String descripcionCatalogo) {
        this.descripcionCatalogo = descripcionCatalogo;
    }
}
