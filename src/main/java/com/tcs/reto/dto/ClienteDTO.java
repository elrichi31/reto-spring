package com.tcs.reto.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class ClienteDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El género es obligatorio")
    @Pattern(regexp = "Masculino|Femenino", message = "El género debe ser 'Masculino' o 'Femenino'")
    private String genero;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 120, message = "La edad no puede ser mayor a 120")
    private Integer edad;

    @NotBlank(message = "La identificación es obligatoria")
    @Size(min = 6, max = 20, message = "La identificación debe tener entre 6 y 20 caracteres")
    private String identificacion;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 150, message = "La dirección no debe superar los 150 caracteres")
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\d{7,15}$", message = "El teléfono debe contener entre 7 y 15 dígitos")
    private String telefono;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, max = 20, message = "La contraseña debe tener entre 4 y 20 caracteres")
    private String contrasena;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}
