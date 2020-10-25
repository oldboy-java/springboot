package com.liul.shiro.mapper;

import com.liul.shiro.po.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    @Select("select * from t_user where user_name=#{userName}")
    public User findUserByUserName(@Param("userName") String userName);

    @Select("select\n" +
            "\ttr.role_name\n" +
            "from\n" +
            "\tt_user_role tur\n" +
            "inner join t_role tr on\n" +
            "\ttur.role_id = tr.id\n" +
            "inner join t_user tu on\n" +
            "\ttu.id = tur.user_id\n" +
            "where\n" +
            "\ttu.user_name = #{userName}")
    @Results(@Result(column="role_name", property="roleName"))
    public List<String> getUserRoles(@Param("userName") String userName);


    @Select("select\n" +
            "\tdistinct tp.permission \n" +
            "from\n" +
            "\tt_user_role tur\n" +
            "inner join t_role tr on\n" +
            "\ttur.role_id = tr.id\n" +
            "inner join t_user tu on\n" +
            "\ttu.id = tur.user_id\n" +
            "inner  join t_role_permission trp \n" +
            "on tr.id  = trp .role_id \n" +
            "inner  join  t_permission tp \n" +
            "on tp.id  = trp .id \n" +
            "where\n" +
            "\ttu.user_name = #{userName}")
    @Results(@Result(column="permission", property="permission"))
    public Set<String> getPermissions(@Param("userName") String userName);
}
