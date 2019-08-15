package cn.sqwsy.health365interface.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.dao.entity.SOrgEntity;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

public class OrgSqlFactory {
	public String getOrg(Map<String, Object> para){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("s_org");
        if(ValidateUtil.isNotNull(para.get("id").toString())){
        	sql.WHERE("id="+para.get("id"));
        }
        return sql.toString();
	}
	
	public String setOrg(SOrgEntity org){
		 SQL sql = new SQL();
	        sql.INSERT_INTO("s_org");
	        if(ValidateUtil.isNotNull(org.getName())){
	        	sql.VALUES("name", "#{name}");
	        }
	        sql.VALUES("createtime", "now()");
	        return sql.toString();
	}
	
	public String updateOrg(SOrgEntity org){
		 SQL sql = new SQL();
	        sql.UPDATE("s_org");
	        if(ValidateUtil.isNotNull(org.getName())){
	        	sql.SET("name=#{name}");
	        }
	        sql.SET("updatetime", "now()");
	        sql.WHERE("id = #{id}");
	        return sql.toString();
	}
}
