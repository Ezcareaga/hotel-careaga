package py.com.hotelcareaga.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import py.com.hotelcareaga.entity.EstadoReserva;
import py.com.hotelcareaga.entity.Reserva;

import java.util.List;

@ApplicationScoped
public class ReservaRepository implements PanacheRepository<Reserva> {
    
    /**
     * Busca reservas por estado
     * Ejemplo: findByEstado(EstadoReserva.PENDIENTE)
     */
    public List<Reserva> findByEstado(EstadoReserva estado) {
        return list("estado", estado);
    }
    
    /**
     * Busca reservas por habitación
     * Ejemplo: findByHabitacionId(1L)
     */
    public List<Reserva> findByHabitacionId(Long habitacionId) {
        return list("habitacion.id", habitacionId);
    }
    
    /**
     * Busca reservas por CI del cliente
     * Ejemplo: findByCiCliente("1234567")
     */
    public List<Reserva> findByCiCliente(String ciCliente) {
        return list("ciCliente", ciCliente);
    }
}