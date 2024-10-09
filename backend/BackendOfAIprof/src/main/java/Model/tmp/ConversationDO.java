package Model.tmp;

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
    private String conversation_id;
    private String conversation_name;
    private LocalDate created_at;
}
