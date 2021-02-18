package it.eliapitozzi.gestionepresidi.backend.repository;

import it.eliapitozzi.gestionepresidi.backend.model.Movimento;
import it.eliapitozzi.gestionepresidi.backend.model.Paziente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Elia
 */
public interface MovimentoRepository extends JpaRepository<Movimento, Long> {
    List<Movimento> findAllByPaziente(Paziente paziente);
}
