package com.example.ologyprofbackenddemo.model.DO;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("exerciseHistory")

public class ExeHistoryDO {
    private String userId;
    private long exeId;
    private LocalDateTime exeTime;
}
