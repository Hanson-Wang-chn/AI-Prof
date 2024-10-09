package Model.tmp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserLoginDO {
    private String email;
    private String password;
    private String message;
    private String user_id;
    private String token;
}
