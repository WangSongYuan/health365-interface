package cn.sqwsy.health365interface.dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.sqwsy.health365interface.dao.entity.SOrgEntity;
import cn.sqwsy.health365interface.dao.sql.OrgSqlFactory;


public interface OrgMapper {
	@SelectProvider(type=OrgSqlFactory.class,method="getOrg")
	SOrgEntity getOrg(Map<String, Object> para);
	
	@InsertProvider(type = OrgSqlFactory.class, method = "setOrg")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setOrg(SOrgEntity org);

	@UpdateProvider(type = OrgSqlFactory.class, method = "updateOrg")
	void updateOrg(SOrgEntity org);
}