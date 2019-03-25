package cn.sqwsy.health365interface.dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;

import cn.sqwsy.health365interface.dao.entity.SDepartmentplateEntity;
import cn.sqwsy.health365interface.dao.sql.DepartmentPlateSqlFactory;

public interface DepartmentPlateMapper {
	@InsertProvider(type = DepartmentPlateSqlFactory.class, method = "setDepartmentPlate")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setDepartmentPlate(SDepartmentplateEntity departmentPlate);
	
	@SelectProvider(type=DepartmentPlateSqlFactory.class,method="getDepartmentPlate")
	SDepartmentplateEntity getDepartmentPlate(Map<String, Object> para);
}
