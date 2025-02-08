package pe.edu.vallegrande.classroom.application.port.in;

import pe.edu.vallegrande.classroom.application.port.dto.req.ClassroomRequestDTO;
import pe.edu.vallegrande.classroom.application.port.dto.res.ClassroomResponseDTO;
import pe.edu.vallegrande.classroom.domain.model.Classroom;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.DidacticUnitDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClassroomServicePort {
    Mono<Classroom> createClassroom(ClassroomRequestDTO classroom);

    Mono<Classroom> updateClassroom(String id, ClassroomRequestDTO classroom);

    Mono<ClassroomResponseDTO> getClassroomById(String id);

    Mono<Void> changeClassroomStatus(String id, String status);

    Flux<ClassroomResponseDTO> getClassroomByStatus(String status);

    Flux<Classroom> searchClassroom(String searchTerm, String status);

    Flux<ClassroomResponseDTO> getClassroomByStudyProgramId(String studyProgramId);

    Flux<ClassroomResponseDTO> getClassroomByDidacticUnitId(String didacticUnitId);

    Flux<DidacticUnitDTO> getDidacticUnitByClassroomId(String classroomId);

}
