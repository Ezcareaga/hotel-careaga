package py.com.hotelcareaga.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import py.com.hotelcareaga.dto.ConsumoDTO;
import py.com.hotelcareaga.entity.Consumo;
import py.com.hotelcareaga.service.ConsumoService;

import java.util.List;
import java.util.Optional;

@Path("/consumos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Consumos")
public class ConsumoResource {
    @Inject ConsumoService service;

    @GET
    @Operation(summary = "Listar consumos")
    public Response listar() {
        List<Consumo> list = service.listarTodos();
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener consumo por ID")
    public Response buscar(@PathParam("id") Long id) {
        Optional<Consumo> opt = service.buscarPorId(id);
        if (opt.isEmpty()) return Response.status(Response.Status.NOT_FOUND).entity("No encontrado").build();
        return Response.ok(opt.get()).build();
    }

    @POST
    @Operation(summary = "Crear consumo")
    public Response crear(@Valid ConsumoDTO dto) {
        Consumo c = service.crear(dto);
        return Response.status(Response.Status.CREATED).entity(c).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar consumo")
    public Response actualizar(@PathParam("id") Long id, @Valid ConsumoDTO dto) {
        Optional<Consumo> opt = service.actualizar(id, dto);
        if (opt.isEmpty()) return Response.status(Response.Status.NOT_FOUND).entity("No encontrado").build();
        return Response.ok(opt.get()).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar consumo")
    public Response eliminar(@PathParam("id") Long id) {
        boolean ok = service.eliminar(id);
        if (!ok) return Response.status(Response.Status.NOT_FOUND).entity("No encontrado").build();
        return Response.noContent().build();
    }
}

