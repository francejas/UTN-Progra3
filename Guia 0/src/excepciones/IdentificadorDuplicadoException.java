package excepciones;

public class IdentificadorDuplicadoException extends RuntimeException {
    public IdentificadorDuplicadoException(String message) {
        super(message);
    }
}
