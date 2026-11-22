package engtelecom.std.apirest.service;

import engtelecom.std.apirest.dto.SituacaoDispositivo;
import engtelecom.std.apirest.entities.Central;
import engtelecom.std.apirest.exceptions.DispositivoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class AmbienteService {

    @Autowired
    private Central central;

    @Autowired
    private LampadaService lampadaService;

    @Autowired
    private TvService tvService;

    // liga ou desliga todos os dispositivos do ambiente
    public Map<String, SituacaoDispositivo> definirLigada(String nome, boolean ligada) {
        Map<String, SituacaoDispositivo> resultado = new LinkedHashMap<>();

        for (String dispositivo : central.dispositivosDo(nome)) {
            SituacaoDispositivo situacao = switch (dispositivo) {
                case Central.LAMPADA -> {
                    lampadaService.definirLigada(ligada);
                    yield lampadaService.consultar();
                }
                case Central.TV -> {
                    tvService.definirLigada(ligada);
                    yield tvService.consultar();
                }
                default -> throw new DispositivoNaoEncontradoException(dispositivo);
            };
            resultado.put(dispositivo, situacao);
        }

        return resultado;
    }
}