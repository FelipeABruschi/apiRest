package engtelecom.std.apirest.controller;

import engtelecom.std.apirest.dto.*;
import engtelecom.std.apirest.entities.TV;
import engtelecom.std.apirest.exceptions.*;
import engtelecom.std.apirest.service.TvService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/tv", "/tv/"})
public class TvController {
    @Autowired
    private TvService tvService;

    @GetMapping
    public TV obterTv() {
        return this.tvService.consultar();
    }

    @PutMapping("/estado")
    public TV alterarEstado(@RequestBody TvLigadaDTO dto) {
        this.tvService.definirLigada(dto.ligada());
        return this.tvService.consultar();
    }

    @PutMapping("/volume")
    public TV alterarVolume(@Valid @RequestBody TvVolumeDTO dto) {
        this.tvService.definirVolume(dto.volume());
        return this.tvService.consultar();
    }

    @PutMapping("/canal")
    public TV alterarCanal(@Valid @RequestBody TvCanalDTO dto) {
        this.tvService.definirCanal(dto.canal());
        return this.tvService.consultar();
    }

    @ControllerAdvice
    static class TvDesligada {
        @ResponseBody
        @ExceptionHandler(TvDesligadaException.class)
        @ResponseStatus(HttpStatus.CONFLICT)
        String tvDesligada(TvDesligadaException e) {
            return e.getMessage();
        }
    }
}