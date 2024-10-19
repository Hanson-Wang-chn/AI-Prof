package com.example.ologyprofbackenddemo.service;

import java.time.LocalDate;
import java.util.Map;

public interface DataAnalysisService {
    Long TotalExeNum(String userId);
    Map<LocalDate, Long> DailyExeNum(String userId);
}
