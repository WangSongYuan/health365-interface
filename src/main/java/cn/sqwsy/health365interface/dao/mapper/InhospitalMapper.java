package cn.sqwsy.health365interface.dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.sqwsy.health365interface.dao.entity.SInhospitalEntity;
import cn.sqwsy.health365interface.dao.sql.InhospitalSqlFactory;

public interface InhospitalMapper {
	@InsertProvider(type = InhospitalSqlFactory.class, method = "setInhospital")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setInhospital(SInhospitalEntity inhospitalEntity);
	
	@SelectProvider(type=InhospitalSqlFactory.class,method="getInhospital")
	SInhospitalEntity getInhospital(Map<String, Object> para);
	
	@UpdateProvider(type=InhospitalSqlFactory.class,method="updateInhospital")
	void updateInhospital(SInhospitalEntity inhospitalEntity);
}
