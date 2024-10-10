package com.example.ologyprofbackenddemo.model.VO;

import com.example.ologyprofbackenddemo.model.DO.StudentDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {
    private String userId; //varchar(32)
    private String userName;//学号 varchar(11)
    private int classNum;  //Tinyint //待定
    private String email;   //varchar(100)

    public UserInfoVO(StudentDO stu) {
        this.userId = stu.getUserId();
        this.userName=stu.getUserName();
        this.email=stu.getEmail();
        this.classNum=stu.getClassNum();
    }
}
