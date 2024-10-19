package com.example.ologyprofbackenddemo.repository.impl;

import com.example.ologyprofbackenddemo.mapper.ExeHistoryMapper;
import com.example.ologyprofbackenddemo.model.DO.ExeHistoryDO;
import com.example.ologyprofbackenddemo.repository.IExeHistoryRepo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ExeHistoryRepository extends ServiceImpl<ExeHistoryMapper, ExeHistoryDO> implements IExeHistoryRepo {
}
