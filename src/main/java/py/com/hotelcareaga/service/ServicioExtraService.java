package py.com.hotelcareaga.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import py.com.hotelcareaga.dto.ServicioExtraDTO;
import py.com.hotelcareaga.entity.ServicioExtra;
import py.com.hotelcareaga.repository.ServicioExtraRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ServicioExtraService {
    @Inject
    ServicioExtraRepository repo;

    public List<ServicioExtra> listarTodas() { return repo.listAll(); }

    public Optional<ServicioExtra> buscarPorId(Long id) { return repo.findByIdOptional(id); }

    @Transactional
    public ServicioExtra crear(ServicioExtraDTO dto) {
        ServicioExtra s = new ServicioExtra();
        s.setNombre(dto.nombre);
        s.setPrecio(dto.precio);
        s.setDescripcion(dto.descripcion);
        repo.persist(s);
        return s;
    }

    @Transactional
    public Optional<ServicioExtra> actualizar(Long id, ServicioExtraDTO dto) {
        Optional<ServicioExtra> opt = repo.findByIdOptional(id);
        if (opt.isEmpty()) return Optional.empty();
        ServicioExtra s = opt.get();
        s.setNombre(dto.nombre);
        s.setPrecio(dto.precio);
        s.setDescripcion(dto.descripcion);
        return Optional.of(s);
    }

    @Transactional
    public boolean eliminar(Long id) { return repo.deleteById(id); }
}

