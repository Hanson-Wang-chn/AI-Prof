package com.example.ologyprofbackenddemo.model.VO;

import com.example.ologyprofbackenddemo.model.DO.HistoryGroupDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryGroupVO {
    private String groupName;  //varchar(20)
    private int groupId;       //int

    public HistoryGroupVO(HistoryGroupDO historyGroupDO ){
        this.groupId = historyGroupDO.getGroupId();
        this.groupName = historyGroupDO.getGroupName();
    }
}
