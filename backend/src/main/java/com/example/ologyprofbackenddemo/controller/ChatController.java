package com.example.ologyprofbackenddemo.controller;

import com.example.ologyprofbackenddemo.common.base.BaseResponse;
import com.example.ologyprofbackenddemo.common.exception.OpException;
import com.example.ologyprofbackenddemo.model.VO.HistoryGroupVO;
import com.example.ologyprofbackenddemo.service.HistoryGroupService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/ui/study/chat/{userId}")
public class ChatController {

    @Resource
    HistoryGroupService historyGroupService;

    @PostMapping()
    public BaseResponse<HistoryGroupVO> createGroup(@PathVariable String userId) {
        try {
            return BaseResponse.buildSuccess(historyGroupService.buildGroup(userId));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping()
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
    public BaseResponse<Void> deleteGroups(@PathVariable String userId, @RequestParam int groupId) {
        try {
            return BaseResponse.buildSuccess(historyGroupService.deleteGroup(userId, groupId));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }
}
