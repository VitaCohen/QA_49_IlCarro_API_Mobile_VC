package dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegistrationBodyDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;


}
