package it.eliapitozzi.gestionepresidi.backend.repository;

import it.eliapitozzi.gestionepresidi.backend.model.Paziente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Elia
 */
public interface PazienteRepository extends JpaRepository<Paziente, Long> {
}
