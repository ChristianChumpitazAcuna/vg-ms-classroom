package pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "new_classrooms")
@Getter
@Setter
public class ClassroomEntity {
    @Id
    private String id;
    private String name;
    private String academicPeriodId;
    private String studyProgramId;
    private String didacticUnitId;
    private List<String> enrollmentDetailIds;
    private Integer capacity;
    private String status;
}
