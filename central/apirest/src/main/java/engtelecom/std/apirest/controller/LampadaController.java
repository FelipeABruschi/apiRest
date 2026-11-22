package engtelecom.std.apirest.controller;

import engtelecom.std.apirest.dto.BrilhoDTO;
import engtelecom.std.apirest.dto.LampadaDTO;
import engtelecom.std.apirest.dto.LigadaDTO;
import engtelecom.std.apirest.service.LampadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lampada")
public class LampadaController {

    @Autowired
    private LampadaService lampadaService;

    @GetMapping
    public LampadaDTO consultar() {
        return lampadaService.consultar();
    }

    @PutMapping("/estado")
    public LampadaDTO alterarEstado(@RequestBody LigadaDTO dto) {
        lampadaService.definirLigada(dto.ligada());
        return lampadaService.consultar();
    }

    @PutMapping("/brilho")
    public LampadaDTO alterarBrilho(@RequestBody BrilhoDTO dto) {
        lampadaService.definirBrilho(dto.brilho());
        return lampadaService.consultar();
    }
}