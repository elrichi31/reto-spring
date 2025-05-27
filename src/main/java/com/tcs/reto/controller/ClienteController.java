package com.tcs.reto.controller;

import com.tcs.reto.dto.ClienteDTO;
import com.tcs.reto.entity.Cliente;
import com.tcs.reto.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Validated
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(service.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody ClienteDTO dto) {
        Cliente cliente = mapearDesdeDTO(dto);
        Cliente creado = service.crearCliente(cliente);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        Cliente cliente = mapearDesdeDTO(dto);
        Cliente actualizado = service.editarCliente(id, cliente);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    private Cliente mapearDesdeDTO(ClienteDTO dto) {
        return new Cliente(
                null, // El ID lo maneja Spring al actualizar
                dto.getNombre(),
                dto.getGenero(),
                dto.getEdad(),
                dto.getIdentificacion(),
                dto.getDireccion(),
                dto.getTelefono(),
                dto.getContrasena(),
                dto.getEstado()
        );
    }
    @PostMapping("/test")
    public ResponseEntity<String> testValidacion(@Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok("Validación OK");
    }

}
