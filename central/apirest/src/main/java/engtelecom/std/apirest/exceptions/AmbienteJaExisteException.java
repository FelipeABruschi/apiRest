package engtelecom.std.apirest.exceptions;

public class AmbienteJaExisteException extends RuntimeException {
    public AmbienteJaExisteException(String nome) {
        super("Já existe um ambiente chamado: " + nome);
    }
}