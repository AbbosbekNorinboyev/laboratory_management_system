package uz.brb.laboratorymanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "fullName can not be null or empty")
    private String fullName;
    @NotBlank(message = "username can not be null or empty")
    private String username;
    @NotBlank(message = "password can not be null or empty")
    private String password;
    @NotBlank(message = "email can not be null or empty")
    private String email;
    @NotBlank(message = "role can not be null or empty")
    private String userRole;
}