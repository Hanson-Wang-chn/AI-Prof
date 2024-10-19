package com.example.ologyprofbackenddemo.service.impl;

import com.example.ologyprofbackenddemo.common.enums.OpExceptionEnum;
import com.example.ologyprofbackenddemo.common.exception.OpException;
import com.example.ologyprofbackenddemo.service.VerificationService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VerificationServiceImpl implements VerificationService {

    // 用于存储验证码的临时存储
    private static final Map<String, String> verificationCodes = new ConcurrentHashMap<>();
    private static final int CODE_EXPIRATION_TIME = 300; // 验证码有效期300（秒）

    @Override
    public Void SendEmail(JavaMailSender javaMailSender, String email) {
        // 生成6位数字的验证码
        String verificationCode = generateVerificationCode();

        // 存储验证码到临时存储，并设置过期时间
        verificationCodes.put(email, verificationCode);
        scheduleCodeExpiration(email);
        // 注意：这里假设javaMailSender已经是一个配置好的JavaMailSender实例，它内部应该使用Jakarta Mail
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        // 设置收件人、主题和正文，这些调用在MimeMessageHelper中通常不会因JavaMail到Jakarta Mail的迁移而改变
        try {
            helper.setTo(email);
            helper.setSubject("Your Verification Code from OlogyProf");
            helper.setText("Your verification code is: " + verificationCode, true); // true表示发送HTML格式的邮件
        }catch (MessagingException e) {
            throw new OpException(OpExceptionEnum.EXECUTE_FAIL);
        }

        // 发送邮件
        javaMailSender.send(message);
        return null;
    }

    @Override
    public Void VerifyCode(String email, String code){
        //获取用户右相对应的发出的验证码
        String storedCode = verificationCodes.get(email);

        //验证
        if (storedCode != null && storedCode.equals(code)){
            verificationCodes.remove(email);
            return null;
        }else{
            throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);
        }

    }

    //生成验证码(字符串)
    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); // 生成6位数字
        return String.valueOf(code);
    }

    //验证码过期处理
    private void scheduleCodeExpiration(String email) {
        // 使用ScheduledExecutorService或其他方式实现验证码过期
        // 这里为了简化，只是演示逻辑，实际项目中应使用更健壮的定时任务机制
        new Thread(() -> {
            try {
                Thread.sleep(CODE_EXPIRATION_TIME * 1000);
                verificationCodes.remove(email);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
