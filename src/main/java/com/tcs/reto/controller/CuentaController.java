package com.tcs.reto.controller;

import com.tcs.reto.dto.CuentaDTO;
import com.tcs.reto.entity.Cliente;
import com.tcs.reto.entity.Cuenta;
import com.tcs.reto.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@Validated
public class CuentaController {

    private final CuentaService service;

    public CuentaController(CuentaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cuenta> listar() {
        return service.listarCuentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cuenta> crear(@Valid @RequestBody CuentaDTO dto) {
        Cuenta cuenta = mapearDesdeDTO(dto);
        Cuenta creada = service.guardarCuenta(cuenta);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizar(@PathVariable Long id, @Valid @RequestBody CuentaDTO dto) {
        Cuenta cuenta = mapearDesdeDTO(dto);
        cuenta.setId(id);
        Cuenta actualizada = service.editarCuenta(id, cuenta);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }

    private Cuenta mapearDesdeDTO(CuentaDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getCliente().getId());

        return new Cuenta(
                null,
                dto.getNumeroCuenta(),
                Cuenta.TipoCuenta.valueOf(dto.getTipoCuenta()),
                dto.getSaldoInicial(),
                dto.getSaldoInicial(),
                dto.getEstado(),
                cliente
        );
    }
}
