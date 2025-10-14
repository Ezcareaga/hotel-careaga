package py.com.hotelcareaga.dto;

public class HabitacionDTO {
    public String numero;
    public String tipo;
    public Double precio;
    public Integer capacidad;
    
    // Constructor vacío (obligatorio para Jackson - JSON)
    public HabitacionDTO() {}
    
    // Constructor con parámetros (opcional, útil para tests)
    public HabitacionDTO(String numero, String tipo, Double precio, Integer capacidad) {
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.capacidad = capacidad;
    }
}