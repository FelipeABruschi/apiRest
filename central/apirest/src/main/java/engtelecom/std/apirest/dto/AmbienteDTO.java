package engtelecom.std.apirest.dto;

import jakarta.validation.constraints.NotBlank;

public record AmbienteDTO(
    @NotBlank(message = "o nome do ambiente não pode estar em branco")
    String nome
) {}