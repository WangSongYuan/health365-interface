package cn.sqwsy.health365interface.dao.sql;


import org.apache.ibatis.jdbc.SQL;
import cn.sqwsy.health365interface.dao.entity.SMessageListEntity;

public class MessageListSqlFactory {

	public String setMessageList(SMessageListEntity messageListEntity){
		 SQL sql = new SQL();
	        sql.INSERT_INTO("s_messagelist");
	        sql.VALUES("createTime", "now()");
	        sql.VALUES("type", "4");
	        sql.VALUES("title", "#{title}");
	        sql.VALUES("orgId", "#{orgId}");
	        sql.VALUES("inhospitalId", "#{inhospitalId}");
	        sql.VALUES("isRead", "#{isRead}");
	        sql.VALUES("recipientId", "#{recipientId}");
	        sql.VALUES("senderId", "#{senderId}");
	        return sql.toString();
	}
	
	public String updateMessageList(SMessageListEntity messageListEntity){
		 SQL sql = new SQL();
	        sql.UPDATE("s_messagelist");
	        sql.SET("title=#{title}");
	        sql.SET("isRead=#{isRead}");
	        sql.SET("recipientId=#{recipientId}");
	        sql.WHERE("inhospitalId=#{inhospitalId}");
	        sql.WHERE("type=4");
	        return sql.toString();
	}
}
