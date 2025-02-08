package pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DidacticUnitDTO {
    private String didacticId;
    private String name;
    private String studyProgramId;
    private String credit;
    private String hours;
    private String condition;
    private String status;
}
