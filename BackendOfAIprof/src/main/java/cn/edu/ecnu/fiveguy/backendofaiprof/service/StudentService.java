package cn.edu.ecnu.fiveguy.backendofaiprof.service;

import cn.edu.ecnu.fiveguy.backendofaiprof.model.DO.StudentDO;

public interface StudentService {
    void registerStudent();
    StudentDO queryStudentInfo();
    void updateStudentInfo(StudentDO student);
}
