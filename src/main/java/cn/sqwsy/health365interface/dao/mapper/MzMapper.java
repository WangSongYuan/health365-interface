package cn.sqwsy.health365interface.dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.sqwsy.health365interface.dao.entity.SOutpatientServiceInfoEntity;
import cn.sqwsy.health365interface.dao.sql.MzSqlFactory;


public interface MzMapper {
	@SelectProvider(type=MzSqlFactory.class,method="getMz")
	SOutpatientServiceInfoEntity getMz(Map<String, Object> para);
	
	@InsertProvider(type = MzSqlFactory.class, method = "setMz")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setMz(SOutpatientServiceInfoEntity mz);
	
	@UpdateProvider(type=MzSqlFactory.class,method="updateMz")
	void updateMz(SOutpatientServiceInfoEntity mz);
}