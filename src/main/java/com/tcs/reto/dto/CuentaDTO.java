package com.tcs.reto.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CuentaDTO {

    private Long id;

    @NotBlank(message = "El número de cuenta es obligatorio")
    @Size(min = 6, max = 20, message = "El número de cuenta debe tener entre 6 y 20 caracteres")
    private String numeroCuenta;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    @Pattern(regexp = "AHORROS|CORRIENTE", message = "El tipo de cuenta debe ser 'AHORROS' o 'CORRIENTE'")
    private String tipoCuenta;

    @NotNull(message = "El saldo inicial es obligatorio")
    @PositiveOrZero(message = "El saldo inicial no puede ser negativo")
    private Double saldoInicial;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    @NotNull(message = "Se debe especificar el cliente")
    private ClienteRefDTO cliente;

    @Data
    public static class ClienteRefDTO {
        @NotNull(message = "El ID del cliente es obligatorio")
        @Positive(message = "El ID del cliente debe ser un número positivo")
        private Long id;
    }
}
