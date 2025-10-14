package py.com.hotelcareaga.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import py.com.hotelcareaga.dto.ReservaDTO;
import py.com.hotelcareaga.entity.EstadoReserva;
import py.com.hotelcareaga.entity.Reserva;
import py.com.hotelcareaga.service.ReservaService;

import java.util.List;
import java.util.Optional;

@Path("/reservas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaResource {

    @Inject
    ReservaService reservaService;

    @GET
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
    public Response crear(ReservaDTO dto) {
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
    @Path("/{id}/cancelar")
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
}
