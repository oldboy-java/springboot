package com.liuli.boot.es.java.model.page;


import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1745180548325837892L;
    @Getter
    private Long totalCount;
    @Getter
    private Long totalPage;
    @Getter
    private Integer currentPage;
    @Getter
    private List<T> records = Collections.emptyList();

    public static <T> PageResult<T> emptyResult() {
        return new PageResult();
    }

    public PageResult(Long totalCount, Long totalPage, Integer currentPage, List<T> records) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.records = records;
    }


    public PageResult(Long totalCount, Integer pageSize, Integer currentPage, List<T> records) {
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.records = records;
        this.totalPage =  totalCount% pageSize== 0 ? totalCount/ pageSize : totalCount/ pageSize+ 1 ;
    }

    public PageResult() {
    }

}
