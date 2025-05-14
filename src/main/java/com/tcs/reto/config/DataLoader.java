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

            // 1. Clientes
            if (clienteRepo.count() == 0) {
                Cliente c1 = clienteRepo.save(new Cliente(null, "Jose Lema", "Masculino", 30, "1234567890", "Otavalo sn y principal", "098254785", "1234", true));
                Cliente c2 = clienteRepo.save(new Cliente(null, "Marianela Montalvo", "Femenino", 28, "0987654321", "Amazonas y NNUU", "097548965", "5678", true));
                Cliente c3 = clienteRepo.save(new Cliente(null, "Juan Osorio", "Masculino", 35, "1122334455", "13 junio y Equinoccial", "098874587", "1245", true));
                System.out.println("🟢 Clientes insertados");

                // 2. Cuentas
                Cuenta cu1 = cuentaRepo.save(new Cuenta(null, "123456789", Cuenta.TipoCuenta.AHORROS, 500.0, true, c1));
                Cuenta cu2 = cuentaRepo.save(new Cuenta(null, "987654321", Cuenta.TipoCuenta.CORRIENTE, 1000.0, true, c2));
                System.out.println("🟢 Cuentas insertadas");

                // 3. Movimientos
                movimientoRepo.save(new Movimiento(null, LocalDateTime.now().minusDays(2), "Retiro", 100.0, 400.0, cu1));
                movimientoRepo.save(new Movimiento(null, LocalDateTime.now().minusDays(1), "Depósito", 200.0, 600.0, cu1));
                movimientoRepo.save(new Movimiento(null, LocalDateTime.now(), "Pago", 300.0, 700.0, cu2));
                System.out.println("🟢 Movimientos insertados");
            }
        };
    }
}
