package pl.kurs.java.AllegroAppl.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenCreateRequest {
    private String username;
}
