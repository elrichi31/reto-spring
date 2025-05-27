package com.tcs.reto.service;

import com.tcs.reto.entity.Cuenta;
import com.tcs.reto.entity.Movimiento;
import com.tcs.reto.exception.BadRequestException;
import com.tcs.reto.repository.CuentaRepository;
import com.tcs.reto.repository.MovimientoRepository;
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
        if (movimiento.getValor() == null || movimiento.getValor() <= 0) {
            throw new BadRequestException("El valor del movimiento debe ser mayor a cero.");
        }

        if (movimiento.getTipoMovimiento() == null || movimiento.getTipoMovimiento().trim().isEmpty()) {
            throw new BadRequestException("Debe especificar el tipo de movimiento.");
        }

        Long cuentaId = movimiento.getCuenta() != null ? movimiento.getCuenta().getId() : null;
        if (cuentaId == null) {
            throw new BadRequestException("Debe especificar una cuenta válida para el movimiento.");
        }

        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new BadRequestException("Cuenta no encontrada."));

        Double saldoActual = cuenta.getSaldo();
        Double valor = movimiento.getValor();

        switch (movimiento.getTipoMovimiento().trim().toUpperCase()) {
            case "DEPOSITO":
                saldoActual += valor;
                break;
            case "RETIRO":
                if (saldoActual < valor) {
                    throw new BadRequestException("Saldo insuficiente para realizar el retiro.");
                }
                saldoActual -= valor;
                break;
            default:
                throw new BadRequestException("Tipo de movimiento no válido. Use 'DEPOSITO' o 'RETIRO'.");
        }

        cuenta.setSaldo(saldoActual);
        cuentaRepository.save(cuenta);

        movimiento.setCuenta(cuenta);
        movimiento.setSaldo(saldoActual);

        return movimientoRepository.save(movimiento);
    }

    public Movimiento editarMovimiento(Long id, Movimiento nuevo) {
        // ⚠️ En escenarios reales, editar movimientos puede afectar el balance
        // Aquí lo permitimos solo como demostración
        return movimientoRepository.findById(id)
                .map(m -> {
                    if (nuevo.getValor() == null || nuevo.getValor() <= 0) {
                        throw new BadRequestException("El valor del movimiento debe ser mayor a cero.");
                    }

                    m.setFecha(nuevo.getFecha());
                    m.setTipoMovimiento(nuevo.getTipoMovimiento());
                    m.setValor(nuevo.getValor());

                    if (nuevo.getCuenta() != null && nuevo.getCuenta().getId() != null) {
                        Cuenta cuenta = cuentaRepository.findById(nuevo.getCuenta().getId())
                                .orElseThrow(() -> new BadRequestException("Cuenta asociada no encontrada."));
                        m.setCuenta(cuenta);
                    }

                    return movimientoRepository.save(m);
                })
                .orElseThrow(() -> new BadRequestException("Movimiento no encontrado."));
    }

    public void eliminarMovimiento(Long id) {
        if (!movimientoRepository.existsById(id)) {
            throw new BadRequestException("No se puede eliminar: el movimiento no existe.");
        }
        movimientoRepository.deleteById(id);
    }
}
