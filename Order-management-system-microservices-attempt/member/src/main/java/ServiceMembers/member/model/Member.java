package ServiceMembers.member.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private UUID memberId;
    private String fullName;
    private String email;
    private String password;

}
