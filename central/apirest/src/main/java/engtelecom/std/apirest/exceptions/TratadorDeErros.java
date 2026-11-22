package engtelecom.std.apirest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class TratadorDeErros {

    // 404: o que foi pedido não existe
    @ResponseBody
    @ExceptionHandler({AmbienteNaoEncontradoException.class,
                        DispositivoNaoEncontradoException.class,
                       DispositivoForaDoAmbienteException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String naoEncontrado(RuntimeException e) {
        return e.getMessage();
    }

    // 409: pedido válido, mas conflita com o estado atual
    @ResponseBody
    @ExceptionHandler(AmbienteJaExisteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String conflito(RuntimeException e) {
        return e.getMessage();
    }

    // 502: a lâmpada ou a TV não respondeu
    @ResponseBody
    @ExceptionHandler(DispositivoIndisponivelException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    String indisponivel(RuntimeException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ErroDoDispositivoException.class)
    ResponseEntity<String> erroDoDispositivo(ErroDoDispositivoException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}