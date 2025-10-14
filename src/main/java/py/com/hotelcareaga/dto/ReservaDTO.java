package py.com.hotelcareaga.dto;

import java.time.LocalDate;

public class ReservaDTO {
    public Long idHabitacion;
    public LocalDate fechaInicio;
    public LocalDate fechaFin;
    public String nombreCliente;
    public String emailCliente;
    public String telefonoCliente;
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