package com.tcs.reto.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Persona {

    private String contrasena;
    private Boolean estado;

    public Cliente(Long id, String nombre, String genero, Integer edad, String identificacion, String direccion, String telefono, String contrasena, Boolean estado) {
        super(id, nombre, genero, edad, identificacion, direccion, telefono);
        this.contrasena = contrasena;
        this.estado = estado;
    }
}


