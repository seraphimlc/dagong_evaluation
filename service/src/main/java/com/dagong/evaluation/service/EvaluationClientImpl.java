package com.dagong.evaluation.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.rocketmq.common.SystemClock;
import com.dagong.evaluation.EvaluationClient;
import com.dagong.evaluation.vo.CompanyEvaluationVO;
import com.dagong.evaluation.vo.JobEvaluationVO;
import com.dagong.evaluation.vo.UserEvaluationVO;
import com.dagong.evaluation.vo.VOList;
import com.dagong.job.JobClient;
import com.dagong.job.vo.JobVO;
import com.dagong.mapper.CompanyEvaluationMapper;
import com.dagong.mapper.JobEvaluationMapper;
import com.dagong.mapper.UserEvaluationMapper;
import com.dagong.pojo.CompanyEvaluation;
import com.dagong.pojo.Job;
import com.dagong.pojo.JobEvaluation;
import com.dagong.pojo.UserEvaluation;
import com.dagong.user.UserClient;
import com.dagong.user.vo.UserVO;
import com.dagong.util.IdGenerator;
import com.github.pagehelper.PageHelper;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuchang on 16/5/18.
 */
@Service(version = "1.0.0")
public class EvaluationClientImpl implements EvaluationClient {

    @Resource
    private JobEvaluationMapper jobEvaluationMapper;

    @Resource
    private UserEvaluationMapper userEvaluationMapper;

    @Resource
    private CompanyEvaluationMapper companyEvaluationMapper;

    @Reference(version = "1.0.0")
    private UserClient userClient;

    @Reference(version = "1.0.0")
    private JobClient jobClient;

    @Resource
    private IdGenerator idGenerator;

    @Override
    public boolean evaluateCompany(String userId, String companyId, int rank, String comment) {

        CompanyEvaluation companyEvaluation = new CompanyEvaluation();
        companyEvaluation.setUserId(userId);
        companyEvaluation.setCompanyId(companyId);
        List<CompanyEvaluation> list = companyEvaluationMapper.getList(companyEvaluation);
        if (list != null && !list.isEmpty()) {
            return false;
        }
        companyEvaluation.setComment(comment);
        companyEvaluation.setRank(rank);
        companyEvaluation.setCreateTime(System.currentTimeMillis());
        companyEvaluation.setId(idGenerator.generate(CompanyEvaluation.class.getSimpleName()));
        companyEvaluation.setStatus(1);
        return true;
    }

    @Override
    public boolean evaluateUser(String companyUser, String companyId, String userId, int rank, String comment) {
        UserEvaluation userEvaluation = new UserEvaluation();
        userEvaluation.setCompanyUser(companyUser);
        userEvaluation.setCompanyId(companyId);
        userEvaluation.setUserId(userId);
        List<UserEvaluation> list = userEvaluationMapper.getList(userEvaluation);
        if (list != null && !list.isEmpty()) {
            return false;
        }
        UserVO userVO = userClient.getUserByUserId(userId);
        if (userVO == null) {
            return false;
        }
        userEvaluation.setId(idGenerator.generate(UserEvaluation.class.getSimpleName()));
        userEvaluation.setCreateTime(System.currentTimeMillis());
        userEvaluation.setRank(rank);
        userEvaluation.setStatus(1);
        userEvaluation.setComment(comment);
        userEvaluation.setInfo(userVO.toString());
        userEvaluationMapper.insertSelective(userEvaluation);
        return true;
    }

    @Override
    public boolean evaluateJob(String userId, String jobId, int rank, String comment) {

        JobEvaluation jobEvaluation = new JobEvaluation();
        jobEvaluation.setUserId(userId);
        jobEvaluation.setJobId(jobId);
        List<JobEvaluation> list = jobEvaluationMapper.getList(jobEvaluation);
        if(list!=null&&!list.isEmpty())
        {
            return false;
        }
        JobVO job = jobClient.getJobByJobId(jobId);
        if(job==null){
            return false;
        }
        jobEvaluation.setId(idGenerator.generate(JobEvaluation.class.getSimpleName()));
        jobEvaluation.setComment(comment);
        jobEvaluation.setStatus(1);
        jobEvaluation.setCreateTime(System.currentTimeMillis());
        jobEvaluation.setRank(rank);
        jobEvaluationMapper.insertSelective(jobEvaluation);
        return true;

    }

    @Override
    public VOList<CompanyEvaluationVO> getCompanyEvaluationForUser(String userId, int page, int pageSize) {
      if(pageSize>20||pageSize<=0){
          pageSize=20;
      }


        return null;
    }

    @Override
    public VOList<JobEvaluationVO> getJobEvaluationForUser(String userId, int page, int pageSize) {
        return null;
    }

    @Override
    public VOList<UserEvaluationVO> getUserEvaluationForCompanyUser(String companyId, int page, int pageSize) {
        return null;
    }

    @Override
    public VOList<CompanyEvaluationVO> getCompanyEvaluation(String companyId, int page, int pageSize) {
        return null;
    }

    @Override
    public VOList<UserEvaluationVO> getUserEvaluation(String userId, int page, int pageSize) {
        return null;
    }

    @Override
    public VOList<JobEvaluationVO> getJobEvaluation(String jobId, int page, int pageSize) {
        return null;
    }
}
