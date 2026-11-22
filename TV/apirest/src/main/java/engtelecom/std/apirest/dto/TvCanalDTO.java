package engtelecom.std.apirest.dto;

import jakarta.validation.constraints.Min;

public record TvCanalDTO(
    @Min(value = 1, message = "só existem canais a partir do 1")
    int canal
){}