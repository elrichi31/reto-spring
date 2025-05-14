package com.tcs.reto.service;

import com.tcs.reto.entity.Cliente;
import com.tcs.reto.entity.Cuenta;
import com.tcs.reto.exception.BadRequestException;
import com.tcs.reto.repository.CuentaRepository;
import com.tcs.reto.repository.ClienteRepository;

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
        if (cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta()).isPresent()) {
            throw new BadRequestException("Ya existe una cuenta con ese número.");
        }

        Long clienteId = cuenta.getCliente().getId();
        Cliente clienteCompleto = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado"));

        cuenta.setCliente(clienteCompleto);
        return cuentaRepository.save(cuenta);
    }

    public Cuenta editarCuenta(Long id, Cuenta cuentaActualizada) {
        return cuentaRepository.findById(id)
                .map(c -> {
                    c.setNumeroCuenta(cuentaActualizada.getNumeroCuenta());
                    c.setTipoCuenta(cuentaActualizada.getTipoCuenta());
                    c.setSaldoInicial(cuentaActualizada.getSaldoInicial());
                    c.setEstado(cuentaActualizada.getEstado());
                    c.setCliente(cuentaActualizada.getCliente());
                    return cuentaRepository.save(c);
                }).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    public void eliminarCuenta(Long id) {
        cuentaRepository.deleteById(id);
    }
}
