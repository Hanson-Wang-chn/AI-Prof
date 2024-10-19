package com.example.ologyprofbackenddemo.service;

import com.example.ologyprofbackenddemo.model.VO.exercise.ExercisePage;

public interface LibraryService {
 ExercisePage pageByGroup(String group, Integer pageNum, Integer pageSize);
 ExercisePage pageByUser(String studentId, Integer pageNum, Integer pageSize);
}
