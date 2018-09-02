package com.liuli.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2018-9-1.
 */
@Configuration
public class ElasticsearchClientConfig {

    @Value("${elasticsearch.cluster-name}")
    private String clusterName;

    @Value("${elasticsearch.master.host}")
    private String masterHost;

    @Value("${elasticsearch.master.port}")
    private Integer masterPort;

    @Value("${elasticsearch.slave.host}")
    private String slaveHost;

    @Value("${elasticsearch.slave.port}")
    private Integer slavePort;


    @Bean(name = "client")
    public TransportClient client() throws UnknownHostException{
        //构造节点
        TransportAddress master = new InetSocketTransportAddress(InetAddress.getByName(masterHost),masterPort);
        TransportAddress slave = new InetSocketTransportAddress(InetAddress.getByName(slaveHost),slavePort);

        Settings settings = Settings.builder().put("cluster.name",clusterName).build();

        PreBuiltTransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(master);
        client.addTransportAddress(slave);
        return client;
    }
}
