package com.example.ologyprofbackenddemo.controller;

import com.example.ologyprofbackenddemo.common.base.BaseResponse;
import com.example.ologyprofbackenddemo.common.exception.OpException;
import com.example.ologyprofbackenddemo.service.DataAnalysisService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/study/{userId}")
public class DataAnalysisController {

    @Resource
    private DataAnalysisService dataAnalysisService;

    @GetMapping("/total_num")
    public BaseResponse<Long> TotalExeNum(@PathVariable String userId) {
        try {
            return BaseResponse.buildSuccess(dataAnalysisService.TotalExeNum(userId));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping("/daily_num")
    public BaseResponse<Map<LocalDate,Long>> DailyExeNum(@PathVariable String userId) {
        try {
            return BaseResponse.buildSuccess(dataAnalysisService.DailyExeNum(userId));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }
}
