package com.dagong.service;

import com.alibaba.fastjson.JSON;
import com.dagong.pojo.Evaluation;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liuchang on 16/4/5.
 */
@Service
public class SearchService {

    private TransportClient transportClient = null;
    private static final int PAGE_SIZE = 10;
    private final String INDEX = "evaluation";
    private final String JOB_EVALUATION_TYPE = "job";
    private final String USER_EVALUATION_TYPE = "user";
    private final String COMPANY_EVALUATION_TYPE = "company";

    @Value("${searchEngine.address}")
    private String searchAddress = null;

    @Value("${searchEngine.port}")
    private int searchPort = 0;


    @PostConstruct
    public void init() {
        try {
            transportClient = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(searchAddress), searchPort));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }





    public List<Map> searchJobEvaluation(String jobId, int page,int pageSize) {
        return search(JOB_EVALUATION_TYPE, "jobId", jobId, page,pageSize);
    }

    public List<Map> searchUserEvaluation(String userId, int page,int pageSize) {
        return search(USER_EVALUATION_TYPE, "userId", userId, page,pageSize);
    }

    public List<Map> searchCompanyEvaluation(String companyId, int page,int pageSize) {
        return search(COMPANY_EVALUATION_TYPE, "companyId", companyId, page,pageSize);
    }

    public Map getJobEvaluation(String id) {
        return searchEvaluation(JOB_EVALUATION_TYPE, id);
    }

    public Map getCompnayEvaluation(String id) {
        return searchEvaluation(COMPANY_EVALUATION_TYPE, id);
    }

    public Map getUserEvaluation(String id) {
        return searchEvaluation(USER_EVALUATION_TYPE, id);
    }

    private Map searchEvaluation(String type, String id) {
        GetResponse response = transportClient.prepareGet(INDEX, type, id).execute().actionGet();
        if (response.isExists()) {
            return response.getSource();
        }
        return null;
    }


    public boolean createEvaluation(List<Evaluation> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();
        list.forEach(evaluation -> {

            bulkRequestBuilder.add(transportClient.prepareIndex(INDEX, evaluation.getType(), evaluation.getId()).setSource(JSON.toJSONString(evaluation)));
        });
        BulkResponse bulkItemResponses = bulkRequestBuilder.execute().actionGet();
        return true;
    }


    public boolean updateEvaluation(List<Evaluation> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();
        list.forEach(evaluation -> {
            bulkRequestBuilder.add(transportClient.prepareUpdate(INDEX, evaluation.getType(), evaluation.getId()).setDoc(JSON.toJSONString(evaluation)));
        });
        BulkResponse bulkItemResponses = bulkRequestBuilder.execute().actionGet();

        return true;
    }


    private List<Map> search(String type, String key, String value, int page,int pageSize) {
        if(pageSize>PAGE_SIZE||pageSize<0){
            pageSize = PAGE_SIZE;
        }
        List<Map> evaluations = new ArrayList<>();

        SearchResponse searchResponse = transportClient.prepareSearch(INDEX)
                .setTypes(type)
                .setQuery(QueryBuilders.matchQuery(key, value))
                .setFrom(page)
                .setSize(pageSize)
                .execute()
                .actionGet();
        for (SearchHit searchHitFields : searchResponse.getHits().getHits()) {
            evaluations.add(searchHitFields.getSource());
        }
        return evaluations;
    }


//
//
//    public Map getJob(String jobId){
//        GetResponse response = transportClient.prepareGet("test", "job", jobId).execute().actionGet();
//        if(response.isExists()){
//            return response.getSource();
//        }
//        return null;
//    }
//
//    public boolean addUserFollowJobToIndex(){
//        return true;
//    }
//
//    public boolean addJobToIndex(List<Job> jobList){
//        if(jobList==null||jobList.isEmpty()){
//            return false;
//        }
//        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();
//        jobList.forEach(job -> {
//
//            bulkRequestBuilder.add(transportClient.prepareIndex("test", "job", job.getId()).setSource(JSON.toJSONString(job)));
//        });
//        BulkResponse bulkItemResponses = bulkRequestBuilder.execute().actionGet();
//
//        return true;
//    }
//
//    public boolean updateJobInIndex(List<Job> jobList){
//        if(jobList==null||jobList.isEmpty()){
//            return false;
//        }
//        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();
//        jobList.forEach(job -> {
//            bulkRequestBuilder.add(transportClient.prepareUpdate("test", "job", job.getId()).setDoc(JSON.toJSONString(job)));
//        });
//        BulkResponse bulkItemResponses = bulkRequestBuilder.execute().actionGet();
//
//        return true;
//    }


}
