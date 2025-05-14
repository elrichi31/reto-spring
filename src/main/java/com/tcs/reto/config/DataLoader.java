package com.tcs.reto.config;

import com.tcs.reto.entity.Cliente;
import com.tcs.reto.entity.Cuenta;
import com.tcs.reto.entity.Movimiento;
import com.tcs.reto.repository.ClienteRepository;
import com.tcs.reto.repository.CuentaRepository;
import com.tcs.reto.repository.MovimientoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(
            ClienteRepository clienteRepo,
            CuentaRepository cuentaRepo,
            MovimientoRepository movimientoRepo
    ) {
        return args -> {
            if (clienteRepo.count() == 0) {

                // 1. Insertar clientes
                Cliente c1 = clienteRepo.save(new Cliente(null, "Jose Lema", "Masculino", 30, "1234567890", "Otavalo sn y principal", "098254785", "1234", true));
                Cliente c2 = clienteRepo.save(new Cliente(null, "Marianela Montalvo", "Femenino", 28, "0987654321", "Amazonas y NNUU", "097548965", "5678", true));
                Cliente c3 = clienteRepo.save(new Cliente(null, "Juan Osorio", "Masculino", 35, "1122334455", "13 junio y Equinoccial", "098874587", "1245", true));
                System.out.println("🟢 Clientes insertados");

                // 2. Insertar cuentas con saldo inicial en cero
                Cuenta cu1 = cuentaRepo.save(new Cuenta(null, "123456789", Cuenta.TipoCuenta.AHORROS, 0.0, true, c1));
                Cuenta cu2 = cuentaRepo.save(new Cuenta(null, "987654321", Cuenta.TipoCuenta.CORRIENTE, 0.0, true, c2));
                System.out.println("🟢 Cuentas insertadas");

                // 3. Movimientos para cu1
                Double saldo1 = cu1.getSaldoInicial();
                Movimiento m1 = new Movimiento(null, LocalDateTime.now().minusDays(2), "RETIRO", 100.0, cu1);
                saldo1 -= 100.0;
                cu1.setSaldoInicial(saldo1);
                cuentaRepo.save(cu1);
                movimientoRepo.save(m1);

                Movimiento m2 = new Movimiento(null, LocalDateTime.now().minusDays(1), "DEPOSITO", 200.0, cu1);
                saldo1 += 200.0;
                cu1.setSaldoInicial(saldo1);
                cuentaRepo.save(cu1);
                movimientoRepo.save(m2);

                // 4. Movimiento para cu2
                Double saldo2 = cu2.getSaldoInicial();
                Movimiento m3 = new Movimiento(null, LocalDateTime.now(), "PAGO", 300.0, cu2);
                saldo2 -= 300.0;
                cu2.setSaldoInicial(saldo2);
                cuentaRepo.save(cu2);
                movimientoRepo.save(m3);

                System.out.println("🟢 Movimientos insertados y saldos actualizados");
            }
        };
    }
}