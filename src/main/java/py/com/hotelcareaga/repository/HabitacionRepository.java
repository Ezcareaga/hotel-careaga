package py.com.hotelcareaga.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import py.com.hotelcareaga.entity.Habitacion;

import java.util.Optional;

@ApplicationScoped //Singleton
public class HabitacionRepository implements PanacheRepository<Habitacion> {
    //metodos automaticos
    /**
     * Busca habitación por número
     * Ejemplo: findByNumero("101")
     */
    public Optional<Habitacion> findByNumero(String numero) {
        return find("numero", numero).firstResultOptional();
    }
    
    /**
     * Busca habitaciones por tipo
     * Ejemplo: findByTipo("SUITE")
     */
    public java.util.List<Habitacion> findByTipo(String tipo) {
        return list("tipo", tipo);
    }
}