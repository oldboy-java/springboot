package com.liuli.boot.sharding.mapper;

import com.liuli.boot.sharding.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into t_user (f_username,f_age) values(#{user.userName},#{user.age}})")
     Integer insertUser(@Param("user") User user);


    @Select("<script> select f_id as id, f_username as userName,f_age as age from t_user where f_id in " +
      "<foreach item='userId' collection='userIds'  open='( ' close=')' separator=','> "+"#{userId}"+" </foreach> </script>")
    List<User> listUsers(@Param("userIds") List<Long> userIds);

}
