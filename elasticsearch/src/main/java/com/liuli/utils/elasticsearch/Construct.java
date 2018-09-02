package com.liuli.utils.elasticsearch;

/**
 * Created by Administrator on 2018-9-2.
 * 定义索引结构
 */
public class Construct {
    private String field; //字段
    private String type; //类型
    private String analyzer; //索引分词器
    private String searchAnalyzer;//检索分词器
    private String format; //格式

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

    public String getSearchAnalyzer() {
        return searchAnalyzer;
    }

    public void setSearchAnalyzer(String searchAnalyzer) {
        this.searchAnalyzer = searchAnalyzer;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
