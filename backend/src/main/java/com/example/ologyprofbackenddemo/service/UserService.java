package com.example.ologyprofbackenddemo.service;

import com.example.ologyprofbackenddemo.model.VO.UserInfoVO;

public interface UserService {
    Void createStudent(String userName, int classNum, String email, String password);
    UserInfoVO getStuInfo(String userId,String password);
    Void updateStuPwd(String userId,String newPassword);
}
