package com.example.ologyprofbackenddemo.controller;

import com.example.ologyprofbackenddemo.model.VO.UserInfoVO;
import com.example.ologyprofbackenddemo.service.UserService;

import com.example.ologyprofbackenddemo.common.base.BaseResponse;
import com.example.ologyprofbackenddemo.common.exception.OpException;
import com.example.ologyprofbackenddemo.common.jwt.AuthStorage;
import com.example.ologyprofbackenddemo.common.jwt.JwtUser;
import com.example.ologyprofbackenddemo.service.VerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final JavaMailSender javaMailSender;

    @Resource
    private VerificationService verificationService;

    @Resource
    private UserService userService;

    public UserController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @PostMapping("/register")
    public BaseResponse<Void> userRegister(@RequestParam("userName") String userName,
                                     @RequestParam("classNum") int classNUm,
                                     @RequestParam("email") String email,
                                     @RequestParam("password") String password){
        try {
            return BaseResponse.buildSuccess(userService.createStudent(userName, classNUm, email, password));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping("/verification_register")
    public BaseResponse<Void> VerificationRegister(@RequestParam String email) {
        try {
            return BaseResponse.buildSuccess(verificationService.SendEmail(javaMailSender, email));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping("/verify/{email}")
    public BaseResponse<Void> verifyCode(@PathVariable String email, @RequestParam("code") String code) {
        try {
            return BaseResponse.buildSuccess(verificationService.VerifyCode(email,code));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @PostMapping("/login")
    public BaseResponse<UserInfoVO> logIn(@RequestParam String password) {
        JwtUser jwtUser = AuthStorage.getUser();
        log.info("Get student information: studentId = {}", jwtUser.getUserId());
        try {
            return BaseResponse.buildSuccess(userService.getStuInfo(jwtUser.getUserId(),password));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @PostMapping("/reset_pwd")
    public BaseResponse<Void> ResetStuPwd(@RequestParam String newPassword) {
        JwtUser jwtUser = AuthStorage.getUser();
        log.info("Update student password: studentId = {}", jwtUser.getUserId());
        try {
            return BaseResponse.buildSuccess(userService.updateStuPwd(jwtUser.getUserId(),newPassword));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping("/verification_reset_pwd")
    public BaseResponse<Void> VerificationResetPed(@RequestParam String email) {
        try {
            return BaseResponse.buildSuccess(verificationService.SendEmail(javaMailSender, email));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping("/{userId}")
    public BaseResponse<UserInfoVO> getUserInfo(@PathVariable String userId,@RequestParam String password) {
        try{
            return BaseResponse.buildSuccess(userService.getStuInfo(userId,password));
        }catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

}
