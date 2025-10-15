package py.com.hotelcareaga.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class ReservaDTO {
    @NotNull
    public Long idHabitacion;

    @NotNull
    public LocalDate fechaInicio;

    @NotNull
    public LocalDate fechaFin;

    @NotBlank
    @Size(max = 100)
    public String nombreCliente;

    @NotBlank
    @Email
    @Size(max = 100)
    public String emailCliente;

    @NotBlank
    @Size(max = 20)
    public String telefonoCliente;

    @NotBlank
    @Size(max = 20)
    public String ciCliente;

    public ReservaDTO() {}

    public ReservaDTO(Long idHabitacion, LocalDate fechaInicio, LocalDate fechaFin,
                      String nombreCliente, String emailCliente,
                      String telefonoCliente, String ciCliente) {
        this.idHabitacion = idHabitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nombreCliente = nombreCliente;
        this.emailCliente = emailCliente;
        this.telefonoCliente = telefonoCliente;
        this.ciCliente = ciCliente;
    }
}

