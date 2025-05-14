package com.tcs.reto.service;

import com.tcs.reto.entity.Cuenta;
import com.tcs.reto.entity.Movimiento;
import com.tcs.reto.repository.MovimientoRepository;
import com.tcs.reto.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    public MovimientoService(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public List<Movimiento> listarMovimientos() {
        return movimientoRepository.findAll();
    }

    public Optional<Movimiento> buscarPorId(Long id) {
        return movimientoRepository.findById(id);
    }

    public Movimiento guardarMovimiento(Movimiento movimiento) {
        Long cuentaId = movimiento.getCuenta().getId();
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        Double saldoActual = cuenta.getSaldoInicial();
        Double valor = movimiento.getValor();

        // Sumar o restar según tipo
        switch (movimiento.getTipoMovimiento().toUpperCase()) {
            case "DEPOSITO":
                saldoActual += valor;
                break;
            case "RETIRO":
                if (saldoActual < valor) {
                    throw new RuntimeException("Saldo insuficiente");
                }
                saldoActual -= valor;
                break;
            default:
                throw new RuntimeException("Tipo de movimiento no válido");
        }

        cuenta.setSaldoInicial(saldoActual);
        cuentaRepository.save(cuenta);

        movimiento.setCuenta(cuenta); // Actualizamos la relación
        return movimientoRepository.save(movimiento);
    }
    public Movimiento editarMovimiento(Long id, Movimiento nuevo) {
        return movimientoRepository.findById(id)
                .map(m -> {
                    m.setFecha(nuevo.getFecha());
                    m.setTipoMovimiento(nuevo.getTipoMovimiento());
                    m.setValor(nuevo.getValor());
                    m.setCuenta(nuevo.getCuenta());
                    return movimientoRepository.save(m);
                }).orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
    }

    public void eliminarMovimiento(Long id) {
        movimientoRepository.deleteById(id);
    }
}
