package cn.sqwsy.health365interface.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.dao.entity.SUserEntity;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

public class UserSqlFactory {
	public String getUser(Map<String, Object> para){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("s_user");
        if(para.get("thirdpartyhisid")!=null){
        	sql.WHERE("thirdpartyhisid="+para.get("thirdpartyhisid"));
        }
        if(para.get("id")!=null){
        	sql.WHERE("id="+para.get("id"));
        }
        if(para.get("jobnum")!=null){
        	sql.WHERE("jobnum="+para.get("jobnum"));
        }
        return sql.toString();
	}
	
	public String setUser(SUserEntity user){
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
	        return sql.toString();
	}
	
	public String updateUser(SUserEntity user){
        SQL sql = new SQL();
        sql.UPDATE("s_user");
        if(ValidateUtil.isNotNull(user.getName())){
        	sql.SET("name=#{name}");
        }
        if(ValidateUtil.isNotNull(user.getJobnum())){
        	sql.SET("jobnum=#{jobnum}");
        }
        if(ValidateUtil.isNotNull(user.getPassword())){ 
            sql.SET("password=#{password}");
        } 
        if(ValidateUtil.isNotNull(user.getThirdpartyhisid())){ 
            sql.SET("thirdpartyhisid=#{thirdpartyhisid}");
        } 
        sql.SET("updatetime=now()");
        sql.WHERE("id=#{id}");
        return sql.toString();
	}
}
