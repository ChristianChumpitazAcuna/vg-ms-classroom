package pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.exception;

public class CapacityExceededException extends RuntimeException {
    public CapacityExceededException(String message) {
        super(message);
    }
}
