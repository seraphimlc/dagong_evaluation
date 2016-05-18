package com.dagong.mapper;

import com.dagong.pojo.UserEvaluation;

public interface UserEvaluationMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserEvaluation record);

    int insertSelective(UserEvaluation record);

    UserEvaluation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserEvaluation record);

    int updateByPrimaryKey(UserEvaluation record);
}