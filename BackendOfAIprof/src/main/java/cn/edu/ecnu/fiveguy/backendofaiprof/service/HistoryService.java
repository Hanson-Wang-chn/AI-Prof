package cn.edu.ecnu.fiveguy.backendofaiprof.service;

import cn.edu.ecnu.fiveguy.backendofaiprof.model.DO.HistoryDO;

import java.util.List;

public interface HistoryService {
    void buildHistory();
    List<HistoryDO> getHistory();
}
