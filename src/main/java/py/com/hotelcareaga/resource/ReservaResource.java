package py.com.hotelcareaga.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import py.com.hotelcareaga.dto.ReservaDTO;
import py.com.hotelcareaga.entity.EstadoReserva;
import py.com.hotelcareaga.entity.Reserva;
import py.com.hotelcareaga.service.ReservaService;

import java.util.List;
import java.util.Optional;

@Path("/reservas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Reservas")
public class ReservaResource {

    @Inject
    ReservaService reservaService;

    @GET
    @Operation(summary = "Listar reservas (opcional: filtrar por estado)")
    public Response listar(@QueryParam("estado") String estado) {
        List<Reserva> reservas;

        if (estado != null && !estado.isEmpty()) {
            try {
                EstadoReserva estadoEnum = EstadoReserva.valueOf(estado.toUpperCase());
                reservas = reservaService.listarPorEstado(estadoEnum);
            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Estado inválido. Valores: PENDIENTE, EN_CURSO, FINALIZADA, CANCELADA")
                        .build();
            }
        } else {
            reservas = reservaService.listarTodas();
        }

        return Response.ok(reservas).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener reserva por ID")
    public Response buscar(@PathParam("id") Long id) {
        Optional<Reserva> reserva = reservaService.buscarPorId(id);

        if (reserva.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Reserva no encontrada")
                    .build();
        }

        return Response.ok(reserva.get()).build();
    }

    @POST
    @Operation(summary = "Crear reserva")
    public Response crear(@Valid ReservaDTO dto) {
        try {
            Reserva reserva = reservaService.crear(dto);
            return Response.status(Response.Status.CREATED).entity(reserva).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar reserva")
    public Response actualizar(@PathParam("id") Long id, @Valid ReservaDTO dto) {
        try {
            Optional<Reserva> opt = reservaService.actualizar(id, dto);
            if (opt.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Reserva no encontrada").build();
            }
            return Response.ok(opt.get()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}/cancelar")
    @Operation(summary = "Cancelar reserva")
    public Response cancelar(@PathParam("id") Long id) {
        Optional<Reserva> reserva = reservaService.cancelar(id);

        if (reserva.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Reserva no encontrada")
                    .build();
        }

        return Response.ok(reserva.get()).build();
    }

    @PUT
    @Path("/{id}/checkin")
    @Operation(summary = "Check-in de reserva")
    public Response checkIn(@PathParam("id") Long id) {
        try {
            Optional<Reserva> reserva = reservaService.checkIn(id);

            if (reserva.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Reserva no encontrada")
                        .build();
            }

            return Response.ok(reserva.get()).build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}/checkout")
    @Operation(summary = "Check-out de reserva")
    public Response checkOut(@PathParam("id") Long id) {
        try {
            Optional<Reserva> reserva = reservaService.checkOut(id);

            if (reserva.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Reserva no encontrada")
                        .build();
            }

            return Response.ok(reserva.get()).build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar reserva")
    public Response eliminar(@PathParam("id") Long id) {
        boolean ok = reservaService.eliminar(id);
        if (!ok) return Response.status(Response.Status.NOT_FOUND).entity("Reserva no encontrada").build();
        return Response.noContent().build();
    }
}

