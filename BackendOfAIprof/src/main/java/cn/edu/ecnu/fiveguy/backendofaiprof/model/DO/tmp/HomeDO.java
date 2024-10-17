package cn.edu.ecnu.fiveguy.backendofaiprof.model.DO.tmp;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class HomeDO {
    private String message;
    private LocalDate date;//like 2024-10-8
    //待定
}
