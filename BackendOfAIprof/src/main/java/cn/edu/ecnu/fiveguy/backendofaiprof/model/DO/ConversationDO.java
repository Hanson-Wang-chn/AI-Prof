package cn.edu.ecnu.fiveguy.backendofaiprof.model.DO;

//used by ChatDO

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ConversationDO {
    private int type;
    private String conversation;
    private LocalDate created_at;
}
