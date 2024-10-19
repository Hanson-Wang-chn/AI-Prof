package com.example.ologyprofbackenddemo.repository.impl;

import com.example.ologyprofbackenddemo.mapper.HistoryGroupMapper;
import com.example.ologyprofbackenddemo.model.DO.HistoryGroupDO;
import com.example.ologyprofbackenddemo.repository.IHistoryGroupRepo;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service
public class HistoryGroupRepository extends ServiceImpl<HistoryGroupMapper, HistoryGroupDO> implements IHistoryGroupRepo {
}
