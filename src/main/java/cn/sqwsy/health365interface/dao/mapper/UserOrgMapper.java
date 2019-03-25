package cn.sqwsy.health365interface.dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;

import cn.sqwsy.health365interface.dao.entity.SUserorgEntity;
import cn.sqwsy.health365interface.dao.sql.UserOrgSqlFactory;


public interface UserOrgMapper {
	@SelectProvider(type=UserOrgSqlFactory.class,method="getUserOrg")
	SUserorgEntity getUserOrg(Map<String, Object> para);
	
	@InsertProvider(type = UserOrgSqlFactory.class, method = "setUserOrg")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setUserOrg(SUserorgEntity userOrg);
}
