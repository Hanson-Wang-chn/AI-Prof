package com.example.ologyprofbackenddemo.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ologyprofbackenddemo.mapper.ExerciseMapper;
import com.example.ologyprofbackenddemo.model.DO.ExerciseDO;
import com.example.ologyprofbackenddemo.repository.IExerciseRepo;

import org.springframework.stereotype.Service;

@Service
public class ExerciseRepository extends ServiceImpl<ExerciseMapper, ExerciseDO> implements IExerciseRepo {
}
