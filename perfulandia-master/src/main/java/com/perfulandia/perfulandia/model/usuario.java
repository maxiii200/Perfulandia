package com.perfulandia.perfulandia.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity

    @Table(name= "usuario")

    @Data

    @NoArgsConstructor

    @AllArgsConstructor
public class usuario {

        @Id

        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Integer id;

        @Column(unique=true, length = 13, nullable=false)

        private String run;

        @Column(nullable=false)

        private String nombres;

        @Column(nullable=false)

        private String apellidos;

        @Column(nullable=true)

        private Date fechaNacimiento;

        @Column(nullable=false)

        private String correo;

    }
