package com.dagong.mapper;

import com.dagong.pojo.JobEvaluation;

public interface JobEvaluationMapper {
    int deleteByPrimaryKey(String id);

    int insert(JobEvaluation record);

    int insertSelective(JobEvaluation record);

    JobEvaluation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JobEvaluation record);

    int updateByPrimaryKey(JobEvaluation record);
}