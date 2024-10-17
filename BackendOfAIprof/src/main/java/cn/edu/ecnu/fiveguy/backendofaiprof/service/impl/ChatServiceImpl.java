package cn.edu.ecnu.fiveguy.backendofaiprof.service.impl;


import com.springboot.cli.common.enums.OpExceptionEnum;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.common.jwt.AuthStorage;
import com.springboot.cli.model.DO.ChatDO;
import com.springboot.cli.model.DO.HistoryDO;
import com.springboot.cli.model.VO.ChatVO;
import com.springboot.cli.repository.impl.ChatRepository;
import com.springboot.cli.repository.impl.HistoryRepository;
import com.springboot.cli.service.ChatService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    ChatRepository chatRepository;

    @Resource
    HistoryRepository historyRepository;

    @Override
    public Long insert() {
        String studentId = AuthStorage.getUser().getUserId();
        ChatDO chat = ChatDO.builder()
                .studentId(studentId)
                .sort(0)
                .longTerm(0)
                .updateTime(LocalDateTime.now())
                .deletedFlag(0)
                .title("新建对话")
                .build();
        System.out.println(chat);
        chatRepository.save(chat);
        return chat.getId();
    }

    @Override
    public List<ChatVO> getList() {
        String studentId = AuthStorage.getUser().getUserId();
        LambdaQueryWrapper<ChatDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatDO::getStudentId, studentId);
        queryWrapper.eq(ChatDO::getDeletedFlag, 0);
        queryWrapper.orderByDesc(ChatDO::getUpdateTime);
        List<ChatDO> chatList = chatRepository.list(queryWrapper);
        List<ChatVO> chatVOList = new ArrayList<>();
        chatList.forEach(chat -> chatVOList.add(new ChatVO(chat)));
        return chatVOList;
    }

    @Override
    @Transactional
    public Void delete(Long chatId) {
        if(chatId == null) throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);
        UpdateWrapper<ChatDO> chatUpdateWrapper = new UpdateWrapper<>();
        chatUpdateWrapper.eq("id", chatId)
                .set("deleted_flag", 1);
        chatRepository.update(chatUpdateWrapper);
        UpdateWrapper<HistoryDO> historyQueryWrapper = new UpdateWrapper<>();
        historyQueryWrapper.eq("chat_id", chatId)
                .set("deleted_flag", 1);
        historyRepository.update(historyQueryWrapper);
        return null;
    }
}
