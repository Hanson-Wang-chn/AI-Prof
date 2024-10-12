package com.example.ologyprofbackenddemo.controller;

import com.example.ologyprofbackenddemo.common.base.BaseResponse;
import com.example.ologyprofbackenddemo.common.exception.OpException;
import com.example.ologyprofbackenddemo.model.VO.ConversationVO;
import com.example.ologyprofbackenddemo.model.VO.HistoryGroupVO;
import com.example.ologyprofbackenddemo.service.HistoryGroupService;
import com.example.ologyprofbackenddemo.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/study/chat/{userId}")
public class ChatController {

    @Resource
    HistoryService historyService;

    @Resource
    HistoryGroupService historyGroupService;

    @GetMapping
    public BaseResponse<HistoryGroupVO> createGroup(@PathVariable String userId) {
        try {
            return BaseResponse.buildSuccess(historyGroupService.buildGroup(userId));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping
    public BaseResponse<List<HistoryGroupVO>> getGroups(@PathVariable String userId) {
        try {
            return BaseResponse.buildSuccess(historyGroupService.getAllGroups(userId));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @DeleteMapping
    public BaseResponse<Void> deleteGroups(@PathVariable String userId, @RequestParam(required = true) int groupId) {
        try {
            return BaseResponse.buildSuccess(historyGroupService.deleteGroup(userId, groupId));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping
    public BaseResponse<ConversationVO> insertHistory(@PathVariable String userId,
                                                      @RequestParam(required = true) int groupId,
                                                      @RequestParam(required = true) String text,
                                                      @RequestParam(required = true) int type) {
        try {
            return BaseResponse.buildSuccess(historyService.insertCoversation(userId, groupId, text, type));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping
    public BaseResponse<List<ConversationVO>> getHistories(@PathVariable String userId, @RequestParam(required = true) int groupId) {
        try {
            return BaseResponse.buildSuccess(historyService.getConversation(userId, groupId));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }
}
