package cn.sqwsy.health365interface.dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;

import cn.sqwsy.health365interface.dao.entity.SRoleEntity;
import cn.sqwsy.health365interface.dao.sql.RoleSqlFactory;


public interface RoleMapper {
	@SelectProvider(type=RoleSqlFactory.class,method="getRole")
	SRoleEntity getRole(Map<String, Object> para);
	
	@InsertProvider(type = RoleSqlFactory.class, method = "setRole")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setRole(SRoleEntity role);
}
