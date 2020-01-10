package com.scau.ssm.dao;

import com.scau.ssm.domain.Permission;
import com.scau.ssm.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IRoleDao {

    //根据用户id查询出所有对应的角色
    @Select("select * from role where id in (select roleId from users_role where userId=#{userId})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many = @Many(select = "com.scau.ssm.dao.IPermissionDao.findPermissionByRoleId"))
    })
    public List<Role> findRoleByUserId(String userId) throws Exception;


    @Select("select * from role")
    public List<Role> findAll();

    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    public void add(Role role) throws Exception;

    @Select("select * from role where id=#{id}")
    public Role findById(String id) throws Exception;

    @Select("select * from permission where id not in (select permissionId from role_permission where roleId=#{id})")
    public List<Permission> findOtherPermissions(String id) throws Exception;

    @Insert("insert into role_permission(roleId,permissionId) values(#{roleId},#{permissionId})")
    public void addPermissionToRole(@Param("roleId") String roleId,@Param("permissionId") String permissionId) throws Exception ;
}
