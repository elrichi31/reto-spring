package com.tcs.reto.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovimientoDTO {

    private Long id;

    @NotNull(message = "La fecha del movimiento es obligatoria")
    private LocalDateTime fecha;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Pattern(regexp = "RETIRO|DEPOSITO", message = "El tipo de movimiento debe ser 'RETIRO' o 'DEPOSITO'")
    private String tipoMovimiento;

    @NotNull(message = "El valor del movimiento es obligatorio")
    @Positive(message = "El valor del movimiento debe ser un número positivo")
    private Double valor;

    @NotNull(message = "Se debe especificar la cuenta")
    private CuentaRefDTO cuenta;

    @Data
    public static class CuentaRefDTO {
        @NotNull(message = "El ID de la cuenta es obligatorio")
        @Positive(message = "El ID de la cuenta debe ser un número positivo")
        private Long id;
    }
}
