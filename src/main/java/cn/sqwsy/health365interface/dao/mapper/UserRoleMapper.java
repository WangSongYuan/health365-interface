package cn.sqwsy.health365interface.dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;

import cn.sqwsy.health365interface.dao.entity.SUserroleEntity;
import cn.sqwsy.health365interface.dao.sql.UserRoleSqlFactory;



public interface UserRoleMapper {
	@SelectProvider(type=UserRoleSqlFactory.class,method="getUserRole")
	SUserroleEntity getUserRole(Map<String, Object> para);
	
	@InsertProvider(type = UserRoleSqlFactory.class, method = "setUserRole")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setUserRole(SUserroleEntity userRole);
}
