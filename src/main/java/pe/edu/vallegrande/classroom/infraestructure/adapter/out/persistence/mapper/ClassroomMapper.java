package pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.mapper;

import org.mapstruct.Mapper;
import pe.edu.vallegrande.classroom.domain.model.Classroom;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.entity.ClassroomEntity;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    Classroom toDomain(ClassroomEntity classroomEntity);

    ClassroomEntity toEntity(Classroom classroom);
}
