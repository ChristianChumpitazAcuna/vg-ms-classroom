package pe.edu.vallegrande.classroom.infraestructure.adapter.out.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String id;
    private String documentType;
    private String documentNumber;
    private String lastNamePaternal;
    private String lastNameMaternal;
    private String names;
}
