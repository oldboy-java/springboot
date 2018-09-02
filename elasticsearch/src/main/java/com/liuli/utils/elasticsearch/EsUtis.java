package com.liuli.utils.elasticsearch;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.util.List;

/**
 * Created by Administrator on 2018-9-2.
 */
public class EsUtis {

    /**
     *  建立索引并设置mapping
     * @param client 客户端
     * @param indexName 索引名称
     * @param constructList 索引结构集合
     * @return
     */
    public static  String createIndex(TransportClient client,String indexName,String typeName, List<Construct> constructList) throws  Exception{
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .startObject("properties");

        if(constructList != null) {
            for(Construct construct :constructList){
                //设置字段名和字段类型
                builder.startObject(construct.getField()).field("type",construct.getType());
                //设置索引分词器
                if(construct.getAnalyzer()!=null){
                    builder.field("analyzer",construct.getAnalyzer());
                }
                //设置检索分词器
                if(construct.getSearchAnalyzer()!=null){
                    builder.field("search_analyzer",construct.getSearchAnalyzer());
                }
                if(construct.getFormat()!=null) {
                    builder.field("format",construct.getFormat());
                }
                builder.endObject();
            };
        }
        builder.endObject();
        builder.endObject();
        //构造MapperingRequest
        PutMappingRequest putmap = Requests.putMappingRequest(indexName).type(typeName).source(builder);
        //创建索引
        client.admin().indices().prepareCreate(indexName).execute().actionGet();
        //为索引添加映射
        PutMappingResponse response =  client.admin().indices().putMapping(putmap).actionGet();
        return response.toString();
    }

    /***
     * 删除索引
     * @param client
     * @param indexName 索引名称
     * @return
     */
    public static  String  delIndex(TransportClient client,String indexName){
        DeleteIndexResponse response = client.admin().indices().prepareDelete(indexName).execute().actionGet();
        return response.toString();
    }
}
