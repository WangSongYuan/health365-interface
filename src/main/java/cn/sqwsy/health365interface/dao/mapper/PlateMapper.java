package cn.sqwsy.health365interface.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

import cn.sqwsy.health365interface.dao.entity.SPlateEntity;
import cn.sqwsy.health365interface.dao.sql.PlateSqlFactory;

public interface PlateMapper {
	@SelectProvider(type=PlateSqlFactory.class,method="getAllPlate")
	List<SPlateEntity> getAllPlate(Map<String, Object> para);
}