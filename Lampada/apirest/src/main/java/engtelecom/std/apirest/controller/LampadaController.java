package engtelecom.std.apirest.controller;

import engtelecom.std.apirest.dto.*;
import engtelecom.std.apirest.entities.Lampada;
import engtelecom.std.apirest.exceptions.*;
import engtelecom.std.apirest.service.LampadaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/lampada", "/lampada/"})

public class LampadaController {
    @Autowired
    private LampadaService lampadaService;

    @GetMapping
    public Lampada obterLampada(){
        return this.lampadaService.consultar();
    }

    @PutMapping("/estado")
    @ResponseStatus(HttpStatus.OK)
    public Lampada alterarEstado(@RequestBody LampadaLigadaDTO dto) {
        this.lampadaService.definirLigada(dto.ligada());
        return this.lampadaService.consultar();
    }

    @PutMapping("/brilho")
    @ResponseStatus(HttpStatus.OK)
    public Lampada alterarBrilho(@Valid @RequestBody LampadaBrilhoDTO dto) {
        this.lampadaService.definirBrilho(dto.brilho());
        return this.lampadaService.consultar();
    }

    @ControllerAdvice
    static class LampadaDesligada {
        @ResponseBody
        @ExceptionHandler(LampadaDesligadaException.class)
        @ResponseStatus(HttpStatus.CONFLICT)
        String lampadaDesligada(LampadaDesligadaException e) {
            return e.getMessage();
        }
    }
}