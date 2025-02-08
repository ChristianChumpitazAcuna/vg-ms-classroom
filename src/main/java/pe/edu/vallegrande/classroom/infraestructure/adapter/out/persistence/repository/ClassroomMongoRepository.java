package pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.entity.ClassroomEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClassroomMongoRepository extends ReactiveMongoRepository<ClassroomEntity, String> {

    Flux<ClassroomEntity> findByStatus(String status);

    @Query("{ 'status' : ?0 }")
    Mono<Void> changeStatus(String id, String status);

    Flux<ClassroomEntity> findByDidacticUnitId(String didacticUnitId);

    Flux<ClassroomEntity> findByStudyProgramId(String studyProgramId);
}
