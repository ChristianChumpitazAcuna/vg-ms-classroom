package pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ApiError {
    private HttpStatus status;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ApiError(HttpStatus status) {
        this.status = status;
        this.errors = new HashMap<>();
        this.timestamp = LocalDateTime.now();
    }

    public void addError(String key, String value) {
        this.errors.put(key, value);
    }
}
