package engtelecom.std.apirest.exceptions;

public class TvDesligadaException extends RuntimeException {
    public TvDesligadaException() {
        super("Não é possível alterar volume ou canal com a TV desligada");
    }
}