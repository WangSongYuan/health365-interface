package cn.sqwsy.health365interface.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.sqwsy.health365interface.dao.entity.SInhospitalEntity;
import cn.sqwsy.health365interface.dao.sql.InhospitalSqlFactory;

public interface InhospitalMapper {
	@InsertProvider(type = InhospitalSqlFactory.class, method = "setInhospital")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setInhospital(SInhospitalEntity inhospitalEntity);
	
	@SelectProvider(type=InhospitalSqlFactory.class,method="getInhospital")
	@ResultMap("inhospitalMap")
	SInhospitalEntity getInhospital(Map<String, Object> para);
	
	@UpdateProvider(type=InhospitalSqlFactory.class,method="updateInhospital")
	void updateInhospital(SInhospitalEntity inhospitalEntity);
	
	@Select("SELECT s.visitnum,s.zy_code,s.patientHisId, s.INHOSPITALCOUNT, d.THIRDPARTYHISID FROM s_inhospital s, s_department d WHERE s.INHOSPITALDEPARTMENTID = d.id AND s.inhospitalstatus = 1")
	List<Map<String,String>> getInhospitalAndDepartmentId();
	
    @Results(id="inhospitalMap", value={
            @Result(id = true,property = "id" ,column = "id"),
            @Result(property ="sPatientEntity",column="patientId",one =@One(select ="cn.sqwsy.health365interface.dao.mapper.PatientMapper.getPatinetById"))}
    )
    @Select("select * from s_inhospital s where s.zy_code = #{zy_code}")
    public SInhospitalEntity getInhospitalByZyCode(@Param("zy_code") String zy_code);
}