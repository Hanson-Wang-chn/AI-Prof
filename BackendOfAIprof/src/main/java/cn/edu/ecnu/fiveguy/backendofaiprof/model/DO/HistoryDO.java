package cn.edu.ecnu.fiveguy.backendofaiprof.model.DO;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("history")

public class
HistoryDO {
    private String user_id;     //外键 varchar(32)
    private int group_id;       //外键 int
    private String conversasion;//text
    private boolean type;       //tinyInt(1)
    //1 for query, 0 for response
    private LocalDateTime time; //timestamp
}
