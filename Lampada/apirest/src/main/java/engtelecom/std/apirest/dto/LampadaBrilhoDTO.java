package engtelecom.std.apirest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record LampadaBrilhoDTO(
    @Min(value = 0, message = "brilho não pode ser negativo")
    @Max(value = 100, message = "limite máximo de brilho é 100")
    int brilho
) {}