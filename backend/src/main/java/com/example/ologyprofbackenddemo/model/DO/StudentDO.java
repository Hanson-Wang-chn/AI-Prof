package com.example.ologyprofbackenddemo.model.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("students")

public class StudentDO {
    private String userId; //varchar(32)
    private String userName;//学号 varchar(11)
    private int classNum;  //Tinyint //待定
    private String email;   //varchar(100)
    private String password;//varchar(100)
}
