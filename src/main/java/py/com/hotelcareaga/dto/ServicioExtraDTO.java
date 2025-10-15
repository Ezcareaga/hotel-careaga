package py.com.hotelcareaga.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ServicioExtraDTO {
    @NotBlank
    @Size(max = 100)
    public String nombre;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    public BigDecimal precio;

    @Size(max = 255)
    public String descripcion;

    public ServicioExtraDTO() {}
}

