package cn.sqwsy.health365interface.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.dao.entity.SRoleEntity;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

public class RoleSqlFactory {
	public String getRole(Map<String, Object> para){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("s_role");
        if(ValidateUtil.isNotNull(para.get("orgId").toString())){
        	sql.WHERE("orgId="+para.get("orgId"));
        }
        if(ValidateUtil.isNotNull(para.get("typeId").toString())){
        	sql.WHERE("typeId="+para.get("typeId"));
        }
        return sql.toString();
	}
	
	public String setRole(SRoleEntity user){
		 SQL sql = new SQL();
	        sql.INSERT_INTO("s_user");
	        if(ValidateUtil.isNotNull(user.getName())){
	        	sql.VALUES("name", "#{name}");
	        }
	        System.out.println(sql.toString());
	        return sql.toString();
	}
}
