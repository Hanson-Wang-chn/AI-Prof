package cn.edu.ecnu.fiveguy.backendofaiprof.model.DO.tmp;

//used by LibraryDO

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LibraryDataDO {
    private String question_id;
    private String knowledge_point;
    private String describetion;
    private String answer;
}
