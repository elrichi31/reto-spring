package com.tcs.reto.service;

import com.tcs.reto.entity.Cliente;
import com.tcs.reto.exception.BadRequestException;
import com.tcs.reto.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente crearCliente(Cliente cliente) {
        if (clienteRepository.findByIdentificacion(cliente.getIdentificacion()).isPresent()) {
            throw new BadRequestException("Ya existe un cliente con la misma identificación.");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente editarCliente(Long id, Cliente clienteActualizado) {
        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado con ID: " + id));

        // Validar si se intenta cambiar la identificación a una ya usada por otro
        clienteRepository.findByIdentificacion(clienteActualizado.getIdentificacion())
                .filter(c -> !c.getId().equals(id))
                .ifPresent(c -> {
                    throw new BadRequestException("Ya existe otro cliente con la misma identificación.");
                });

        // Aplicar cambios
        existente.setNombre(clienteActualizado.getNombre());
        existente.setGenero(clienteActualizado.getGenero());
        existente.setEdad(clienteActualizado.getEdad());
        existente.setIdentificacion(clienteActualizado.getIdentificacion());
        existente.setDireccion(clienteActualizado.getDireccion());
        existente.setTelefono(clienteActualizado.getTelefono());
        existente.setContrasena(clienteActualizado.getContrasena());
        existente.setEstado(clienteActualizado.getEstado());

        return clienteRepository.save(existente);
    }

    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new BadRequestException("No se puede eliminar: cliente no encontrado con ID " + id);
        }
        clienteRepository.deleteById(id);
    }
}
