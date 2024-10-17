package cn.edu.ecnu.fiveguy.backendofaiprof.model.DO.tmp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationForRegistionDO {
    private String email;
    private String user_id;
    private int code;
    private String message;
}
