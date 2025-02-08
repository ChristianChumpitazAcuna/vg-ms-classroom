package pe.edu.vallegrande.classroom.infraestructure.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.classroom.application.port.dto.res.ClassroomResponseDTO;
import pe.edu.vallegrande.classroom.application.port.in.ClassroomServicePort;
import pe.edu.vallegrande.classroom.domain.model.Classroom;
import pe.edu.vallegrande.classroom.application.port.dto.req.ClassroomRequestDTO;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.DidacticUnitDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/management/${api.version}/classroom")
@RequiredArgsConstructor
public class ClassroomController {
    private final ClassroomServicePort servicePort;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Classroom> create(@Valid @RequestBody ClassroomRequestDTO classroomRequest) {
        return servicePort.createClassroom(classroomRequest);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Classroom> update(@PathVariable String id, @Valid @RequestBody ClassroomRequestDTO classroomRequest) {
        return servicePort.updateClassroom(id, classroomRequest);
    }

    @PutMapping("/active/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> active(@PathVariable String id) {
        return servicePort.changeClassroomStatus(id, "A");
    }

    @PutMapping("/inactive/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> inactive(@PathVariable String id) {
        return servicePort.changeClassroomStatus(id, "I");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClassroomResponseDTO> getById(@PathVariable String id) {
        return servicePort.getClassroomById(id);
    }

    @GetMapping("/list/active")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ClassroomResponseDTO> getAllActive() {
        return servicePort.getClassroomByStatus("A");
    }

    @GetMapping("/list/inactive")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ClassroomResponseDTO> getAllInactive() {
        return servicePort.getClassroomByStatus("I");
    }

    @GetMapping("/didactic-unit/{classroomId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ClassroomResponseDTO> getClassroomByDidacticUnitId(@PathVariable String classroomId) {
        return servicePort.getClassroomByDidacticUnitId(classroomId);
    }

    @GetMapping("/study-program/{studyProgramId}/classrooms")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ClassroomResponseDTO> getClassroomByStudyProgramId(@PathVariable String studyProgramId) {
        return servicePort.getClassroomByStudyProgramId(studyProgramId);
    }

    @GetMapping("/classroom/{classroomId}/didactic-units")
    @ResponseStatus(HttpStatus.OK)
    public Flux<DidacticUnitDTO> getDidacticUnitByClassroomId(@PathVariable String classroomId) {
        return servicePort.getDidacticUnitByClassroomId(classroomId);
    }
}
