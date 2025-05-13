package com.tcs.reto.config;

import com.tcs.reto.entity.Cliente;
import com.tcs.reto.repository.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ClienteRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Cliente(null, "Jose Lema", "Masculino", 30, "1234567890", "Otavalo sn y principal", "098254785", "1234", true));
                repository.save(new Cliente(null, "Marianela Montalvo", "Femenino", 28, "0987654321", "Amazonas y NNUU", "097548965", "5678", true));
                repository.save(new Cliente(null, "Juan Osorio", "Masculino", 35, "1122334455", "13 junio y Equinoccial", "098874587", "1245", true));
                System.out.println("🟢 Clientes insertados en la base de datos");
            }
        };
    }
}
