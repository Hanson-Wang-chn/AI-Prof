package com.example.ologyprofbackenddemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ologyprofbackenddemo.common.enums.OpExceptionEnum;
import com.example.ologyprofbackenddemo.common.exception.OpException;
import com.example.ologyprofbackenddemo.model.DO.HistoryDO;
import com.example.ologyprofbackenddemo.model.VO.ConversationVO;
import com.example.ologyprofbackenddemo.repository.impl.HistoryRepository;
import com.example.ologyprofbackenddemo.service.HistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Resource
    private HistoryRepository historyRepository;

    @Override
    @Transactional
    public ConversationVO insertCoversation(String userId,int groupId, String text, int type){
        //异常处理
        if (userId == null) throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);

        //插入新History记录
        HistoryDO history = HistoryDO.builder()
                .userId(userId)
                .groupId(groupId)
                .conversation(text)
                .type(type)
                .time(LocalDateTime.now())
                .build();
        historyRepository.save(history);

        //返回前端
        ConversationVO conversationVO = new ConversationVO(history);
        return conversationVO;
    }

    @Override
    @Transactional
    //前置：groupId存在
    public List<ConversationVO> getConversation(String userId,int groupId){
        //异常处理
        if (userId == null) throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);

        LambdaQueryWrapper<HistoryDO> queryWrapper = new LambdaQueryWrapper<>();
        //获得同用户同组号的History记录，且按照降序排列
        queryWrapper.eq(HistoryDO::getUserId, userId);
        queryWrapper.eq(HistoryDO::getGroupId, groupId);
        queryWrapper.orderByDesc(HistoryDO::getTime);

        List<HistoryDO> historyList = historyRepository.list(queryWrapper);
        List<ConversationVO> conversationList = new ArrayList<>();
        historyList.forEach(history -> conversationList.add(new ConversationVO(history)));
        return conversationList;
    }

}
