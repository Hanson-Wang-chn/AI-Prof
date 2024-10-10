package com.example.ologyprofbackenddemo.model.VO;

import com.example.ologyprofbackenddemo.model.DO.HistoryDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationVO {
    private String text;//text
    private int type;           //tinyInt(1)
    //1 for query, 0 for response
    private LocalDateTime time; //timestamp

    public ConversationVO(HistoryDO historyDO) {
        this.text=historyDO.getConversation();
        this.type=historyDO.getType();
        this.time=historyDO.getTime();
    }
}
