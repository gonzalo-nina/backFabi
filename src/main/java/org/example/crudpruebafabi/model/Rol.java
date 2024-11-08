package org.example.crudpruebafabi.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_rol", nullable = false, unique = true)
    private Role nombreRol;
}
