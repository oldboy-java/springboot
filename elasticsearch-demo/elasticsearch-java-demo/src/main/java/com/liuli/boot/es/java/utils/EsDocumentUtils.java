package com.liuli.boot.es.java.utils;

import com.alibaba.fastjson.JSON;
import com.liuli.boot.es.java.model.Sort;
import com.liuli.boot.es.java.model.es.BaseEsModel;
import com.liuli.boot.es.java.model.page.PageResult;
import com.liuli.boot.es.java.model.page.Pager;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
public class EsDocumentUtils {
    private EsDocumentUtils() {
    }


    /**
     * create doc
     *
     * @param client
     * @param indexName
     * @param model
     */
    public static void indexDoc(RestHighLevelClient client, String indexName, BaseEsModel model) {
        try {
            IndexRequest indexRequest = new IndexRequest(indexName);
            indexRequest.id(model.getId());
            indexRequest.source(JSON.toJSONString(model), XContentType.JSON);
            // 如果文档不存在，则新增文档 （新增）；否则先删除文档，后新增文档 （全局修改）
            IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
            log.info("index document result:{}", index.getResult());
        } catch (Exception ex) {
            throw new RuntimeException("index document fail,cause=" + ex.getMessage());
        }
    }

    /**
     * 局部更新文档
     *
     * @param client
     * @param indexName
     * @param model
     */
    public static void updateDoc(RestHighLevelClient client, String indexName, BaseEsModel model) {
        try {
            UpdateRequest updateRequest = new UpdateRequest(indexName, model.getId());
            updateRequest.doc(JSON.toJSONString(model), XContentType.JSON);
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            log.info("update document result:{}", updateResponse.getResult());
        } catch (Exception ex) {
            throw new RuntimeException("update document fail,cause=" + ex.getMessage());
        }
    }

    /**
     * 查找单个文档
     *
     * @param client
     * @param indexName
     * @param id
     * @param  cls
     */
    public static  <T> T getDoc(RestHighLevelClient client, String indexName, String id, Class<T> cls) {
        try {
            GetRequest getRequest = new GetRequest(indexName, id);
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            Map<String, Object> source = getResponse.getSource();
           return JSON.parseObject(JSON.toJSONString(source),cls);
        } catch (Exception ex) {
            throw new RuntimeException("update document fail,cause=" + ex.getMessage());
        }
    }


