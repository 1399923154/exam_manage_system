package com.exam_manage_system.mapper;

import com.exam_manage_system.entity.Department_dictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Department_dictionaryMapper {

    @Select("select * from department_dictionary where dictionary_code like '"
            + "${dictionary_code}" + "__'")
        //用二位__下划线来匹配 取代% 表示两位数？
    List<Department_dictionary> getFieldList(@Param("dictionary_code") String dictionary_code);
}
