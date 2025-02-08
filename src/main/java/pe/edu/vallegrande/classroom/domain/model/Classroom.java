package pe.edu.vallegrande.classroom.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Classroom {
    private String id;
    private String name;
    private String academicPeriodId;
    private String studyProgramId;
    private String didacticUnitId;
    private List<String> enrollmentDetailIds;
    private Integer capacity;
    private String status;
}
