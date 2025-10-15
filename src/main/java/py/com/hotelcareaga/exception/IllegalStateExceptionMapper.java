package py.com.hotelcareaga.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalStateExceptionMapper implements ExceptionMapper<IllegalStateException> {
    @Override
    public Response toResponse(IllegalStateException exception) {
        var body = new ErrorBody("Invalid State", exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }

    public static class ErrorBody {
        public String error;
        public String message;
        public ErrorBody(String error, String message) { this.error = error; this.message = message; }
    }
}

