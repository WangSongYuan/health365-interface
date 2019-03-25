package cn.sqwsy.health365interface.dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

import cn.sqwsy.health365interface.dao.entity.SOrgEntity;
import cn.sqwsy.health365interface.dao.sql.OrgSqlFactory;


public interface OrgMapper {
	@SelectProvider(type=OrgSqlFactory.class,method="getOrg")
	SOrgEntity getOrg(Map<String, Object> para);
	
	/*@InsertProvider(type = OrgSqlFactory.class, method = "setOrg")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setOrg(SOrgEntity org);*/
}
