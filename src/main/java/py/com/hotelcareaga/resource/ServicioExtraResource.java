package py.com.hotelcareaga.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import py.com.hotelcareaga.dto.ServicioExtraDTO;
import py.com.hotelcareaga.entity.ServicioExtra;
import py.com.hotelcareaga.service.ServicioExtraService;

import java.util.List;
import java.util.Optional;

@Path("/servicios-extra")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Servicios Extra")
public class ServicioExtraResource {
    @Inject ServicioExtraService service;

    @GET
    @Operation(summary = "Listar servicios extra")
    public Response listar() {
        List<ServicioExtra> list = service.listarTodas();
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener servicio extra por ID")
    public Response buscar(@PathParam("id") Long id) {
        Optional<ServicioExtra> opt = service.buscarPorId(id);
        if (opt.isEmpty()) return Response.status(Response.Status.NOT_FOUND).entity("No encontrado").build();
        return Response.ok(opt.get()).build();
    }

    @POST
    @Operation(summary = "Crear servicio extra")
    public Response crear(@Valid ServicioExtraDTO dto) {
        ServicioExtra s = service.crear(dto);
        return Response.status(Response.Status.CREATED).entity(s).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar servicio extra")
    public Response actualizar(@PathParam("id") Long id, @Valid ServicioExtraDTO dto) {
        Optional<ServicioExtra> opt = service.actualizar(id, dto);
        if (opt.isEmpty()) return Response.status(Response.Status.NOT_FOUND).entity("No encontrado").build();
        return Response.ok(opt.get()).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar servicio extra")
    public Response eliminar(@PathParam("id") Long id) {
        boolean ok = service.eliminar(id);
        if (!ok) return Response.status(Response.Status.NOT_FOUND).entity("No encontrado").build();
        return Response.noContent().build();
    }
}
