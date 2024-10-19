package com.example.ologyprofbackenddemo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ologyprofbackenddemo.common.enums.OpExceptionEnum;
import com.example.ologyprofbackenddemo.common.exception.OpException;
import com.example.ologyprofbackenddemo.model.DO.ExeHistoryDO;
import com.example.ologyprofbackenddemo.model.DO.ExerciseDO;
import com.example.ologyprofbackenddemo.repository.impl.ExeHistoryRepository;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.example.ologyprofbackenddemo.model.VO.exercise.ExercisePage;
import com.example.ologyprofbackenddemo.repository.impl.ExerciseRepository;
import com.example.ologyprofbackenddemo.service.LibraryService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Resource
    ExerciseRepository exerciseRepository;

    @Resource
    ExeHistoryRepository exeHistoryRepository;

    @Override
    public ExercisePage pageByGroup(String group,Integer pageNum,Integer pageSize){
        Page<ExerciseDO> page = new Page<>(pageNum, pageSize);
        page.addOrder(new OrderItem("exeId", true));
        LambdaQueryWrapper<ExerciseDO> queryWrapper = new LambdaQueryWrapper<>();
        // 筛选题目类型
        if (group != null) {
            queryWrapper.eq(ExerciseDO::getExeGroup,group);
        }else{
            throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);
        }
        page = exerciseRepository.page(page, queryWrapper);
        return new ExercisePage(page.getRecords(), page.getTotal(), page.getPages());
    }

    @Override
    public ExercisePage pageByUser(String userId,Integer pageNum,Integer pageSize){

        // 筛选用户
        LambdaQueryWrapper<ExeHistoryDO> queryWrapper1 = new LambdaQueryWrapper<>();
        if (userId != null) {
            queryWrapper1.eq(ExeHistoryDO::getUserId, userId);
        }else{
            throw new OpException(OpExceptionEnum.USER_ID_EMPTY);
        }
        // 获取exeId列表
        List<ExeHistoryDO> exeHistoryList = exeHistoryRepository.list(queryWrapper1);
        Collection<Long> exeIds = exeHistoryList.stream()
                .map(ExeHistoryDO::getExeId)
                .collect(Collectors.toList());

        if (exeIds.isEmpty()) {
            // 如果没有exeId，直接返回空的分页结果
            Page<ExerciseDO> emptyPage = new Page<>(pageNum, pageSize);
            return new ExercisePage(emptyPage.getRecords(),emptyPage.getTotal(),emptyPage.getPages() );
        }

        // 在exerciseRepository对应的exercise数据库里查询上面exeId对应的记录
        LambdaQueryWrapper<ExerciseDO> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.in(ExerciseDO::getExeId, exeIds);

        // 将这些记录放到page里
        Page<ExerciseDO> page = new Page<>(pageNum, pageSize);
        page.addOrder(new OrderItem("exeId", true));

        page = exerciseRepository.page(page, queryWrapper2);

        return new ExercisePage(page.getRecords(), page.getTotal(), page.getPages());
    }
}
