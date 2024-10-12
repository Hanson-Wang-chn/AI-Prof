package com.example.ologyprofbackenddemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ologyprofbackenddemo.common.enums.OpExceptionEnum;
import com.example.ologyprofbackenddemo.common.enums.ResponseCodeEnum;
import com.example.ologyprofbackenddemo.common.exception.OpException;
import com.example.ologyprofbackenddemo.model.DO.HistoryDO;
import com.example.ologyprofbackenddemo.model.DO.StudentDO;
import com.example.ologyprofbackenddemo.model.VO.UserInfoVO;
import com.example.ologyprofbackenddemo.repository.IStudentRepo;
import com.example.ologyprofbackenddemo.repository.impl.StudentRepository;
import com.example.ologyprofbackenddemo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    StudentRepository studentRepository;

    @Override
    @Transactional
    public ResponseCodeEnum createStudent(String userName, int classNum, String email, String password){
        //检测学生名是否存在
        LambdaQueryWrapper<StudentDO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(StudentDO::getUserName, userName);
        // 查询数据库
        StudentDO existingStudent1 = studentRepository.getOne(queryWrapper1);
        // 学生名存在，异常处理
        if (existingStudent1 != null) {
            throw new OpException(OpExceptionEnum.USER_NAME_DUPLICATION);
        }

        //检测邮箱是否存在
        LambdaQueryWrapper<StudentDO> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(StudentDO::getEmail,email);
        // 查询数据库
        StudentDO existingStudent2 = studentRepository.getOne(queryWrapper2);
        // 邮箱存在，异常处理
        if (existingStudent2 != null) {
            throw new OpException(OpExceptionEnum.USER_EMAIL_DUPLICATION);
        }

        //创建新用户
        //生成UserId，采用UUID去掉连字符
        String userId = UUID.randomUUID().toString().replaceAll("-", "");
        //生成Student记录并插入
        StudentDO newStudent = StudentDO.builder()
                .userId(userId)
                .userName(userName)
                .classNum(classNum)
                .email(email)
                .password(password)
            .build();
        studentRepository.save(newStudent);

        //返回枚举代码
        return ResponseCodeEnum.SUCCESS;

    }

    @Override
    public UserInfoVO getStuInfo(String userId,String password){
        //根据学号获取学生信息
        LambdaQueryWrapper<StudentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentDO::getUserId, userId);
        queryWrapper.eq(StudentDO::getPassword, password);
        StudentDO studentDO = studentRepository.getOne(queryWrapper);
        //异常处理
        if(studentDO == null){
            throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);
        }

        //返回学生信息
        UserInfoVO userInfoVO = new UserInfoVO(studentDO);
        return userInfoVO;
    }

    @Override
    public void updateStuPwd(String userId,String newPassword){
        //判断是否传了id
        if(userId == null) throw  new OpException(OpExceptionEnum.USER_ID_EMPTY);
        //判断学生是否存在
        StudentDO existingStudent = studentRepository.getById(userId);
        if(existingStudent == null){
            throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);
        }
        //修改学生的密码
        existingStudent.setPassword(newPassword);
        //根据id修改数据库内容
        studentRepository.updateById(existingStudent);
    }
}
