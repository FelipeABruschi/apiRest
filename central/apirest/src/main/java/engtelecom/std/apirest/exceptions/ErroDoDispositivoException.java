package engtelecom.std.apirest.exceptions;

public class ErroDoDispositivoException extends RuntimeException {
    private final int status;

    public ErroDoDispositivoException(int status, String mensagem) {
        super(mensagem);
        this.status = status;
    }

    public int getStatus() { return status; }
}