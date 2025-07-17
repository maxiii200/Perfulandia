package com.perfulandia.cl.perfulandia.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String run;
    private String nombres;
    private String apellidos;

    private LocalDate fechaNacimiento;

    private String correo;
}
