package cn.edu.ecnu.fiveguy.backendofaiprof.service.impl;

import cn.edu.ecnu.fiveguy.backendofaiprof.model.DO.HistoryDO;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HistoryServiceImpl implements cn.edu.ecnu.fiveguy.backendofaiprof.service.HistoryService {
    @Resource
    private ChatRepository chatRepository;

    @Resource
    private HistoryRepository historyRepository;

    void buildHistory() {

    }
    List<HistoryDO> getHistory() {

    }
}
