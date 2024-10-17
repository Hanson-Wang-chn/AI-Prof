package cn.edu.ecnu.fiveguy.backendofaiprof.controller;

/*
    功能：
    查看信息
    修改密码
 */


import base.BaseResponse;
import exception.OpException;
import cn.edu.ecnu.fiveguy.backendofaiprof.common.jwt.AuthStorage;
import cn.edu.ecnu.fiveguy.backendofaiprof.common.jwt.JWTUser;
import cn.edu.ecnu.fiveguy.backendofaiprof.model.DO.StudentDO;
import cn.edu.ecnu.fiveguy.backendofaiprof.service.StudentService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/user")
public class StudentController {

    @Resource
    private StudentService studentService;

    @GetMapping("/{user_id}")
    public BaseResponse<StudentDO> getStuInfo() {
        JWTUser jwtUser = AuthStorage.getUser();
        log.info("Get student information: studentId = {}", jwtUser.getUserId());
        try {
            return BaseResponse.buildSuccess(studentService.queryStudentInfo());
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @PostMapping("/{user_id}")
    public BaseResponse<Void> updateStuInfo(@RequestBody StudentDO student) {
        JwtUser jwtUser = AuthStorage.getUser();
        log.info("Update student information: studentId = {}", jwtUser.getUserId());
        try {
            studentService.updateStuInfo(student);
            return BaseResponse.buildSuccess(null);
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }
}
