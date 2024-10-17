package cn.edu.ecnu.fiveguy.backendofaiprof.model.DO.tmp;

//used by GuidanceDO

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MindmapDO {
    private int mindmap_id;
    private String mindmap_name;
}
