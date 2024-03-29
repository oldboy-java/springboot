---   抢红包
--- Generated by Luanalysis
--- Created by Administrator.
--- DateTime: 2022-4-10 下午 16:01
---


-- 红包ID
local RED_PACKET_ID = tonumber(KEYS[1]);
redis.log(redis.LOG_NOTICE, '红包ID= ' .. RED_PACKET_ID);

--用户ID
local ROB_USER_ID = tonumber(KEYS[2]);
redis.log(redis.LOG_NOTICE, '抢红包用户ID= ' .. ROB_USER_ID);


-- 红包列表Key
local RED_PACKET_LIST_KEY = 'redpacket:'..RED_PACKET_ID;
redis.log(redis.LOG_NOTICE, '红包列表Key= ' .. RED_PACKET_LIST_KEY);

-- 红包被抢记录HashKey
local RED_PACKET_ROB_HASH_KEY = 'redpacket:rob:'..RED_PACKET_ID;
redis.log(redis.LOG_NOTICE, '红包被抢记录HashKey= ' .. RED_PACKET_ROB_HASH_KEY);


-- 当前用户是否抢过当前红包  (命令：hget redpacket:rob:1 1000)
local ROB_INFO = redis.call('HGET', RED_PACKET_ROB_HASH_KEY, ROB_USER_ID);

-- 如果没有抢过红包，则返回nil，redis中返回nil和false代表假
if (ROB_INFO == false)  then
    redis.log(redis.LOG_NOTICE,  'user='..ROB_USER_ID..' do not rob redPacket '..RED_PACKET_ID);

   -- 从list队列中弹出红包 (命令： lpop redpacket:2)
    local RED_PACKET = redis.call('LPOP', RED_PACKET_LIST_KEY);
    if (RED_PACKET == false) then
        return -1;  -- 红包已抢完
    else
        redis.log(redis.LOG_NOTICE, 'user= ' .. ROB_USER_ID..' rob '..RED_PACKET);
         -- 记录用户抢红包记录
        redis.call('HSET',RED_PACKET_ROB_HASH_KEY,ROB_USER_ID,RED_PACKET);
        return tonumber(RED_PACKET);
    end
else
    redis.log(redis.LOG_NOTICE,  'user='..ROB_USER_ID..' robed  redPacket '..RED_PACKET_ID..',money='..ROB_INFO);
      return -2; -- 已抢过红包
end
