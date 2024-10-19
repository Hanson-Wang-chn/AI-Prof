package com.example.ologyprofbackenddemo.controller;

import com.example.ologyprofbackenddemo.common.base.BaseResponse;
import com.example.ologyprofbackenddemo.common.enums.OpExceptionEnum;
import com.example.ologyprofbackenddemo.common.exception.OpException;
import com.example.ologyprofbackenddemo.model.VO.exercise.ExercisePage;
import com.example.ologyprofbackenddemo.service.LibraryService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/study/library/{userId}")
public class LibraryController {

    @Resource
    private LibraryService libraryService;

    @GetMapping("/user_done")
    public BaseResponse<ExercisePage> PageByUser(@PathVariable String userId, @RequestParam int pageNo, @RequestParam int pageSize) {
        if(userId == null || pageNo < 0 || pageSize < 0) {
            return BaseResponse.buildBizEx(OpExceptionEnum.ILLEGAL_ARGUMENT);
        }
        try {
            return BaseResponse.buildSuccess(libraryService.pageByUser(userId, pageNo, pageSize));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping
    public BaseResponse<ExercisePage> PageByGroup(@RequestParam String groupId, @RequestParam int pageNo, @RequestParam int pageSize) {
        if(groupId == null || pageNo < 0 || pageSize < 0) {
            return BaseResponse.buildBizEx(OpExceptionEnum.ILLEGAL_ARGUMENT);
        }
        try {
            return BaseResponse.buildSuccess(libraryService.pageByGroup(groupId, pageNo, pageSize));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }
}
