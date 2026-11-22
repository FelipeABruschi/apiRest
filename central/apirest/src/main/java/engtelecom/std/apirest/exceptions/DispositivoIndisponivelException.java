package engtelecom.std.apirest.exceptions;

public class DispositivoIndisponivelException extends RuntimeException {
    public DispositivoIndisponivelException(String dispositivo) {
        super("O dispositivo " + dispositivo + " está indisponível");
    }
}