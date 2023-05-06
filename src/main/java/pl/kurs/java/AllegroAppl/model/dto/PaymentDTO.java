package pl.kurs.java.AllegroAppl.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private int amount;
    private String username;
    private String tokenValue;
}
