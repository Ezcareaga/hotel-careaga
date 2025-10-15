package py.com.hotelcareaga.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<String> details = exception.getConstraintViolations().stream()
                .map(this::format)
                .collect(Collectors.toList());
        var body = new ErrorBody("Validation Error", "Datos inválidos", details);
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }

    private String format(ConstraintViolation<?> v) {
        return v.getPropertyPath() + ": " + v.getMessage();
    }

    public static class ErrorBody {
        public String error;
        public String message;
        public List<String> details;
        public ErrorBody(String error, String message, List<String> details) {
            this.error = error; this.message = message; this.details = details;
        }
    }
}

