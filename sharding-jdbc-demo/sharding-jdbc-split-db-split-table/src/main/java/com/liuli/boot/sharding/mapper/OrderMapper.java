package com.liuli.boot.sharding.mapper;

import com.liuli.boot.sharding.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    @Insert("insert into t_order (f_price,f_user_id, f_status) values(#{order.price},#{order.userId}, #{order.status})")
     Integer insertOrder(@Param("order")Order order);


    @Select("<script> select f_id as id, f_price as price,f_user_id as userId,f_status as status from t_order where f_id in " +
      "<foreach item='orderId' collection='orderIds'  open='( ' close=')' separator=','> "+"#{orderId}"+" </foreach> </script>")
    List<Order> listOrders(@Param("orderIds") List<Long> orderIds);

}
