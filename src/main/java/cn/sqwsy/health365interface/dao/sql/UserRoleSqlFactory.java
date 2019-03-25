package cn.sqwsy.health365interface.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.dao.entity.SUserroleEntity;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

public class UserRoleSqlFactory {
	public String getUserRole(Map<String, Object> para){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("s_userrole");
        if(ValidateUtil.isNotNull(para.get("userid").toString())){
        	sql.WHERE("userid="+para.get("userid"));
        }
        if(ValidateUtil.isNotNull(para.get("roleid").toString())){
        	sql.WHERE("roleid="+para.get("roleid"));
        }
        return sql.toString();
	}
	
	public String setUserRole(SUserroleEntity userRole){
		 SQL sql = new SQL();
	        sql.INSERT_INTO("s_userrole");
	        sql.VALUES("userid", "#{sUserEntity.id}");
	        sql.VALUES("roleid", "#{sRoleEntity.id}");
	        return sql.toString();
	        /**
	         *  sql.VALUES("userid", "#{"+para.get("userid").toString()+"}");
	        sql.VALUES("roleid", "#{"+para.get("roleid").toString()+"}");
	         */
	}
}
