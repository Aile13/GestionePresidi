package it.eliapitozzi.gestionepresidi.backend.model;

import it.eliapitozzi.gestionepresidi.backend.repository.MovimentoRepository;
import it.eliapitozzi.gestionepresidi.backend.repository.PazienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.Date;

/**
 * @author Elia
 */
@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabasePaziente(PazienteRepository repository, MovimentoRepository movimentoRepository) {

        return args -> {
            Paziente p = repository.save(new Paziente("Pippo", "Via Leonardo", "Passirano", "+396565665 030655646845"));
            Paziente p2 = repository.save(new Paziente("Plut", "Via Gramsci", "Brescia", "+334562849"));
            log.info("Preloading " + p);
            log.info("Preloading " + p2);
            log.info("Preloading " + movimentoRepository.save(new Movimento("", "",
                    Date.from(Instant.now()), null, null, p
            )));
            log.info("Preloading " + movimentoRepository.save(new Movimento("", "",
                    Date.from(Instant.now()), null, null, p2
            )));
        };
    }
}
