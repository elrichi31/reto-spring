package com.tcs.reto.service;

import com.tcs.reto.entity.Cliente;
import com.tcs.reto.entity.Cuenta;
import com.tcs.reto.exception.BadRequestException;
import com.tcs.reto.repository.ClienteRepository;
import com.tcs.reto.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    public CuentaService(CuentaRepository cuentaRepository, ClienteRepository clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Cuenta> listarCuentas() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> buscarPorId(Long id) {
        return cuentaRepository.findById(id);
    }

    public Cuenta guardarCuenta(Cuenta cuenta) {
        // Validaciones básicas
        if (cuenta.getNumeroCuenta() == null || cuenta.getNumeroCuenta().trim().isEmpty()) {
            throw new BadRequestException("El número de cuenta no puede estar vacío.");
        }

        if (cuenta.getSaldoInicial() == null || cuenta.getSaldoInicial() < 0) {
            throw new BadRequestException("El saldo inicial no puede ser negativo ni nulo.");
        }

        if (cuenta.getTipoCuenta() == null) {
            throw new BadRequestException("El tipo de cuenta es obligatorio.");
        }

        if (cuenta.getCliente() == null || cuenta.getCliente().getId() == null) {
            throw new BadRequestException("Debe asignar un cliente válido a la cuenta.");
        }

        // Validar existencia de cliente
        Cliente cliente = clienteRepository.findById(cuenta.getCliente().getId())
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado."));

        // Validar que no se repita el número de cuenta
        if (cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta()).isPresent()) {
            throw new BadRequestException("Ya existe una cuenta con ese número.");
        }

        cuenta.setCliente(cliente);
        cuenta.setSaldo(cuenta.getSaldoInicial());
        return cuentaRepository.save(cuenta);
    }

    public Cuenta editarCuenta(Long id, Cuenta cuentaActualizada) {
        return cuentaRepository.findById(id)
                .map(c -> {
                    if (!c.getNumeroCuenta().equals(cuentaActualizada.getNumeroCuenta())) {
                        // Verifica si el nuevo número ya existe
                        cuentaRepository.findByNumeroCuenta(cuentaActualizada.getNumeroCuenta())
                                .ifPresent(existing -> {
                                    throw new BadRequestException("Ya existe otra cuenta con ese número.");
                                });
                    }

                    if (cuentaActualizada.getSaldoInicial() != null && cuentaActualizada.getSaldoInicial() < 0) {
                        throw new BadRequestException("El saldo inicial no puede ser negativo.");
                    }

                    c.setNumeroCuenta(cuentaActualizada.getNumeroCuenta());
                    c.setTipoCuenta(cuentaActualizada.getTipoCuenta());
                    c.setSaldoInicial(cuentaActualizada.getSaldoInicial());
                    c.setEstado(cuentaActualizada.getEstado());

                    if (cuentaActualizada.getCliente() != null &&
                            cuentaActualizada.getCliente().getId() != null) {
                        Cliente cliente = clienteRepository.findById(cuentaActualizada.getCliente().getId())
                                .orElseThrow(() -> new BadRequestException("Cliente no encontrado."));
                        c.setCliente(cliente);
                    }

                    return cuentaRepository.save(c);
                })
                .orElseThrow(() -> new BadRequestException("Cuenta no encontrada."));
    }

    public void eliminarCuenta(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new BadRequestException("No se puede eliminar: cuenta no encontrada.");
        }
        cuentaRepository.deleteById(id);
    }
}
