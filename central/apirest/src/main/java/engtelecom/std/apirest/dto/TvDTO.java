// TvDTO.java
package engtelecom.std.apirest.dto;

public record TvDTO(boolean ligada, int volume, int canal) implements SituacaoDispositivo {}