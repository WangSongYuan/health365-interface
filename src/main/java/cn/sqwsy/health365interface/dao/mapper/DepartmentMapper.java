package cn.sqwsy.health365interface.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.sqwsy.health365interface.dao.entity.SDepartmentEntity;
import cn.sqwsy.health365interface.dao.sql.DepartmentSqlFactory;

public interface DepartmentMapper {
	@Select("SELECT * FROM s_department WHERE id = #{id}")
	SDepartmentEntity getDepartmentById(int id);

	@Select("SELECT * FROM s_department WHERE thirdpartyhisid = #{thirdpartyhisid} and orgId =#{orgId}")
	SDepartmentEntity getDepartment(Map<String,Object> para);

	@InsertProvider(type = DepartmentSqlFactory.class, method = "setDepartment")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setDepartment(SDepartmentEntity department);

	@UpdateProvider(type = DepartmentSqlFactory.class, method = "updateDepartment")
	void updateDepartment(SDepartmentEntity department);
	
	@SelectProvider(type=DepartmentSqlFactory.class,method="getDepartmentsList")
	List<SDepartmentEntity> getDepartmentsList(Map<String,Object> para);
}
