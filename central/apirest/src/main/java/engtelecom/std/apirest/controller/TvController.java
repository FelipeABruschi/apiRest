package engtelecom.std.apirest.controller;

import engtelecom.std.apirest.dto.CanalDTO;
import engtelecom.std.apirest.dto.LigadaDTO;
import engtelecom.std.apirest.dto.TvDTO;
import engtelecom.std.apirest.dto.VolumeDTO;
import engtelecom.std.apirest.service.TvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tv")
public class TvController {

    @Autowired
    private TvService tvService;

    @GetMapping
    public TvDTO consultar() {
        return tvService.consultar();
    }

    @PutMapping("/estado")
    public TvDTO alterarEstado(@RequestBody LigadaDTO dto) {
        tvService.definirLigada(dto.ligada());
        return tvService.consultar();
    }

    @PutMapping("/volume")
    public TvDTO alterarVolume(@RequestBody VolumeDTO dto) {
        tvService.definirVolume(dto.volume());
        return tvService.consultar();
    }

    @PutMapping("/canal")
    public TvDTO alterarCanal(@RequestBody CanalDTO dto) {
        tvService.definirCanal(dto.canal());
        return tvService.consultar();
    }
}