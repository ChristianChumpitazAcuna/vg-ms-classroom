package pe.edu.vallegrande.classroom.application.port.out;

import org.mapstruct.Mapper;
import pe.edu.vallegrande.classroom.application.port.dto.req.ClassroomRequestDTO;
import pe.edu.vallegrande.classroom.domain.model.Classroom;

@Mapper(componentModel = "spring")
public interface ClassroomRequestMapper {
    Classroom toDomain(ClassroomRequestDTO classroomRequest);
}
