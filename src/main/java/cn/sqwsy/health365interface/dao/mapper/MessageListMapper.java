package cn.sqwsy.health365interface.dao.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.sqwsy.health365interface.dao.entity.SMessageListEntity;
import cn.sqwsy.health365interface.dao.sql.MessageListSqlFactory;

public interface MessageListMapper {
	@Select("select * from s_messagelist where inhospitalid =#{inhospitalid} and type = 4")
	SMessageListEntity getMessageByInhospitalid(@Param("inhospitalid") Integer inhospitalid);
	
	@InsertProvider(type = MessageListSqlFactory.class, method = "setMessageList")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	void setMessageList(SMessageListEntity messageList);
	
	@UpdateProvider(type = MessageListSqlFactory.class, method = "updateMessageList")
	void updateMessageList(SMessageListEntity messageList);
	
	@Delete("delete from s_messagelist where inhospitalid = #{inhospitalid} and type = 4")
	int deleteMessageList(@Param("inhospitalid") Integer inhospitalid);
}