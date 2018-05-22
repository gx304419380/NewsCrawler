package com.fly.blog.service;

import com.fly.blog.entity.User;
import com.fly.blog.entity.UserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author XXX
 * @since 2018-04-11
 */
public interface UserService {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
