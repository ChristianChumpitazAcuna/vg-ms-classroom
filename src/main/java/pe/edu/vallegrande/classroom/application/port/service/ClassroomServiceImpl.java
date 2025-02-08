package pe.edu.vallegrande.classroom.application.port.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.classroom.application.port.dto.HeaderDTO;
import pe.edu.vallegrande.classroom.application.port.dto.res.ClassroomResponseDTO;
import pe.edu.vallegrande.classroom.application.port.in.ClassroomServicePort;
import pe.edu.vallegrande.classroom.application.port.out.ClassroomRequestMapper;
import pe.edu.vallegrande.classroom.domain.model.Classroom;
import pe.edu.vallegrande.classroom.application.port.dto.req.ClassroomRequestDTO;
import pe.edu.vallegrande.classroom.domain.port.in.ClassroomPersistencePort;
import pe.edu.vallegrande.classroom.domain.port.out.ExternalServicePort;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.AcademicPeriodDTO;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.DidacticUnitDTO;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.EnrollmentDetailDTO;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.StudyProgramDTO;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.exception.CapacityExceededException;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.exception.NotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomServicePort {
    private final ClassroomPersistencePort persistencePort;
    private final ExternalServicePort externalServicePort;
    private final ClassroomRequestMapper mapper;

    @Override
    public Mono<Classroom> createClassroom(ClassroomRequestDTO classroom) {
        return validateCapacity(classroom)
                .then(validateIds(classroom))
                .then(Mono.fromCallable(() -> mapper.toDomain(classroom)))
                .flatMap(persistencePort::save);
    }

    @Override
    public Mono<Classroom> updateClassroom(String id, ClassroomRequestDTO classroom) {
        return persistencePort.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Classroom not found with ID: " + id)))
                .then(validateCapacity(classroom))
                .then(validateIds(classroom))
                .then(Mono.defer(() -> {
                    Classroom classroomToUpdate = mapper.toDomain(classroom);
                    classroomToUpdate.setId(id);
                    return persistencePort.update(classroomToUpdate);
                }));
    }

    @Override
    public Mono<ClassroomResponseDTO> getClassroomById(String id) {
        return persistencePort.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Classroom not found with ID: " + id)))
                .flatMap(this::convertToResponse);
    }

    @Override
    public Flux<ClassroomResponseDTO> getClassroomByStatus(String status) {
        return persistencePort.findByStatus(status)
                .flatMap(this::convertToResponse)
                .collectList()
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Flux<Classroom> searchClassroom(String searchTerm, String status) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return persistencePort.findByStatus(status);
        }
        return persistencePort.searchByTerm(searchTerm, status);
    }

    @Override
    public Flux<ClassroomResponseDTO> getClassroomByStudyProgramId(String studyProgramId) {
        return persistencePort.findByStudyProgramId(studyProgramId)
                .flatMap(this::convertToResponse);
    }

    @Override
    public Flux<ClassroomResponseDTO> getClassroomByDidacticUnitId(String didacticUnitId) {
        return persistencePort.findByDidacticUnitId(didacticUnitId)
                .flatMap(this::convertToResponse);
    }

    @Override
    public Flux<DidacticUnitDTO> getDidacticUnitByClassroomId(String classroomId) {
        return persistencePort.findById(classroomId)
                .flatMapMany(classroom ->
                        Flux.fromIterable(classroom.getEnrollmentDetailIds()))
                .flatMap(externalServicePort::getEnrollmentDetail)
                .flatMap(enrollmentDetailDTO ->
                        Flux.fromIterable(
                                enrollmentDetailDTO.getDidacticUnit()))
                .flatMap(customDidacticUnitDTO ->
                        externalServicePort.getDidacticUnit(
                                customDidacticUnitDTO.getDidacticId()))
                .distinct();
    }

    @Override
    public Mono<Void> changeClassroomStatus(String id, String status) {
        return persistencePort.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Classroom not found with ID: " + id)))
                .then(persistencePort.changeStatus(id, status));
    }

    private Mono<Void> validateCapacity(ClassroomRequestDTO classroom) {
        int currentEnrollments = classroom.getEnrollmentDetailIds() != null
                ? classroom.getEnrollmentDetailIds().size() : 0;

        int capacity = classroom.getCapacity();

        if (currentEnrollments >= capacity) {
            return Mono.error(new CapacityExceededException("Classroom capacity exceeded"));
        }

        return Mono.empty();
    }

    private Mono<Void> validateIds(ClassroomRequestDTO classroom) {
        Mono<Void> academicPeriodValidation = externalServicePort.getAcademicPeriod(classroom.getAcademicPeriodId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Academic period not found with ID: " + classroom.getAcademicPeriodId())))
                .then();

        Mono<Void> studyProgramValidation = externalServicePort.getStudyProgram(classroom.getStudyProgramId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Study program not found with ID: " + classroom.getStudyProgramId())))
                .then();

        Mono<Void> didacticUnitValidation = externalServicePort.getDidacticUnit(classroom.getDidacticUnitId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Didactic unit not found with ID: " + classroom.getDidacticUnitId())))
                .then();

        Mono<Void> enrollmentDetailValidation = Flux.fromIterable(classroom.getEnrollmentDetailIds())
                .flatMap(externalServicePort::getEnrollmentDetail)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Enrollment detail not found with ID: " + classroom.getEnrollmentDetailIds())))
                .then();

        return Mono.when(
                academicPeriodValidation,
                studyProgramValidation,
                didacticUnitValidation,
                enrollmentDetailValidation
        );
    }

    private Mono<ClassroomResponseDTO> convertToResponse(Classroom classroom) {
        ClassroomResponseDTO response = new ClassroomResponseDTO();
        response.setClassroomId(classroom.getId());
        response.setName(classroom.getName());
        response.setCapacity(classroom.getCapacity());
        response.setStatus(classroom.getStatus());

        List<String> enrollmentDetailIds = classroom.getEnrollmentDetailIds() != null
                ? classroom.getEnrollmentDetailIds() : Collections.emptyList();

        Mono<AcademicPeriodDTO> academicPeriodDTOMono = externalServicePort.getAcademicPeriod(
                classroom.getAcademicPeriodId()).defaultIfEmpty(new AcademicPeriodDTO());

        Mono<StudyProgramDTO> studyProgramDTOMono = externalServicePort.getStudyProgram(
                classroom.getStudyProgramId()).defaultIfEmpty(new StudyProgramDTO());

        Mono<DidacticUnitDTO> didacticUnitDTOMono = externalServicePort.getDidacticUnit(
                classroom.getDidacticUnitId()).defaultIfEmpty(new DidacticUnitDTO());

        Mono<List<EnrollmentDetailDTO>> enrollmentDetailMono = Flux.fromIterable(enrollmentDetailIds)
                .flatMap(id -> externalServicePort.getEnrollmentDetail(id)
                        .onErrorResume(e -> Mono.empty()))
                .collectList()
                .defaultIfEmpty(Collections.emptyList());

        return Mono.zip(
                academicPeriodDTOMono,
                studyProgramDTOMono,
                didacticUnitDTOMono,
                enrollmentDetailMono
        ).map(tuple -> {
            AcademicPeriodDTO academicPeriodDTO = tuple.getT1();
            StudyProgramDTO studyProgramDTO = tuple.getT2();
            DidacticUnitDTO didacticUnitDTO = tuple.getT3();
            HeaderDTO header = buildHeader(academicPeriodDTO, studyProgramDTO, didacticUnitDTO);

            response.setHeader(header);
            response.setEnrollmentDetailId(tuple.getT4());
            return response;
        });
    }

    private HeaderDTO buildHeader(AcademicPeriodDTO academicPeriodDTO,
                                  StudyProgramDTO studyProgramDTO,
                                  DidacticUnitDTO didacticUnitDTO) {
        HeaderDTO header = new HeaderDTO();
        header.setAcademicPeriodId(academicPeriodDTO.getIdAcademicPeriod());
        header.setAcademicPeriodName(academicPeriodDTO.getAcademicPeriod());
        header.setAcademicPeriodStatus(academicPeriodDTO.getStatus());

        header.setProgramId(studyProgramDTO.getProgramId());
        header.setProgramName(studyProgramDTO.getName());
        header.setProgramModule(studyProgramDTO.getModule());
        header.setProgramStatus(studyProgramDTO.getStatus());

        header.setDidacticId(didacticUnitDTO.getDidacticId());
        header.setDidacticName(didacticUnitDTO.getName());
        header.setDidacticProgramId(didacticUnitDTO.getStudyProgramId());
        header.setDidacticStatus(didacticUnitDTO.getStatus());

        return header;
    }
}
