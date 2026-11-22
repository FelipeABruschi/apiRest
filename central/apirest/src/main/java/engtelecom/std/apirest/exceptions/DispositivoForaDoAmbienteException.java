package engtelecom.std.apirest.exceptions;

public class DispositivoForaDoAmbienteException extends RuntimeException {
    public DispositivoForaDoAmbienteException(String ambiente, String dispositivo) {
        super("O dispositivo " + dispositivo + " não está no ambiente " + ambiente);
    }
}