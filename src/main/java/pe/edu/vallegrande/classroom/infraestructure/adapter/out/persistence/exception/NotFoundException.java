package pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.exception;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}
