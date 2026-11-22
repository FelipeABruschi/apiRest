package engtelecom.std.apirest.exceptions;

public class AmbienteNaoEncontradoException extends RuntimeException {
    public AmbienteNaoEncontradoException(String nome) {
        super("Ambiente não encontrado: " + nome);
    }
}