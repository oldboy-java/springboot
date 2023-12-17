package com.liuli.boot.es.java.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.elasticsearch.search.sort.SortOrder;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Sort {
    /**
     * 排序字段
     */
    private String field;

    /**
     *  排序
     */
    private SortOrder sortOrder;
}
