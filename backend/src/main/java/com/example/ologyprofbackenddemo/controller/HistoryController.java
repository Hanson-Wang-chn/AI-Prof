package com.example.ologyprofbackenddemo.controller;

import com.example.ologyprofbackenddemo.common.base.BaseResponse;
import com.example.ologyprofbackenddemo.common.exception.OpException;
import com.example.ologyprofbackenddemo.model.VO.ConversationVO;
import com.example.ologyprofbackenddemo.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/ui/study/chat/{userId}/{groupId}")
public class HistoryController {
    @Resource
    HistoryService historyService;

    @RequestMapping("/message")
    public BaseResponse<ConversationVO> insertHistory(@PathVariable String userId,
                                                      @PathVariable int groupId,
                                                      @RequestParam String text,
                                                      @RequestParam int type) {
        try {
            return BaseResponse.buildSuccess(historyService.insertConversation(userId, groupId, text, type));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping
    public BaseResponse<List<ConversationVO>> getHistories(@PathVariable String userId, @PathVariable int groupId) {
        try {
            return BaseResponse.buildSuccess(historyService.getConversation(userId, groupId));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }
}
