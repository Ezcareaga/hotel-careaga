package py.com.hotelcareaga.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import py.com.hotelcareaga.dto.ReservaDTO;
import py.com.hotelcareaga.entity.EstadoReserva;
import py.com.hotelcareaga.entity.Habitacion;
import py.com.hotelcareaga.entity.Reserva;
import py.com.hotelcareaga.repository.HabitacionRepository;
import py.com.hotelcareaga.repository.ReservaRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ReservaService {

    @Inject
    ReservaRepository reservaRepository;
    
    @Inject
    HabitacionRepository habitacionRepository;
    
    /**
     * Lista todas las reservas
     */
    public List<Reserva> listarTodas() {
        return reservaRepository.listAll();
    }
    
    /**
     * Busca reserva por ID
     */
    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findByIdOptional(id);
    }
    
    /**
     * Lista reservas por estado
     */
    public List<Reserva> listarPorEstado(EstadoReserva estado) {
        return reservaRepository.findByEstado(estado);
    }
    
    /**
     * Crea nueva reserva
     */
    @Transactional
    public Reserva crear(ReservaDTO dto) {
        // Validar que habitación existe
        Optional<Habitacion> habitacionOpt = habitacionRepository.findByIdOptional(dto.idHabitacion);
        if (habitacionOpt.isEmpty()) {
            throw new IllegalArgumentException("Habitación no encontrada con ID: " + dto.idHabitacion);
        }
        
        Habitacion habitacion = habitacionOpt.get();
        
        // Crear reserva
        Reserva reserva = new Reserva();
        reserva.setHabitacion(habitacion);
        reserva.setFechaInicio(dto.fechaInicio);
        reserva.setFechaFin(dto.fechaFin);
        reserva.setNombreCliente(dto.nombreCliente);
        reserva.setEmailCliente(dto.emailCliente);
        reserva.setTelefonoCliente(dto.telefonoCliente);
        reserva.setCiCliente(dto.ciCliente);
        reserva.setEstado(EstadoReserva.PENDIENTE);
        
        reservaRepository.persist(reserva);
        return reserva;
    }
    
    /**
     * Cancela una reserva
     */
    @Transactional
    public Optional<Reserva> cancelar(Long id) {
        Optional<Reserva> optional = reservaRepository.findByIdOptional(id);
        
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        
        Reserva reserva = optional.get();
        reserva.setEstado(EstadoReserva.CANCELADA);
        return Optional.of(reserva);
    }
    
    /**
     * Check-in: cambia estado a EN_CURSO
     */
    @Transactional
    public Optional<Reserva> checkIn(Long id) {
        Optional<Reserva> optional = reservaRepository.findByIdOptional(id);
        
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        
        Reserva reserva = optional.get();
        
        // Validar que esté en estado PENDIENTE
        if (reserva.getEstado() != EstadoReserva.PENDIENTE) {
            throw new IllegalStateException("Solo se puede hacer check-in de reservas PENDIENTES");
        }
        
        reserva.setEstado(EstadoReserva.EN_CURSO);
        return Optional.of(reserva);
    }
    
    /**
     * Check-out: cambia estado a FINALIZADA
     */
    @Transactional
    public Optional<Reserva> checkOut(Long id) {
        Optional<Reserva> optional = reservaRepository.findByIdOptional(id);
        
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        
        Reserva reserva = optional.get();
        
        // Validar que esté EN_CURSO
        if (reserva.getEstado() != EstadoReserva.EN_CURSO) {
            throw new IllegalStateException("Solo se puede hacer check-out de reservas EN_CURSO");
        }
        
        reserva.setEstado(EstadoReserva.FINALIZADA);
        return Optional.of(reserva);
    }
}