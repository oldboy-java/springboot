package com.liuli.boot.es.java.utils;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class EsIndexUtils {
    private EsIndexUtils(){}


    /**
     * create index
     * @param client
     * @param indexName
     */
    public static boolean createIndex(RestHighLevelClient client, String indexName) {
       return createIndex(client, indexName, null, null, null);
    }

    /**
     * create index
     * @param client 客户端
     * @param indexName 索引名
     * @param properties 字段
     * @param aliases 别名
     * @param settings 配置
     */
    public static boolean createIndex(RestHighLevelClient client, String indexName, Map<String, Object> properties,  Map<String, ?> aliases,  Map<String, ?> settings) {
        boolean existIndex = isExistsIndex(client, indexName);
        if (existIndex) {
            log.info("the current index exists");
            return Boolean.TRUE;
        }else {
            // 构造创建索引请求，指定索引名称
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            if (!CollectionUtils.isEmpty(properties)) {
               Map<String, Object> mapping = new HashMap<>();
               mapping.put("properties", properties);
               request.mapping(mapping);
            }
           if (!CollectionUtils.isEmpty(aliases)) {
               request.aliases(aliases);
            }
            if (!CollectionUtils.isEmpty(settings)) {
                request.settings(settings);
            }
            // 指定创建索引请求和使用默认配置
            try {
                CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
                boolean acknowledged = createIndexResponse.isAcknowledged();
                log.info("create index successfully?:{}", acknowledged);
                return acknowledged;
            }catch (ElasticsearchStatusException ex){
                log.error("create index fail,cause={}", ex.getDetailedMessage());
            }catch (Exception ex) {
               log.error("create index fail,cause={}", ex.getMessage());
            }
            return Boolean.FALSE;
        }
    }

    /**
     * get index
     * @param client
     * @param indexName
     */
    public static GetIndexResponse getIndex(RestHighLevelClient client, String indexName) {
        GetIndexResponse getIndexResponse = null;
        // 构造获取索引请求，指定索引名称
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        // 指定GET索引请求和使用默认配置
        try {
            getIndexResponse = client.indices().get(getIndexRequest, RequestOptions.DEFAULT);
            log.info("mappings={}", getIndexResponse.getMappings());
            log.info("aliases={}", getIndexResponse.getAliases());
        }catch (ElasticsearchStatusException ex) {
            throw new RuntimeException("get index fail,cause=" + ex.getDetailedMessage());
        }catch (Exception ex) {
            throw new RuntimeException("get index fail,cause=" + ex.getMessage());
        }
        return getIndexResponse;
    }

    /**
     * 判断索引是否存在
     * @param indexName 索引名称
     * @return true 存在，false 不存在
     */
    public static boolean isExistsIndex(RestHighLevelClient client, String indexName) {
        try {
            // 构造获取索引请求，指定索引名称
            GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
            // 指定创建索引请求和使用默认配置
           return client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        }catch (ElasticsearchStatusException ex) {
           log.error("exists index fail,cause={}", ex.getDetailedMessage());
        }catch (Exception ex) {
            log.error("exists index fail,cause={}", ex.getMessage());
        }
        return Boolean.FALSE;
    }


    /**
     * delete index
     * @param client
     * @param indexName
     */
    public static boolean deleteIndex(RestHighLevelClient client, String indexName) {
        if (isExistsIndex(client, indexName)) {
            try {
                DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
                // 指定DELETE索引请求和使用默认配置
                AcknowledgedResponse acknowledgedResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
                boolean acknowledged = acknowledgedResponse.isAcknowledged();
                log.info("delete index successfully ?： {}", acknowledged);
                return acknowledged;
            } catch (ElasticsearchStatusException ex) {
                log.error("delete index fail,cause={}", ex.getDetailedMessage());
            } catch (Exception ex) {
                log.error("delete index fail,cause={}",  ex.getMessage());
            }
            return Boolean.FALSE;
        }else {
            log.warn("the index[{}} not exist, delete index fail", indexName);
            return Boolean.TRUE;
        }
    }
}
