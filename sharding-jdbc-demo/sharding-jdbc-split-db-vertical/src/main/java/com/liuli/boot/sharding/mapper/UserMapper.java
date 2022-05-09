package com.liuli.boot.sharding.mapper;

import com.liuli.boot.sharding.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into t_user (f_user_name,f_password) values(#{user.userName},#{user.password})")
     Integer insertUser(@Param("user") User user);


    @Select("<script> select f_id as id, f_user_name as userName,f_password as password from t_user where f_id in " +
      "<foreach item='userId' collection='userIds'  open='( ' close=')' separator=','> "+"#{userId}"+" </foreach> </script>")
    List<User> lisUsers(@Param("userIds") List<Long> userIds);

}
