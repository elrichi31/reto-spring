package com.tcs.reto.service;

import com.tcs.reto.entity.Movimiento;
import com.tcs.reto.repository.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {

    private final MovimientoRepository repository;

    public MovimientoService(MovimientoRepository repository) {
        this.repository = repository;
    }

    public List<Movimiento> listarMovimientos() {
        return repository.findAll();
    }

    public Optional<Movimiento> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Movimiento guardarMovimiento(Movimiento mov) {
        return repository.save(mov);
    }

    public Movimiento editarMovimiento(Long id, Movimiento nuevo) {
        return repository.findById(id)
                .map(m -> {
                    m.setFecha(nuevo.getFecha());
                    m.setTipoMovimiento(nuevo.getTipoMovimiento());
                    m.setValor(nuevo.getValor());
                    m.setSaldo(nuevo.getSaldo());
                    m.setCuenta(nuevo.getCuenta());
                    return repository.save(m);
                }).orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
    }

    public void eliminarMovimiento(Long id) {
        repository.deleteById(id);
    }
}
