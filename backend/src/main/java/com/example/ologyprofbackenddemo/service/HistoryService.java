package com.example.ologyprofbackenddemo.service;


import com.example.ologyprofbackenddemo.model.VO.ConversationVO;
import java.util.List;

public interface HistoryService {
    ConversationVO insertConversation(String userId,int groupId, String text, int type);
    List<ConversationVO> getConversation(String userId,int groupId);
}
