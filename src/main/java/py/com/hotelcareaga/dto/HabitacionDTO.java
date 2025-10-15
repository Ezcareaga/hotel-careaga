package py.com.hotelcareaga.dto;

import jakarta.validation.constraints.*;

public class HabitacionDTO {
    @NotBlank
    @Size(max = 10)
    public String numero;

    @NotBlank
    @Size(max = 50)
    public String tipo;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    public Double precio;

    @NotNull
    @Min(1)
    public Integer capacidad;

    public HabitacionDTO() {}

    public HabitacionDTO(String numero, String tipo, Double precio, Integer capacidad) {
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.capacidad = capacidad;
    }
}

