package cn.sqwsy.health365interface.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.dao.entity.SUserorgEntity;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

public class UserOrgSqlFactory {
	public String getUserOrg(Map<String, Object> para){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("s_userorg");
        if(ValidateUtil.isNotNull(para.get("userid").toString())){
        	sql.WHERE("userid='"+para.get("userid")+"'");
        }
        if(ValidateUtil.isNotNull(para.get("departmentid").toString())){
        	sql.WHERE("departmentid='"+para.get("departmentid")+"'");
        }
        if(ValidateUtil.isNotNull(para.get("orgid").toString())){
        	sql.WHERE("orgid='"+para.get("orgid")+"'");
        }
        return sql.toString();
	}
	
	public String setUserOrg(SUserorgEntity userOrg){
		 SQL sql = new SQL();
	        sql.INSERT_INTO("s_userorg");
	        if(ValidateUtil.isNotNull(userOrg.getUserid().toString())){
	        	sql.VALUES("userid", "#{userid}");
	        }
	        if(ValidateUtil.isNotNull(userOrg.getDepartmentid().toString())){
	        	sql.VALUES("departmentid", "#{departmentid}");
	        }
	        if(ValidateUtil.isNotNull(userOrg.getOrgid().toString())){ 
	            sql.VALUES("orgid", "#{orgid}");
	        } 
	        return sql.toString();
	}
}
