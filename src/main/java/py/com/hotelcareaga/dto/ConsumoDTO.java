package py.com.hotelcareaga.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class ConsumoDTO {
    @NotNull
    public Long reservaId;

    @NotNull
    public Long servicioId;

    @NotNull
    @Min(1)
    public Integer cantidad;

    public LocalDateTime fechaConsumo; // opcional

    public ConsumoDTO() {}
}

