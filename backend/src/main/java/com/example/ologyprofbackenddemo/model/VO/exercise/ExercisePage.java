package com.example.ologyprofbackenddemo.model.VO.exercise;

import com.example.ologyprofbackenddemo.model.DO.ExerciseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExercisePage {
    private List<ExerciseDO> exerciseList;
    private Long total;
    private Long page;
}
