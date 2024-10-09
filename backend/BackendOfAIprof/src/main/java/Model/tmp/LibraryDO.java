package Model.tmp;

import java.util.List;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LibraryDO {
    private String message;
    private List<LibraryDataDO> data=new ArrayList<>();
}
