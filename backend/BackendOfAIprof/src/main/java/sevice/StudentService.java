package sevice;

import Model.DO.StudentDO;

public interface StudentService {
    void registerStudent();
    StudentDO queryStudentInfo();
    void updateStudentInfo(StudentDO student);
}
