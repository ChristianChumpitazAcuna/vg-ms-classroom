package pe.edu.vallegrande.classroom.application.port.dto.req;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomRequestDTO {
    @NotNull(message = "Name is required")
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "Name must have only letters")
    private String name;

    @NotNull(message = "Academic period is required")
    private String academicPeriodId;

    @NotNull(message = "Study program is required")
    private String studyProgramId;

    @NotNull(message = "Didactic unit is required")
    private String didacticUnitId;

    @NotNull(message = "Enrollment details are required")
    private List<String> enrollmentDetailIds;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be greater than 0")
    @Max(value = 10, message = "Capacity must be less than 10")
    private int capacity;
}
