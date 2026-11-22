package engtelecom.std.apirest.exceptions;

public class LampadaDesligadaException extends RuntimeException {
    public LampadaDesligadaException() {
        super("Não é possível ajustar o brilho com a lâmpada desligada");
    }
}