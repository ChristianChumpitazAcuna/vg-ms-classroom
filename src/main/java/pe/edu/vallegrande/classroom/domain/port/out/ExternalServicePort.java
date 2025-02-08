package pe.edu.vallegrande.classroom.domain.port.out;

import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.AcademicPeriodDTO;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.DidacticUnitDTO;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.EnrollmentDetailDTO;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.StudyProgramDTO;
import reactor.core.publisher.Mono;

public interface ExternalServicePort {
    Mono<AcademicPeriodDTO> getAcademicPeriod(String id);

    Mono<StudyProgramDTO> getStudyProgram(String id);

    Mono<DidacticUnitDTO> getDidacticUnit(String id);

    Mono<EnrollmentDetailDTO> getEnrollmentDetail(String id);
}
