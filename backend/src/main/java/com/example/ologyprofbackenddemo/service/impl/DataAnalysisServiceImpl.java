package com.example.ologyprofbackenddemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ologyprofbackenddemo.common.enums.OpExceptionEnum;
import com.example.ologyprofbackenddemo.common.exception.OpException;
import com.example.ologyprofbackenddemo.model.DO.ExeHistoryDO;
import com.example.ologyprofbackenddemo.repository.impl.ExeHistoryRepository;
import com.example.ologyprofbackenddemo.service.DataAnalysisService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DataAnalysisServiceImpl implements DataAnalysisService {

    @Resource
    ExeHistoryRepository exeHistoryRepository;

    @Override
    public Long TotalExeNum(String userId){
        if(userId == null || userId.isEmpty()){
            throw new OpException(OpExceptionEnum.USER_ID_EMPTY);
        }
        //查询userId在ExeHistory中的记录ExeHistoryDO
        LambdaQueryWrapper<ExeHistoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExeHistoryDO::getUserId,userId);
        List<ExeHistoryDO> exeHistoryList = exeHistoryRepository.list(queryWrapper);

        // 使用 Java Stream API 统计不同 exeId 的数量
        Set<Long> distinctExeIds = exeHistoryList.stream()
                .map(ExeHistoryDO::getExeId)
                .collect(Collectors.toSet());

        // 返回 exeId 的种类数
        return (long) distinctExeIds.size();
    }

    @Override
    public Map<LocalDate,Long> DailyExeNum(String userId){

        if(userId == null){
            throw new OpException(OpExceptionEnum.USER_ID_EMPTY);
        }
        //查询userId在ExeHistory中的记录ExeHistoryDO
        LambdaQueryWrapper<ExeHistoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExeHistoryDO::getUserId,userId);
        List<ExeHistoryDO> exeHistoryList = exeHistoryRepository.list(queryWrapper);

        // 统计ExeHistoryDO中，不同时间exeTime对应的记录数
        //将不同时间和对应的记录数返回
        return exeHistoryList.stream()
                .collect(Collectors.groupingBy(
                        exeHistory -> exeHistory.getExeTime().toLocalDate(), // 提取日期部分
                        Collectors.counting() // 统计每个日期的记录数
                ));
    }
}
