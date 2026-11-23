package engtelecom.std.apirest.entities;

import engtelecom.std.apirest.exceptions.AmbienteJaExisteException;
import engtelecom.std.apirest.exceptions.AmbienteNaoEncontradoException;
import engtelecom.std.apirest.exceptions.DispositivoForaDoAmbienteException;
import engtelecom.std.apirest.exceptions.DispositivoNaoEncontradoException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Central {
    public static final String LAMPADA = "lampada";
    public static final String TV = "tv";

    private final Map<String, Set<String>> ambientes = new HashMap<>();

    public Central() {
        Set<String> inicial = new HashSet<>();
        inicial.add(LAMPADA);
        inicial.add(TV);
        ambientes.put("sala", inicial);
    }

    public static String validar(String dispositivo) {
        String d = dispositivo.trim().toLowerCase();
        if (!d.equals(LAMPADA) && !d.equals(TV)) {
            throw new DispositivoNaoEncontradoException(dispositivo);
        }
        return d;
    }

    private String normalizar(String nome) {
        return nome.trim().toLowerCase();
    }

    private Set<String> buscar(String nome) {
        Set<String> dispositivos = ambientes.get(normalizar(nome));
        if (dispositivos == null) {
            throw new AmbienteNaoEncontradoException(nome);
        }
        return dispositivos;
    }

    public synchronized void criarAmbiente(String nome) {
        String chave = normalizar(nome);
        if (ambientes.containsKey(chave)) {
            throw new AmbienteJaExisteException(nome);
        }
        ambientes.put(chave, new HashSet<>());
    }

    public synchronized void apagarAmbiente(String nome) {
        buscar(nome); // lança exceção se não existir
        ambientes.remove(normalizar(nome));
    }

    public synchronized void adicionarDispositivo(String nome, String dispositivo) {
        dispositivo = validar(dispositivo);
        Set<String> destino = buscar(nome);
        for (Set<String> outro : ambientes.values()) {
            outro.remove(dispositivo);
        }
        destino.add(dispositivo);
    }

    public synchronized void removerDispositivo(String nome, String dispositivo) {
        dispositivo = validar(dispositivo);
        Set<String> dispositivos = buscar(nome);
        if (!dispositivos.remove(dispositivo)) {
            throw new DispositivoForaDoAmbienteException(nome, dispositivo);
        }
    }

    public synchronized Set<String> dispositivosDo(String nome) {
        return new HashSet<>(buscar(nome));
    }

    public synchronized Map<String, Set<String>> listarAmbientes() {
        Map<String, Set<String>> copia = new HashMap<>();
        ambientes.forEach((nome, disp) -> copia.put(nome, new HashSet<>(disp)));
        return copia;
    }

    public synchronized Optional<String> ambienteDoDispositivo(String dispositivo) {
        String d = validar(dispositivo);
        return ambientes.entrySet().stream()
                .filter(e -> e.getValue().contains(d))
                .map(Map.Entry::getKey)
                .findFirst();
    }
}