package engtelecom.std.apirest.controller;

import engtelecom.std.apirest.dto.DispositivosDTO;
import engtelecom.std.apirest.service.LampadaService;
import engtelecom.std.apirest.service.TvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dispositivos")
public class DispositivoController {

    @Autowired
    private LampadaService lampadaService;

    @Autowired
    private TvService tvService;

    @GetMapping
    public DispositivosDTO consultarTodos() {
        return new DispositivosDTO(lampadaService.consultar(), tvService.consultar());
    }
}