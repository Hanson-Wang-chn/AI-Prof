package com.example.ologyprofbackenddemo.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ologyprofbackenddemo.mapper.HistoryMapper;
import com.example.ologyprofbackenddemo.model.DO.HistoryDO;
import com.example.ologyprofbackenddemo.repository.IHistoryRepo;
import org.springframework.stereotype.Service;

@Service
public class HistoryRepository extends ServiceImpl<HistoryMapper, HistoryDO> implements IHistoryRepo {
}
