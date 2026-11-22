package engtelecom.std.apirest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record TvVolumeDTO(
    @Min(value = 0, message = "volume não pode ser negativo")
    @Max(value = 100, message = "limite máximo do volume é 100")
    int volume
) {}