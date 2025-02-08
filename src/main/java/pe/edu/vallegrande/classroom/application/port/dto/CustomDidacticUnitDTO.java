package pe.edu.vallegrande.classroom.application.port.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomDidacticUnitDTO {
    private String didacticId;
    private String studyProgramId;
    private String status;
}
