package pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.vallegrande.classroom.application.port.dto.CustomDidacticUnitDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDetailDTO {
    private String id;
    private StudentDTO student;
    private List<CustomDidacticUnitDTO> didacticUnit;
    private String status;
}


