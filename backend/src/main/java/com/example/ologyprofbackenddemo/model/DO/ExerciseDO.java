package com.example.ologyprofbackenddemo.model.DO;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("exercises")

public class ExerciseDO {
    private long exeId;     // BIGINT AUTO_INCREMENT
    private String exeGroup;//VARCHAR(30)
    private String question;//TEXT
    private String answer;  //TEXT
    private String knlgPoint;//VARCHAR(255)
    private LocalDateTime updateTime;//DATETIME
}
