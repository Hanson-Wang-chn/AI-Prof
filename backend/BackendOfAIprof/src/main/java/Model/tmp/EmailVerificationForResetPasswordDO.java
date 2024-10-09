package Model.tmp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EmailVerificationForResetPasswordDO {
    private String email;
    private String new_pwd;
    private String message;
}
