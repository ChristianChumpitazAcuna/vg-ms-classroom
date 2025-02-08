package pe.edu.vallegrande.classroom.domain.port.in;

import pe.edu.vallegrande.classroom.domain.model.Classroom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClassroomPersistencePort {
    Mono<Classroom> save(Classroom classroom);

    Mono<Classroom> update(Classroom classroom);

    Mono<Classroom> findById(String id);

    Mono<Void> changeStatus(String id, String status);

    Flux<Classroom> findByStatus(String status);

    Flux<Classroom> searchByTerm(String term, String status);

    Flux<Classroom> findByDidacticUnitId(String didacticUnitId);

    Flux<Classroom> findByStudyProgramId(String studyProgramId);
}
