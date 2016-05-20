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
import com.dagong.service.EvaluationService;
import com.dagong.service.SearchService;
import com.dagong.user.UserClient;
import com.dagong.user.vo.UserVO;
import com.dagong.util.IdGenerator;
import com.github.pagehelper.PageHelper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by liuchang on 16/5/18.
 */
@Service(version = "1.0.0")
public class EvaluationClientImpl implements EvaluationClient {


    @Resource
    private EvaluationService evaluationService;

    public boolean evaluateCompany(String userId, String companyId, int rank, String comment) {

        return evaluationService.evaluateCompany(userId, companyId, rank, comment);
    }

    public boolean evaluateUser(String companyUser, String companyId, String userId, int rank, String comment) {
        return evaluationService.evaluateUser(companyUser, companyId, userId, rank, comment);
    }


    public boolean evaluateJob(String userId, String jobId, int rank, String comment) {
        return evaluationService.evaluateJob(userId, jobId, rank, comment);
    }

    @Override
    public VOList<CompanyEvaluationVO> getCompanyEvaluationForUser(String userId, int page, int pageSize) {
        return evaluationService.getCompanyEvaluationForUser(userId, page, pageSize);
    }

    @Override
    public VOList<JobEvaluationVO> getJobEvaluationForUser(String userId, int page, int pageSize) {
        return evaluationService.getJobEvaluationForUser(userId, page, pageSize);

    }

    @Override
    public VOList<UserEvaluationVO> getUserEvaluationForCompanyUser(String companyId, int page, int pageSize) {
        return evaluationService.getUserEvaluationForCompany(companyId,page,pageSize);
    }

    @Override
    public List<Map> getCompanyEvaluation(String companyId, int page, int pageSize) {
        return evaluationService.getCompanyEvaluation(companyId, page, pageSize);
    }

    @Override
    public List<Map> getUserEvaluation(String userId, int page, int pageSize) {
        return evaluationService.getUserEvaluation(userId, page, pageSize);
    }

    @Override
    public List<Map> getJobEvaluation(String jobId, int page, int pageSize) {
        return evaluationService.getJobEvaluation(jobId, page, pageSize);
    }
}
