package Model.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("students")

public class StudentDO {
    private String user_id; //varchar(32)
    private String user_name;//学号 varchar(11)
    private int class_num;  //Tinyint //待定
    private String email;   //varchar(100)
    private String password;//varchar(100)
}
