package engtelecom.std.apirest.service;

import engtelecom.std.apirest.entities.Lampada;
import engtelecom.std.apirest.exceptions.LampadaDesligadaException;
import org.springframework.stereotype.Component;

@Component
public class LampadaService {

    private final Lampada lampada = new Lampada();

    public Lampada consultar() { return lampada; }

    public void definirBrilho(int brilho) {
        if (!lampada.isLigada()) {
            throw new LampadaDesligadaException();
        }
        lampada.setBrilho(brilho);
    }

    public void definirLigada(boolean ligada) {
        lampada.setLigada(ligada);
    }
}