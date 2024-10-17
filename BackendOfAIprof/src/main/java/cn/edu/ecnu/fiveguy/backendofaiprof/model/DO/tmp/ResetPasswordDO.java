package cn.edu.ecnu.fiveguy.backendofaiprof.model.DO.tmp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ResetPasswordDO {
    private String email;
    private String message;
}
