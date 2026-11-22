package engtelecom.std.apirest.service;

import engtelecom.std.apirest.entities.TV;
import engtelecom.std.apirest.exceptions.TvDesligadaException;
import org.springframework.stereotype.Component;

@Component
public class TvService {
    private final TV tv = new TV();

    public TV consultar() {
        return tv;
    }

    public void definirLigada(boolean ligada) {
        tv.setLigada(ligada);
    }

    public void definirVolume(int volume) {
        if (!tv.isLigada()) {
            throw new TvDesligadaException();
        }
        tv.setVolume(volume);
    }

    public void definirCanal(int canal) {
        if (!tv.isLigada()) {
            throw new TvDesligadaException();
        }
        tv.setCanal(canal);
    }
}