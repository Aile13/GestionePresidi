package it.eliapitozzi.gestionepresidi.backend.exception;

/**
 * @author Elia
 */
public class PazienteNotFoundException extends RuntimeException {
    public PazienteNotFoundException(Long id) {
        super("Non posso trovare il paziente di id: " + id);
    }
}
