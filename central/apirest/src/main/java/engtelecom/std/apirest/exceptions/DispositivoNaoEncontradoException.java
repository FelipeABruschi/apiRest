package engtelecom.std.apirest.exceptions;

public class DispositivoNaoEncontradoException extends RuntimeException {
    public DispositivoNaoEncontradoException(String nome) {
        super("Dispositivo não encontrado: " + nome);
    }
}