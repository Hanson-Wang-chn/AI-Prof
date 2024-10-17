package cn.edu.ecnu.fiveguy.backendofaiprof.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.edu.ecnu.fiveguy.backendofaiprof.model.DO.StudentDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<StudentDO> {
}
