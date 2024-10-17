package cn.edu.ecnu.fiveguy.backendofaiprof.service;

import cn.edu.ecnu.fiveguy.backendofaiprof.model.DO.ChatDO;

import java.util.List;

public interface ChatService {
    Long insert();
    List<ChatDO> getList();
    Void delete(Long chatId);
}
