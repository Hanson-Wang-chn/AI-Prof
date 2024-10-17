package cn.edu.ecnu.fiveguy.backendofaiprof.model.DO;

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

public class ChatDO {
    private String message;
    private List<ConversationDO> history = new ArrayList<>();
}
