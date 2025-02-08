package pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.vallegrande.classroom.domain.model.Classroom;
import pe.edu.vallegrande.classroom.domain.port.in.ClassroomPersistencePort;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.entity.ClassroomEntity;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.mapper.ClassroomMapper;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.repository.ClassroomMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClassroomRepositoryAdapter implements ClassroomPersistencePort {
    private final ClassroomMongoRepository repository;
    private final ClassroomMapper classroomMapper;

    @Override
    @Transactional
    public Mono<Classroom> save(Classroom classroom) {
        ClassroomEntity entity = classroomMapper.toEntity(classroom);
        entity.setStatus("A");
        return repository.save(entity)
                .map(classroomMapper::toDomain);
    }

    @Override
    @Transactional
    public Mono<Classroom> update(Classroom classroom) {
        ClassroomEntity entity = classroomMapper.toEntity(classroom);
        return repository.save(entity)
                .map(classroomMapper::toDomain);
    }

    @Override
    public Mono<Classroom> findById(String id) {
        return repository.findById(id)
                .map(classroomMapper::toDomain);
    }

    @Override
    public Mono<Void> changeStatus(String id, String status) {
        return repository.changeStatus(id, status);
    }

    @Override
    public Flux<Classroom> findByStatus(String status) {
        return repository.findByStatus(status)
                .map(classroomMapper::toDomain);
    }

    @Override
    public Flux<Classroom> searchByTerm(String term, String status) {
        return null;
    }

    @Override
    public Flux<Classroom> findByDidacticUnitId(String didacticUnitId) {
        return repository.findByDidacticUnitId(didacticUnitId)
                .map(classroomMapper::toDomain);
    }

    @Override
    public Flux<Classroom> findByStudyProgramId(String studyProgramId) {
        return repository.findByStudyProgramId(studyProgramId)
                .map(classroomMapper::toDomain);
    }
}
