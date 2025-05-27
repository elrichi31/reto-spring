package com.tcs.reto.controller;

import com.tcs.reto.dto.MovimientoDTO;
import com.tcs.reto.entity.Cuenta;
import com.tcs.reto.entity.Movimiento;
import com.tcs.reto.service.CuentaService;
import com.tcs.reto.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@Validated
public class MovimientoController {

    private final MovimientoService service;
    private final CuentaService cuentaService;

    public MovimientoController(MovimientoService service, CuentaService cuentaService) {
        this.service = service;
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public List<Movimiento> listar() {
        return service.listarMovimientos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Movimiento> crear(@Valid @RequestBody MovimientoDTO dto) {
        Movimiento mov = mapearDesdeDTO(dto);
        Movimiento guardado = service.guardarMovimiento(mov);
        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimiento> actualizar(@PathVariable Long id, @Valid @RequestBody MovimientoDTO dto) {
        Movimiento mov = mapearDesdeDTO(dto);
        mov.setId(id);
        Movimiento actualizado = service.editarMovimiento(id, mov);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }

    private Movimiento mapearDesdeDTO(MovimientoDTO dto) {
        Cuenta cuenta = cuentaService.buscarPorId(dto.getCuenta().getId())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        Movimiento mov = new Movimiento();
        mov.setFecha(dto.getFecha());
        mov.setTipoMovimiento(dto.getTipoMovimiento().toUpperCase());
        mov.setValor(dto.getValor());
        mov.setCuenta(cuenta);
        return mov;
    }
}
