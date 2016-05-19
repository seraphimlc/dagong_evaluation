package com.dagong.evaluation;

import com.dagong.evaluation.vo.CompanyEvaluationVO;
import com.dagong.evaluation.vo.JobEvaluationVO;
import com.dagong.evaluation.vo.UserEvaluationVO;
import com.dagong.evaluation.vo.VOList;

/**
 * Created by liuchang on 16/5/18.
 */
public interface EvaluationClient {


    public boolean evaluateCompany(String userId, String companyId, int rank, String comment);

    public boolean evaluateUser(String companyUser, String companyId, String userId, int rank, String comment);

    public boolean evaluateJob(String userId, String jobId, int rank, String comment);


    public VOList<CompanyEvaluationVO> getCompanyEvaluationForUser(String userId, int page, int pageSize);

    public VOList<JobEvaluationVO> getJobEvaluationForUser(String userId, int page, int pageSize);

    public VOList<UserEvaluationVO> getUserEvaluationForCompanyUser(String companyId, int page, int pageSize);


    public VOList<CompanyEvaluationVO> getCompanyEvaluation(String companyId, int page, int pageSize);

    public VOList<UserEvaluationVO> getUserEvaluation(String userId, int page, int pageSize);

    public VOList<JobEvaluationVO> getJobEvaluation(String jobId, int page, int pageSize);

}
