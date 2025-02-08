package pe.edu.vallegrande.classroom.application.port.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeaderDTO {
    private String academicPeriodId;
    private String academicPeriodName;
    private String academicPeriodStatus;
    //
    private String programId;
    private String programName;
    private String programModule;
    private String programStatus;
    //
    private String didacticId;
    private String didacticName;
    private String didacticProgramId;
    private String didacticStatus;
}
