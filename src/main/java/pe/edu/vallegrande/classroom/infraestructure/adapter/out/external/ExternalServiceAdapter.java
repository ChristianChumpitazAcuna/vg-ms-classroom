package pe.edu.vallegrande.classroom.infraestructure.adapter.out.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.AcademicPeriodDTO;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.DidacticUnitDTO;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.EnrollmentDetailDTO;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.StudyProgramDTO;
import pe.edu.vallegrande.classroom.domain.port.out.ExternalServicePort;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExternalServiceAdapter implements ExternalServicePort {

    private final WebClient.Builder webClientBuilder;

    @Value("${services.study-program.url}")
    private String studyProgramUrl;

    @Value("${services.acdemic-period.url}")
    private String academicPeriodUrl;

    @Value("${services.enrollment-detail.url}")
    private String enrollmentDetailUrl;

    @Value("${services.didactic-unit.url}")
    private String didacticUnitUrl;

    @Override
    public Mono<AcademicPeriodDTO> getAcademicPeriod(String id) {
        return fetchData(academicPeriodUrl + "/id/",
                id, AcademicPeriodDTO.class);
    }

    @Override
    public Mono<StudyProgramDTO> getStudyProgram(String id) {
        return fetchData(studyProgramUrl + "/",
                id, StudyProgramDTO.class);
    }

    @Override
    public Mono<DidacticUnitDTO> getDidacticUnit(String id) {
        return fetchData(didacticUnitUrl + "/",
                id, DidacticUnitDTO.class);
    }

    @Override
    public Mono<EnrollmentDetailDTO> getEnrollmentDetail(String id) {
        return fetchData(enrollmentDetailUrl + "/newFindById/",
                id, EnrollmentDetailDTO.class);
    }

    private <T> Mono<T> fetchData(String baseUrl, String id, Class<T> responseType) {
        return webClientBuilder.baseUrl(baseUrl)
                .build()
                .get()
                .uri(baseUrl + id)
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(e -> {
                    log.error("Error fetching data from external service", e);
                    return Mono.empty();
                });
    }
}
