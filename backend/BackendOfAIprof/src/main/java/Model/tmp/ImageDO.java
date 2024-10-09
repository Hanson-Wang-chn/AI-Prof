package Model.tmp;

//used by GuidanceDO

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ImageDO {
    private int image_id;
    private String image_name;
}
