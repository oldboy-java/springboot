package com.liuli.service;

import com.liuli.pojo.Novel;
import com.liuli.utils.elasticsearch.Construct;
import org.elasticsearch.client.transport.TransportClient;

import java.util.List;

/**
 * Created by Administrator on 2018-9-1.
 */
public interface NovelService {

    /***
     * 建立索引
     * @param  constructList 索引结构集合
     * @param  indexName 索引名称
     * @param  typeName 类型名称
     * @return
     */
    public String createIndex(List<Construct> constructList,String indexName,String typeName);

    /***
     * 删除索引
     * @param  indexName 索引名称
     * @return
     */
    public String delIndex(String indexName);

    /**
     * 插入文档
     * @param novel
     * @return 文档ID
     * @throws Exception
     */
    public String saveNovel(Novel novel) throws Exception;

    /**
     * 删除文档
     * @param docId 文档ID
     * @return 返回结果状态 DELETED
     */
    public String delNovel(String docId);


    /***
     * 更新文档
     * @param novel
     * @return 返回结果状态 UPDATED
     */
    public String updateNovel(Novel novel) throws Exception;


    public List<Novel> schNovel(Novel novel,Integer offset,Integer pageSize);


}
