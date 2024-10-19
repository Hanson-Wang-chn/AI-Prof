package com.example.ologyprofbackenddemo.model.DO;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("history")

public class HistoryDO {
    private String userId;     //外键 varchar(32)
    private int groupId;       //外键 int
    private String conversation;//text
    private int type;           //tinyInt(1)
    //1 for query, 0 for response
    private LocalDateTime time; //timestamp
}
