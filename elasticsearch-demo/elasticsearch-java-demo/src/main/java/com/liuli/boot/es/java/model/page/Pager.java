package com.liuli.boot.es.java.model.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pager {
    protected Long totalCount;
    protected Long totalPage;
    protected Integer pageIndex;
    protected Integer pageSize;
}
