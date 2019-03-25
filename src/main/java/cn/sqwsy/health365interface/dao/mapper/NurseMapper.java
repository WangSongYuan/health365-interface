package cn.sqwsy.health365interface.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.SelectProvider;

import cn.sqwsy.health365interface.dao.entity.Nurse;
import cn.sqwsy.health365interface.dao.sql.NurseSqlFactory;


public interface NurseMapper {
	@SelectProvider(type=NurseSqlFactory.class,method="getAllNurse")
	List<Nurse> getAllNurse();
}
