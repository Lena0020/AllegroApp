package pl.kurs.java.AllegroAppl.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequestBank {
    private String name;
    private String username;
    private int balance;
}