    /**
     * 删除单个文档
     *
     * @param client
     * @param indexName
     * @param id
     */
    public static void deleteDoc(RestHighLevelClient client, String indexName, String id) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(indexName, id);
            DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("delete document result:{}", deleteResponse.getResult());
        } catch (Exception ex) {
            throw new RuntimeException("delete document fail,cause=" + ex.getMessage());
        }
    }


    /**
     * 批量插入文档
     *
     * @param client
     * @param indexName
     * @param models
     */
    public static void batchInsertDocs(RestHighLevelClient client, String indexName, List<BaseEsModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return;
        }
        try {
            BulkRequest bulkRequest = new BulkRequest();
            for (BaseEsModel model : models) {
                IndexRequest indexRequest = new IndexRequest(indexName);
                indexRequest.id(model.getId());
                indexRequest.source(JSON.toJSONString(model), XContentType.JSON);
                bulkRequest.add(indexRequest);
            }
            BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("batch insert docs result:{}", JSON.toJSONString(bulk));
        } catch (Exception ex) {
            throw new RuntimeException("batch insert docs fail,cause=" + ex.getMessage());
        }
    }

    /**
     * 批量插入文档
     *
     * @param client
     * @param indexName
     * @param models
     */
    public static void batchInsertDocs(RestHighLevelClient client, String indexName, List<BaseEsModel> models, TimeValue timeout) {
        try {
            BulkRequest bulkRequest = new BulkRequest();
            //设置超时时间，默认是1分钟:1m
            bulkRequest.timeout(timeout);
            for (BaseEsModel model : models) {
                IndexRequest indexRequest = new IndexRequest(indexName);
                indexRequest.id(model.getId());
                indexRequest.source(JSON.toJSONString(model), XContentType.JSON);
                bulkRequest.add(indexRequest);
            }
            BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("batch insert docs result:{}", JSON.toJSONString(bulk));
        } catch (Exception ex) {
            throw new RuntimeException("batch insert docs fail,cause=" + ex.getMessage());
        }
    }


    /**
     * 批量删除文档
     *
     * @param client
     * @param indexName
     * @param docIds
     */
    public static void batchDeleteDocs(RestHighLevelClient client, String indexName, List<String> docIds) {
        try {
            BulkRequest bulkRequest = new BulkRequest();
            for (String docId : docIds) {
                DeleteRequest deleteRequest = new DeleteRequest(indexName);
                deleteRequest.id(docId);
                bulkRequest.add(deleteRequest);
            }
            BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("batch delete docs result:{}", JSON.toJSONString(bulk));
        } catch (Exception ex) {
            throw new RuntimeException("batch delete docs fail,cause=" + ex.getMessage());
        }
    }

    /**
     * 查询所有文档:matchAllQuery
     *
     * @param client
     * @param indexName
     * @param cls
     */
    public static <T> List<T> listAll(RestHighLevelClient client, String indexName, Class<T> cls) {
        try {
            List<T> result = new ArrayList<>();
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(indexName);
            //指定数据的查询方式
            searchRequest.source(SearchSourceBuilder.searchSource().query(QueryBuilders.matchAllQuery()));
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            log.info("总记录数：{}", hits.getTotalHits());
            log.info("总耗时：{}", response.getTook());
            for (SearchHit hit : hits) {
                T t = JSON.parseObject(hit.getSourceAsString(), cls);
                result.add(t);
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("list all docs fail,cause=" + ex.getMessage());
        }
    }


    /**
     * 分页查询所有文档
     *
     @param client 客户端
      * @param indexName 索引名
     * @param cls  查询对象类型
     * @param  pager 分页信息
     */
    public static <T> PageResult<T> listAllByPage(RestHighLevelClient client, String indexName, Class<T> cls, Pager pager) {
        return listAllByPage(client, indexName, cls, pager, null);
    }

    /**
     * 分页查询所有文档  + 排序
     *
     @param client 客户端
      * @param indexName 索引名
     * @param cls  查询对象类型
     * @param  pager 分页信息
     * @param sorts 排序信息
     */
    public static <T> PageResult<T> listAllByPage(RestHighLevelClient client, String indexName, Class<T> cls, Pager pager, List<Sort> sorts) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MatchAllQueryBuilder builder = QueryBuilders.matchAllQuery();
        boolQueryBuilder.filter(builder);
        return listAllByPage(client, indexName, cls, pager, sorts, boolQueryBuilder, Collections.emptyList());
    }



    /**
     * 分页按条件查询 + 排序
     *
     @param client 客户端
      * @param indexName 索引名
     * @param cls  查询对象类型
     * @param  pager 分页信息
     * @param sorts 排序信息
     * @param queryBuilder 查询构造器
     * @param highlightFields 高亮字段
     */
    public static <T> PageResult<T> listAllByPage(RestHighLevelClient client, String indexName, Class<T> cls, Pager pager,
                                                  List<Sort> sorts, BoolQueryBuilder queryBuilder, List<String> highlightFields) {
        try {
            List<T> result = new ArrayList<>();
            SearchRequest searchRequest = new SearchRequest();
            //指定查询索引名
            searchRequest.indices(indexName);

            //查询数据构造器
            SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();
            //设置分页
            if (Objects.nonNull(pager.getPageIndex()) && Objects.nonNull(pager.getPageSize())) {
                searchSourceBuilder.from((pager.getPageIndex()-1) * pager.getPageSize());
                searchSourceBuilder.size(pager.getPageSize());
            }

            //设置排序:排序字段必须时keyword类型
            if (!CollectionUtils.isEmpty(sorts)) {
                sorts.forEach(sort-> searchSourceBuilder.sort(sort.getField(), sort.getSortOrder()));
            }
            //指定查询条件
            searchSourceBuilder.query(queryBuilder);

            //指定高亮
            if (!CollectionUtils.isEmpty(highlightFields)) {
                HighlightBuilder highlightBuilder = new HighlightBuilder();
                highlightFields.forEach(field -> highlightBuilder.field(field));
                highlightBuilder.preTags("<span style='color:red'>");
                highlightBuilder.postTags("</span>");
                searchSourceBuilder.highlighter(highlightBuilder);
            }

            //指定数据的查询方式
            searchRequest.source(searchSourceBuilder);
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            log.info("总记录数：{}", hits.getTotalHits());
            log.info("总耗时：{}", response.getTook());
            for (SearchHit hit : hits) {
                Map<String, HighlightField> highlightFieldMap = hit.getHighlightFields();
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                highlightFieldMap.forEach((k,v) -> {
                    sourceAsMap.put(k,  getHighlightValue(highlightFieldMap.get(k)));
                });
                T t = JSON.parseObject(JSON.toJSONString(sourceAsMap), cls);
                result.add(t);
            }
            return new PageResult<T>(hits.getTotalHits().value, pager.getPageSize(), pager.getPageIndex(), result);
        } catch (Exception ex) {
            throw new RuntimeException("list all docs fail,cause=" + ex.getMessage());
        }
    }

    private static String getHighlightValue(HighlightField highlightField) {
        StringJoiner stringJoiner = new StringJoiner("");
        Text[] fragments = highlightField.fragments();
        for (Text text : fragments) {
            stringJoiner.add(text.string());
        }
        return stringJoiner.toString();
    }

}
