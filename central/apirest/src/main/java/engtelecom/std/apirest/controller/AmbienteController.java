package engtelecom.std.apirest.controller;

import engtelecom.std.apirest.dto.AmbienteDTO;
import engtelecom.std.apirest.dto.LigadaDTO;
import engtelecom.std.apirest.dto.SituacaoDispositivo;
import engtelecom.std.apirest.entities.Central;
import engtelecom.std.apirest.service.AmbienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/ambientes")
public class AmbienteController {

    @Autowired
    private Central central;

    @Autowired
    private AmbienteService ambienteService;

    @GetMapping
    public Map<String, Set<String>> listar() {
        return central.listarAmbientes();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Set<String>> criar(@Valid @RequestBody AmbienteDTO dto) {
        central.criarAmbiente(dto.nome());
        return central.listarAmbientes();
    }

    @DeleteMapping("/{nome}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable String nome) {
        central.apagarAmbiente(nome);
    }

    @PutMapping("/{nome}/dispositivos/{dispositivo}")
    public Set<String> adicionar(@PathVariable String nome,
                                 @PathVariable String dispositivo) {
        central.adicionarDispositivo(nome, dispositivo);
        return central.dispositivosDo(nome);
    }

    @DeleteMapping("/{nome}/dispositivos/{dispositivo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable String nome,
                        @PathVariable String dispositivo) {
        central.removerDispositivo(nome, dispositivo);
    }

    @PutMapping("/{nome}/estado")
    public Map<String, SituacaoDispositivo> alterarEstado(@PathVariable String nome,
                                                          @RequestBody LigadaDTO dto) {
        return ambienteService.definirLigada(nome, dto.ligada());
    }
}