package it.eliapitozzi.gestionepresidi.backend.exception;

/**
 * @author Elia
 */
public class MovimentoNotFoundException extends RuntimeException {
    public MovimentoNotFoundException(Long id) {
        super("Non posso trovare il movimento di id: " + id);
    }
}
