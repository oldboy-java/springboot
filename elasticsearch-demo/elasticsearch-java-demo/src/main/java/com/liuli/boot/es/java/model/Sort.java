package com.liuli.boot.es.java.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Sort {
    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 是否升序,默认升序
     */
    private Boolean asc;
}
