package com.tcs.reto.controller;

import com.tcs.reto.entity.Cuenta;
import com.tcs.reto.service.CuentaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
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
    public Cuenta obtener(@PathVariable Long id) {
        return service.buscarPorId(id).orElseThrow();
    }

    @PostMapping
    public Cuenta crear(@RequestBody Cuenta cuenta) {
        return service.guardarCuenta(cuenta);
    }

    @PutMapping("/{id}")
    public Cuenta actualizar(@PathVariable Long id, @RequestBody Cuenta actualizada) {
        return service.editarCuenta(id, actualizada);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarCuenta(id);
    }
}
