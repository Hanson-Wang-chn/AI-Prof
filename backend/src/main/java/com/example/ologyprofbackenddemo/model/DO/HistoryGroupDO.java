package com.example.ologyprofbackenddemo.model.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("historygroup")

public class HistoryGroupDO {
    private String groupName;  //varchar(20)
    private int groupId;       //int
    private String userId;     //外键 varchar(32)
}