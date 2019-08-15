package cn.sqwsy.health365interface.dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.sqwsy.health365interface.dao.entity.SUserEntity;
import cn.sqwsy.health365interface.dao.sql.UserSqlFactory;


public interface UserMapper {
	@SelectProvider(type=UserSqlFactory.class,method="getUser")
	SUserEntity getUser(Map<String, Object> para);
	
	@InsertProvider(type = UserSqlFactory.class, method = "setUser")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setUser(SUserEntity user);
	
	/**
	 * 根据科室随机获取一个疾病管理师
	 * @param departmentId
	 * @return
	 */
	@Select("SELECT u.id, u.`NAME` FROM s_department d, s_user u, s_userorg uo, s_userrole ur, s_role r WHERE d.id = uo.DEPARTMENTID AND u.id = uo.USERID AND d.id = #{departmentId} AND u.id = ur.userid AND r.id = ur.roleid AND r.typeId = 3 ORDER BY rand() LIMIT 1")
	SUserEntity getRandDiseaseManager(@Param("departmentId")Integer departmentId);
	
	/**
	 * 根据科室获取任务量最少的疾病管理师
	 * @param departmentId
	 * @return
	 */
	@Select("SELECT u.id, COUNT(i.inDiseaseManagerId) AS num FROM s_user u LEFT JOIN s_inhospital i ON u.id = i.inDiseaseManagerId LEFT JOIN s_userrole ur ON ur.userid = u.id LEFT JOIN s_role r ON ur.roleid = r.id LEFT JOIN s_userorg uo ON uo.USERID = u.id WHERE r.typeId = 3 OR r.typeId = 8 AND uo.DEPARTMENTID = #{departmentId} GROUP BY u.id ORDER BY num LIMIT 1")
	SUserEntity getOrderDiseaseManager(@Param("departmentId")Integer departmentId);
	
	@UpdateProvider(type = UserSqlFactory.class, method = "updateUser")
	void updateUser(SUserEntity user);
}
