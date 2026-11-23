package engtelecom.std.apirest.service;

import engtelecom.std.apirest.dto.BrilhoDTO;
import engtelecom.std.apirest.dto.LampadaDTO;
import engtelecom.std.apirest.dto.LigadaDTO;
import engtelecom.std.apirest.exceptions.DispositivoIndisponivelException;
import engtelecom.std.apirest.exceptions.ErroDoDispositivoException;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class LampadaService {

    private final HttpClient client;
    private static final JsonMapper jsonMapper = JsonMapper.builder().build();
    private static final String url = "http://localhost:8081/lampada";

    public LampadaService() {
        this.client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
    }

    public LampadaDTO consultar() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(5))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return jsonMapper.readValue(response.body(), LampadaDTO.class);
            }
            throw new ErroDoDispositivoException(response.statusCode(), response.body());

        } catch (IOException | InterruptedException e) {
            throw new DispositivoIndisponivelException("lampada");
        }
    }

    public void definirLigada(boolean ligada) {
        enviarPut("/estado", new LigadaDTO(ligada));
    }

    public void definirBrilho(int brilho) {
        enviarPut("/brilho", new BrilhoDTO(brilho));
    }

    private void enviarPut(String caminho, Object corpo) {
        String jsonBody = jsonMapper.writeValueAsString(corpo);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + caminho))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(5))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            switch (response.statusCode()) {
                case 200 -> { }
                default -> throw new ErroDoDispositivoException(response.statusCode(), response.body());
            }

        } catch (IOException | InterruptedException e) {
            throw new DispositivoIndisponivelException("lampada");
        }
    }
}