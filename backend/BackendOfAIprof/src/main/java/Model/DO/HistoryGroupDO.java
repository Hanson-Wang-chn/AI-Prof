package Model.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("historygroup")

public class HistoryGroupDO {
    private String group_name;  //varchar(20)
    private int group_id;       //int
    private String user_id;     //外键 varchar(32)
}
