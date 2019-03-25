package cn.sqwsy.health365interface.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

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
	/*
	public String setO(SUserEntity user){
		 SQL sql = new SQL();
	        sql.INSERT_INTO("s_user");
	        if(ValidateUtil.isNotNull(user.getName())){
	        	sql.VALUES("name", "#{name}");
	        }
	        if(ValidateUtil.isNotNull(user.getJobnum())){
	        	sql.VALUES("jobnum", "#{jobnum}");
	        }
	        if(ValidateUtil.isNotNull(user.getPassword())){ 
	            sql.VALUES("password", "#{password}");
	        } 
	        if(ValidateUtil.isNotNull(user.getThirdpartyhisid())){ 
	            sql.VALUES("thirdpartyhisid", "#{thirdpartyhisid}");
	        } 
	        if(user.getCreatetime()==null){ 
	            sql.VALUES("createtime", "now()");
	        }
	        System.out.println(sql.toString());
	        return sql.toString();
	}*/
}
