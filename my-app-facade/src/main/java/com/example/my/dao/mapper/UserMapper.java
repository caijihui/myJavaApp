package com.example.my.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.my.dao.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where id = #{id}")
    User abc(@Param("id") Long id);
}
