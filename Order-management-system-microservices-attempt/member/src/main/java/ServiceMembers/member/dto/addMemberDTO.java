package ServiceMembers.member.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class addMemberDTO {
    @Valid
    @NotBlank(message="Full name is required")
    @Pattern(regexp="[a-zA-z ]+", message="Full name should only contain letters")
    private String fullName;

    @Email
    @NotBlank(message="Invalid email format")
    private String email;

    @Valid
    @NotBlank(message="password is required")
    @Size(min=6, message="Password should be atleast 6 characters")
    private String password;

}
