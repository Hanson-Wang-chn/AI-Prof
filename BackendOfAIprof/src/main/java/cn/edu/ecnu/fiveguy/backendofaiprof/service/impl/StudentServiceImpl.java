package cn.edu.ecnu.fiveguy.backendofaiprof.service.impl;

import cn.edu.ecnu.fiveguy.backendofaiprof.model.DO.StudentDO;
import cn.edu.ecnu.fiveguy.backendofaiprof.mapper.StudentMapper;

import cn.edu.ecnu.fiveguy.backendofaiprof.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.Scanner;//测试使用

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper; // 假定StudentMapper已经存在，并提供了基础的CRUD操作

    @Override
    @Transactional
    public void registerStudent() {
        // 构造StudentDO对象
        StudentDO student = StudentDO.builder().build();

        //用户信息输入
        String userName;
        String passwd;
        String email;
        int classNum;
        /*
            获取用户信息
         */
        //测试代码：
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入学号：");
        userName = scanner.nextLine();
        System.out.println("输入邮箱：");
        email = scanner.nextLine();
        System.out.println("输入密码：");
        passwd = scanner.nextLine();
        System.out.println("输入班级：");
        classNum = Integer.parseInt(scanner.nextLine());

        //设置信息
        student.setUser_name(userName);
        student.setClass_num(classNum);
        student.setEmail(email);
        student.setPassword(passwd);
        student.setUser_id(generateUserId());

        // 将学生信息保存到数据库
        studentMapper.insert(student);
    }

    // 用于生成唯一的用户ID
    private String generateUserId() {
        // 实现用户ID的生成逻辑，使用UUID
        return UUID.randomUUID().toString().replace("-", "");
    }


    @Override
    public StudentDO queryStudentInfo() {
        //获取用户id

        //*******测试********
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入uid:");
        String userId = scanner.nextLine();

        // 根据用户ID查询学生信息
        LambdaQueryWrapper<StudentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentDO::getUser_id, userId);

        return studentMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional
    public void updateStudentInfo(StudentDO student) {
        //输入新密码
        String newpwd;
        /*
         * 新密码获取
         */
        //***********测试***********
        System.out.println("请输入新密码：");
        Scanner scanner = new Scanner(System.in);
        newpwd = scanner.nextLine();

        // 更新学生密码
        UpdateWrapper<StudentDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("user_name", student.getUser_name())
                .set("class_num", student.getClass_num())
                .set("email", student.getEmail())
                .set("password", student.getPassword()) // 注意：通常不会直接更新密码，除非有特定的业务需求
                .eq("user_id", student.getUser_id());

        studentMapper.update(null, updateWrapper); // 第一个参数为null，因为我们没有提供要更新的实体对象，只使用了UpdateWrapper
    }
}
