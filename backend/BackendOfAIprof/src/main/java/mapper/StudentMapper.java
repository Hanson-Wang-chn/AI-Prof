package mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import Model.DO.StudentDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<StudentDO> {
}
