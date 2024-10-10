package com.example.ologyprofbackenddemo.service;

import com.example.ologyprofbackenddemo.model.VO.HistoryGroupVO;
import java.util.List;

public interface HistoryGroupService {
    HistoryGroupVO buildGroup(String userId);
    List<HistoryGroupVO> getAllGroups(String userId);
    void deleteGroup(String userId,int groupId);
}
