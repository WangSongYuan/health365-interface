package cn.sqwsy.health365interface.dao.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

import cn.sqwsy.health365interface.dao.entity.SProgrammeEntity;
import cn.sqwsy.health365interface.dao.sql.ProgrammeSqlFactory;

public interface ProgrammeMapper {
	@InsertProvider(type = ProgrammeSqlFactory.class, method = "setProgramme")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setProgramme(SProgrammeEntity programme);
}
