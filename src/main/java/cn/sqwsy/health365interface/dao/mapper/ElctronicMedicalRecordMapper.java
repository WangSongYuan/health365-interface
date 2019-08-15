package cn.sqwsy.health365interface.dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.sqwsy.health365interface.dao.entity.SelctronicMedicalRecordEntity;
import cn.sqwsy.health365interface.dao.sql.ElctronicMedicalRecordSqlFactory;

public interface ElctronicMedicalRecordMapper {
	@SelectProvider(type=ElctronicMedicalRecordSqlFactory.class,method="getEMR")
	SelctronicMedicalRecordEntity getEMR(Map<String, Object> para);
	
	@InsertProvider(type = ElctronicMedicalRecordSqlFactory.class, method = "setEMR")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setEMR(SelctronicMedicalRecordEntity emr);
	
	@UpdateProvider(type = ElctronicMedicalRecordSqlFactory.class, method = "updateEMR")
	void updateEMR(SelctronicMedicalRecordEntity emr);
}
