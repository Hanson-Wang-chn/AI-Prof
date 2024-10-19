package com.example.ologyprofbackenddemo.service.impl;

import com.example.ologyprofbackenddemo.common.enums.OpExceptionEnum;
import com.example.ologyprofbackenddemo.common.exception.OpException;
import com.example.ologyprofbackenddemo.model.DO.HistoryGroupDO;
import com.example.ologyprofbackenddemo.model.VO.HistoryGroupVO;
import com.example.ologyprofbackenddemo.repository.impl.HistoryGroupRepository;
import com.example.ologyprofbackenddemo.repository.impl.HistoryRepository;
import com.example.ologyprofbackenddemo.service.HistoryGroupService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistoryGroupServiceImpl implements HistoryGroupService {
    @Resource
    private HistoryGroupRepository historyGroupRepository;

    @Resource
    private HistoryRepository historyRepository;

    @Override
    @Transactional
    public HistoryGroupVO buildGroup(String userId){
        //异常处理
        if(userId == null) throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);

        //生成groupId
        LambdaQueryWrapper<HistoryGroupDO> queryWrapper = new LambdaQueryWrapper<>();
        //获得该用户的HistoryGroup记录，且按照升序排列
        queryWrapper.eq(HistoryGroupDO::getUserId, userId);
        queryWrapper.orderByDesc(HistoryGroupDO::getGroupId);
        queryWrapper.last("LIMIT 1"); // 限制结果集为1条记录，以提高查询效率
        // 执行查询并获取结果
        Optional<HistoryGroupDO> optionalHistoryGroup = Optional.ofNullable(historyGroupRepository.getOne(queryWrapper));
        //构造新的groupId
        int newGroupId = optionalHistoryGroup.map(HistoryGroupDO::getGroupId)
                .map(groupId -> groupId + 1)
                .orElse(1); // 如果没有找到任何记录，则默认newGroupId为1

        //生成新的HistoryGroup记录，并插入
        HistoryGroupDO historyGroupDO = HistoryGroupDO.builder()
                .groupName("新建对话")
                .userId(userId)
                .groupId(newGroupId)
                .build();
        historyGroupRepository.save(historyGroupDO);

        //生成HistoryVO，并返回
        return new HistoryGroupVO(historyGroupDO);
    }

    @Override
    public List<HistoryGroupVO> getAllGroups(String userId){
        //异常处理
        if(userId == null) throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);

        //查询所有的HistoryGroup记录
        LambdaQueryWrapper<HistoryGroupDO> queryWrapper = new LambdaQueryWrapper<>();
        //获得该用户的HistoryGroup记录，且按照降序排列
        queryWrapper.eq(HistoryGroupDO::getUserId, userId);
        queryWrapper.orderByDesc(HistoryGroupDO::getGroupId);

        // 执行查询并获取结果列表
        List<HistoryGroupDO> historyGroupDOList = historyGroupRepository.list(queryWrapper);

        // 创建一个空的List来存储HistoryGroupVO对象
        List<HistoryGroupVO> historyGroupVOList;

        // 将HistoryGroupDO列表转换为HistoryGroupVO列表
        historyGroupVOList = historyGroupDOList.stream()
                .map(HistoryGroupVO::new)
                .collect(Collectors.toList());

        return historyGroupVOList;
    }

    @Override
    @Transactional
    //前置：groupId不为空
    public Void deleteGroup(String userId,int groupId){
        //异常处理
        if(userId == null) throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);

        //查询所有的HistoryGroup记录
        LambdaQueryWrapper<HistoryGroupDO> queryWrapper = new LambdaQueryWrapper<>();
        //查询该用户该组号是否存在
        queryWrapper.eq(HistoryGroupDO::getUserId, userId);
        queryWrapper.eq(HistoryGroupDO::getGroupId,groupId);

        // 执行查询，检查是否存在符合条件的记录
        HistoryGroupDO historyGroupDO = historyGroupRepository.getOne(queryWrapper);
        if (historyGroupDO == null) throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);

        // 如果存在，则执行删除操作
        // 首先删除对应的 HistoryDO 记录
        historyRepository.removeById(groupId);

        // 然后删除 HistoryGroupDO 记录
        historyGroupRepository.removeById(groupId);

        return null;
    }
}
