package cn.edu.ecnu.fiveguy.backendofaiprof.controller;

import cn.edu.ecnu.fiveguy.backendofaiprof.common.base.BaseResponse;
import cn.edu.ecnu.fiveguy.backendofaiprof.common.exception.OpException;
import cn.edu.ecnu.fiveguy.backendofaiprof.model.VO.ChatVO;
import cn.edu.ecnu.fiveguy.backendofaiprof.model.VO.MessageVO;
import cn.edu.ecnu.fiveguy.backendofaiprof.model.VO.SMessageVO;
import cn.edu.ecnu.fiveguy.backendofaiprof.service.ChatService;
import cn.edu.ecnu.fiveguy.backendofaiprof.service.HistoryService;
import cn.edu.ecnu.fiveguy.backendofaiprof.service.TutorHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/chat")
public class ChatController {
    @Resource
    ChatService chatService;

    @Resource
    HistoryService historyService;

    @Resource
    TutorHistoryService tutorHistoryService;

    @PostMapping
    public BaseResponse<Long> createChat() {
        try {
            return BaseResponse.buildSuccess(chatService.insert());
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping("/list")
    public BaseResponse<List<ChatVO>> getChatList() {
        try {
            return BaseResponse.buildSuccess(chatService.getList());
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @DeleteMapping
    public BaseResponse<Void> deleteChat(Long chatId) {
        try {
            return BaseResponse.buildSuccess(chatService.delete(chatId));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @PostMapping("/message")
    public BaseResponse<List<MessageVO>> buildMessage(Long chatId, @RequestBody List<SMessageVO> sMessageList) {
        try {
            return BaseResponse.buildSuccess(historyService.buildMessage(chatId, sMessageList));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping("/history")
    public BaseResponse<List<MessageVO>> getHistory(Long chatId) {
        try {
            return BaseResponse.buildSuccess(historyService.getHistory(chatId));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @PostMapping("/tutor")
    public BaseResponse<Void> buildTutorMessage(@RequestBody List<SMessageVO> sMessageList) {
        try {
            return BaseResponse.buildSuccess(tutorHistoryService.buildMessage(sMessageList));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }
}
