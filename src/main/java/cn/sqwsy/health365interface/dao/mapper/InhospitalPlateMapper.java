package cn.sqwsy.health365interface.dao.mapper;


import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.sqwsy.health365interface.dao.entity.SInhospitalplateEntity;
import cn.sqwsy.health365interface.dao.entity.SPlateEntity;
import cn.sqwsy.health365interface.dao.sql.InhospitalPlateSqlFactory;

public interface InhospitalPlateMapper {
	@InsertProvider(type = InhospitalPlateSqlFactory.class, method = "setInhospitalPlate")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setInhospitalPlate(SInhospitalplateEntity inhospitalPlate);
	
	@Select("SELECT p.id,p.type FROM s_department d, s_departmentplate dp, s_plate p WHERE d.id = dp.DEPARTMENTID AND d.id = #{departmentId} AND p.id = dp.PLATEID AND p.state = 1")
	List<SPlateEntity> getDepartmentPlate(@Param("departmentId")Integer departmentId);
}
