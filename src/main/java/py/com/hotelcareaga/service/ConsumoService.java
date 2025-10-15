package py.com.hotelcareaga.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import py.com.hotelcareaga.dto.ConsumoDTO;
import py.com.hotelcareaga.entity.Consumo;
import py.com.hotelcareaga.entity.Reserva;
import py.com.hotelcareaga.entity.ServicioExtra;
import py.com.hotelcareaga.repository.ConsumoRepository;
import py.com.hotelcareaga.repository.ReservaRepository;
import py.com.hotelcareaga.repository.ServicioExtraRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ConsumoService {
    @Inject ConsumoRepository repo;
    @Inject ReservaRepository reservaRepo;
    @Inject ServicioExtraRepository servicioRepo;

    public List<Consumo> listarTodos() { return repo.listAll(); }

    public Optional<Consumo> buscarPorId(Long id) { return repo.findByIdOptional(id); }

    @Transactional
    public Consumo crear(ConsumoDTO dto) {
        Reserva reserva = reservaRepo.findByIdOptional(dto.reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada: " + dto.reservaId));
        ServicioExtra servicio = servicioRepo.findByIdOptional(dto.servicioId)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado: " + dto.servicioId));

        Consumo c = new Consumo();
        c.setReserva(reserva);
        c.setServicio(servicio);
        c.setCantidad(dto.cantidad);
        c.setFechaConsumo(dto.fechaConsumo);
        repo.persist(c);
        return c;
    }

    @Transactional
    public Optional<Consumo> actualizar(Long id, ConsumoDTO dto) {
        Optional<Consumo> opt = repo.findByIdOptional(id);
        if (opt.isEmpty()) return Optional.empty();

        Reserva reserva = reservaRepo.findByIdOptional(dto.reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada: " + dto.reservaId));
        ServicioExtra servicio = servicioRepo.findByIdOptional(dto.servicioId)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado: " + dto.servicioId));

        Consumo c = opt.get();
        c.setReserva(reserva);
        c.setServicio(servicio);
        c.setCantidad(dto.cantidad);
        c.setFechaConsumo(dto.fechaConsumo);
        return Optional.of(c);
    }

    @Transactional
    public boolean eliminar(Long id) { return repo.deleteById(id); }
}

