package com.liuli.service.impl;

import com.liuli.pojo.Novel;
import com.liuli.service.NovelService;
import com.liuli.utils.elasticsearch.Construct;
import com.liuli.utils.elasticsearch.EsUtis;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018-9-1.
 */
@Slf4j
@Service
public class NovelServiceImpl implements NovelService {
    @Autowired
    private TransportClient client;

    @Override
    public String createIndex(List<Construct> constructList,String indexName,String typeName) {
        try {
            return EsUtis.createIndex(client,indexName,typeName, constructList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String delIndex(String indexName) {
        return  EsUtis.delIndex(client,indexName);
    }

    @Override
    public String saveNovel(Novel novel) throws Exception {
        //构造Json
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .field("title",novel.getTitle())
                .field("author",novel.getAuthor())
                .field("pages",novel.getPages())
                .field("publish_date",novel.getPublishDate())
                .endObject();
        //执行新增操作
        IndexResponse response = client.prepareIndex("book","novel").setSource(builder).get();
        return response.getId();//返回文档Id
    }

    @Override
    public String delNovel(String docId) {
        DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete("book","novel",docId);
        DeleteResponse response = deleteRequestBuilder.get();
        return response.getResult().name();
    }

    @Override
    public String updateNovel(Novel novel) throws Exception{
        //构造Json
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
        if(novel.getAuthor()!=null) {
            builder.field("author",novel.getAuthor());
        }
        if(novel.getTitle()!=null) {
            builder.field("title",novel.getTitle());
        }
        if(novel.getPages()!=null && novel.getPages() > 0) {
            builder.field("pages",novel.getPages());
        }
        if(novel.getPublishDate()!=null) {
            builder.field("publish_date",novel.getPublishDate());
        }
        builder.endObject();

        //构造请求
        UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate("book","novel",novel.getId()).setDoc(builder);
        //执行并获取返回值
        UpdateResponse response = updateRequestBuilder.get();
        return response.getResult().name();
    }

    @Override
    public List<Novel> schNovel(Novel novel,Integer offset,Integer pageSize) {
        //构造booleanQueryBuilder
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        //包含当前标题
        if(novel.getTitle()!=null){
            boolQuery.must(QueryBuilders.matchQuery("title",novel.getTitle()));
        }
        //包含当前作者
        if(novel.getAuthor()!=null){
            boolQuery.must(QueryBuilders.matchQuery("author",novel.getAuthor()));
        }
        //等于当前页数
        if(novel.getPages()!=null){
            boolQuery.must(QueryBuilders.termQuery("pages",novel.getPages()));
        }

        //筛选大于当前日期
        if(novel.getPublishDate()!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //由于存储采用字符串格式yyyy-MM-dd，再使用gt,lt时需要格式化
            RangeQueryBuilder  rangeQuery = QueryBuilders.rangeQuery("publish_date").format("yyyy-MM-dd")
                    .gt(novel.getPublishDate())
                    .lt(sdf.format(new Date()));
            boolQuery.filter(rangeQuery);
        }
        //构造searchRequestBuilder
       SearchRequestBuilder searchRequestBuilder  =  client.prepareSearch("book")
                .setTypes("novel")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
               .setQuery(boolQuery)
                .setFrom(offset)
                .setSize(pageSize);
        log.info(String.valueOf(searchRequestBuilder));

        //执行获取结果
        SearchResponse response = searchRequestBuilder.get();

        List<Novel> result = new ArrayList<Novel>();
        SearchHits searchHits = response.getHits();

        log.info(searchHits.getTotalHits()+"");
        if(searchHits != null && searchHits.getTotalHits() > 0) {
            for(SearchHit hit:response.getHits()){
                Novel n = new Novel();
                n.setAuthor((String)hit.getSource().get("author"));
                n.setTitle((String)hit.getSource().get("title"));
                n.setId(hit.getId());
                n.setPages((Integer) hit.getSource().get("pages"));
                n.setPublishDate((String)hit.getSource().get("publish_date"));
                result.add(n);
            }
        }
        return result;
    }
}
