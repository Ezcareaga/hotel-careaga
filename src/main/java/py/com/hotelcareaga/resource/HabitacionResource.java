package py.com.hotelcareaga.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import py.com.hotelcareaga.dto.HabitacionDTO;
import py.com.hotelcareaga.entity.Habitacion;
import py.com.hotelcareaga.service.HabitacionService;

import java.util.List;
import java.util.Optional;

@Path("/habitaciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Habitaciones")
public class HabitacionResource {

    @Inject
    HabitacionService habitacionService;

    @GET
    @Operation(summary = "Listar habitaciones")
    public Response listar() {
        List<Habitacion> habitaciones = habitacionService.listarTodas();
        return Response.ok(habitaciones).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener habitación por ID")
    public Response buscar(@PathParam("id") Long id) {
        Optional<Habitacion> habitacion = habitacionService.buscarPorId(id);

        if (habitacion.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Habitación no encontrada")
                    .build();
        }

        return Response.ok(habitacion.get()).build();
    }

    @POST
    @Operation(summary = "Crear habitación")
    public Response crear(@Valid HabitacionDTO dto) {
        try {
            Habitacion habitacion = habitacionService.crear(dto);
            return Response.status(Response.Status.CREATED).entity(habitacion).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar habitación")
    public Response actualizar(@PathParam("id") Long id, @Valid HabitacionDTO dto) {
        Optional<Habitacion> habitacion = habitacionService.actualizar(id, dto);

        if (habitacion.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Habitación no encontrada")
                    .build();
        }

        return Response.ok(habitacion.get()).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar habitación")
    public Response eliminar(@PathParam("id") Long id) {
        boolean eliminado = habitacionService.eliminar(id);

        if (!eliminado) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Habitación no encontrada")
                    .build();
        }

        return Response.noContent().build();
    }
}

