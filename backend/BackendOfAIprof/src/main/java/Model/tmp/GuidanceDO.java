package Model.tmp;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class GuidanceDO {
    private String message;
    List<ImageDO> images = new ArrayList<>();
    List<MindmapDO> mindmaps = new ArrayList<>();
}
