package py.com.hotelcareaga.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import py.com.hotelcareaga.entity.ServicioExtra;

@ApplicationScoped
public class ServicioExtraRepository implements PanacheRepository<ServicioExtra> { }

