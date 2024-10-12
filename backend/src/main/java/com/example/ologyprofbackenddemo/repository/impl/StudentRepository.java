package com.example.ologyprofbackenddemo.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ologyprofbackenddemo.mapper.StudentMapper;
import com.example.ologyprofbackenddemo.model.DO.StudentDO;
import com.example.ologyprofbackenddemo.repository.IStudentRepo;
import org.springframework.stereotype.Service;

@Service
public class StudentRepository extends ServiceImpl<StudentMapper, StudentDO> implements IStudentRepo {
}
