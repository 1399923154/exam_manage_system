package com.exam_manage_system.service;

import com.exam_manage_system.entity.Department_dictionary;
import com.exam_manage_system.mapper.Department_dictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_department_dictionary {
    @Autowired
    private Department_dictionaryMapper department_dictionaryMapper;

    public List<Department_dictionary> getFieldList(String dictionary_code) {
        return  department_dictionaryMapper.getFieldList(dictionary_code);
    }
}
