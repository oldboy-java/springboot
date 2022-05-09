package com.liuli.redis.list.constants;

public final class RedisCacheKeyConstant {
    /**
     * 红包key
     */
    public static  String RED_PACKET_LIST_KEY = "redpacket:%s";


    /**
     * 红包id Key
     */
    public static  String RED_PACKET_ID= "id:generator:redpacket";


    /**
     * 抢红包key
     */
    public static  String RED_PACKET_ROB_HASH_KEY = "redpacket:rob:%s";
}
