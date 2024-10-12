package com.example.ologyprofbackenddemo.service;

import com.example.ologyprofbackenddemo.common.enums.ResponseCodeEnum;
import com.example.ologyprofbackenddemo.model.DO.StudentDO;
import com.example.ologyprofbackenddemo.model.VO.UserInfoVO;

public interface UserService {
    ResponseCodeEnum createStudent(String userName, int classNum, String email, String password);
    UserInfoVO getStuInfo(String userId,String password);
    void updateStuPwd(String userId,String newPassword);
}
