package cn.sqwsy.health365interface.dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.sqwsy.health365interface.dao.entity.SPatientEntity;
import cn.sqwsy.health365interface.dao.sql.PatientSqlFactory;



public interface PatientMapper {
	@SelectProvider(type=PatientSqlFactory.class,method="getPatinet")
	SPatientEntity getPatinet(Map<String, Object> para);
	
	@InsertProvider(type = PatientSqlFactory.class, method = "setPatient")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setPatient(SPatientEntity patient);
	
	@UpdateProvider(type=PatientSqlFactory.class,method="updatePatient")
	void updatePatient(SPatientEntity patient);
}
