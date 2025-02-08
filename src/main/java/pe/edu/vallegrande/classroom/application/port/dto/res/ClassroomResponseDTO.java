package pe.edu.vallegrande.classroom.application.port.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto.EnrollmentDetailDTO;
import pe.edu.vallegrande.classroom.application.port.dto.HeaderDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomResponseDTO {
    private String classroomId;
    private String name;
    private HeaderDTO header;
    private List<EnrollmentDetailDTO> enrollmentDetailId;
    private int capacity;
    private String status;
}
