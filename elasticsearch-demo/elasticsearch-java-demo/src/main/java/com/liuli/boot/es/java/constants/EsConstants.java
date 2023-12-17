package com.liuli.boot.es.java.constants;

import org.elasticsearch.client.RequestOptions;

public class EsConstants {
    public static final RequestOptions DEFAULT_REQUEST_OPERATION = RequestOptions.DEFAULT;
    public static final String INDEX_MAPPING_PROPERTIES = "properties";
    public static final String DOC_ID = "id";
    public static final String DATE_FORMAT = "format";
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss " +
            "|| yyyy-MM-dd || yyyy/MM/dd HH:mm:ss || yyyy/MM/dd || epoch_millis";
}
