package cn.sqwsy.health365interface.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.service.utils.ValidateUtil;

public class PlateSqlFactory {
	public String getAllPlate(Map<String, Object> para){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("s_plate");
        if(ValidateUtil.isNotNull(para.get("state").toString())){
        	sql.WHERE("state="+para.get("state"));
        }
        return sql.toString();
	}
}
