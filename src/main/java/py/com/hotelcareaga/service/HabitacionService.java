package py.com.hotelcareaga.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import py.com.hotelcareaga.dto.HabitacionDTO;
import py.com.hotelcareaga.entity.Habitacion;
import py.com.hotelcareaga.repository.HabitacionRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class HabitacionService {

    @Inject
    HabitacionRepository habitacionRepository;
    
    /**
     * Lista todas las habitaciones
     */
    public List<Habitacion> listarTodas() {
        return habitacionRepository.listAll();
    }
    
    /**
     * Busca habitación por ID
     */
    public Optional<Habitacion> buscarPorId(Long id) {
        return habitacionRepository.findByIdOptional(id);
    }
    
    /**
     * Crea una nueva habitación
     */
    @Transactional
    public Habitacion crear(HabitacionDTO dto) {
        if (dto.adminKey == null || !"admin123".equals(dto.adminKey)) {
            throw new SecurityException("Acceso denegado: adminKey inválido");
        }
        // Validar que el número no exista
        Optional<Habitacion> existe = habitacionRepository.findByNumero(dto.numero);
        if (existe.isPresent()) {
            throw new IllegalArgumentException("Ya existe habitación con número: " + dto.numero);
        }
        
        // Mapear DTO → Entity
        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(dto.numero);
        habitacion.setTipo(dto.tipo);
        habitacion.setPrecio(dto.precio);
         habitacion.setCapacidad(dto.capacidad);
        
        // Guardar quién creó
        habitacion.setCreatedBy("admin");
        habitacion.setUpdatedBy("admin");
        
        // Persistir
        habitacionRepository.persist(habitacion);
        return habitacion;
    }
    
    /**
     * Actualiza habitación existente
     */
    @Transactional
    public Optional<Habitacion> actualizar(Long id, HabitacionDTO dto) {
            // VALIDAR ADMIN KEY
        if (dto.adminKey == null || !"admin123".equals(dto.adminKey)) {
            throw new SecurityException("Acceso denegado: adminKey inválido");
        }
        Optional<Habitacion> optional = habitacionRepository.findByIdOptional(id);
        
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        
        Habitacion habitacion = optional.get();

        // Validar unicidad de número si cambia o para evitar duplicados
        Optional<Habitacion> otra = habitacionRepository.findByNumero(dto.numero);
        if (otra.isPresent() && !otra.get().getId().equals(habitacion.getId())) {
            throw new IllegalArgumentException("Ya existe habitación con número: " + dto.numero);
        }
        habitacion.setNumero(dto.numero);
        habitacion.setTipo(dto.tipo);
        habitacion.setPrecio(dto.precio);
        habitacion.setCapacidad(dto.capacidad);
        habitacion.setUpdatedBy("admin");
        
        // No necesitas persist() en update, Hibernate detecta cambios
        return Optional.of(habitacion);
    }
    
    /**
     * Elimina habitación
     */
    @Transactional
    public boolean eliminar(Long id) {
        return habitacionRepository.deleteById(id);
    }
}
