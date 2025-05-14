package com.tcs.reto.controller;

import com.tcs.reto.entity.Movimiento;
import com.tcs.reto.service.MovimientoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService service;

    public MovimientoController(MovimientoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Movimiento> listar() {
        return service.listarMovimientos();
    }

    @GetMapping("/{id}")
    public Movimiento obtener(@PathVariable Long id) {
        return service.buscarPorId(id).orElseThrow();
    }

    @PostMapping
    public Movimiento crear(@RequestBody Movimiento mov) {
        return service.guardarMovimiento(mov);
    }

    @PutMapping("/{id}")
    public Movimiento actualizar(@PathVariable Long id, @RequestBody Movimiento nuevo) {
        return service.editarMovimiento(id, nuevo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarMovimiento(id);
    }
}
