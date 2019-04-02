package cn.sqwsy.health365interface.dao.sql;


import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.dao.entity.SDepartmentEntity;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

public class DepartmentSqlFactory {
	public String setDepartment(SDepartmentEntity department){
        SQL sql = new SQL();
        sql.INSERT_INTO("s_department");
        if(department.getType() != null){
        	sql.VALUES("type", "#{type}");
        }
        if(department.getState() != null){
        	//管理状态 默认不管理
        	sql.VALUES("state", "#{state}");
        }
        if(department.getCreatetime() == null){ 
            sql.VALUES("createtime", "now()");
        } 
        if(department.getOrgid() != null){ 
            sql.VALUES("orgid", "#{orgid}");
        } 
        if(department.getName() != null){ 
            sql.VALUES("NAME", "#{name}");
        }
        if(department.getThirdpartyhisid() != null){ 
            sql.VALUES("thirdpartyhisid", "#{thirdpartyhisid}");
        }
        return sql.toString();
	}
	
	public String updateDepartment(SDepartmentEntity department){
        SQL sql = new SQL();
        sql.UPDATE("s_department");
        if(department.getType() != null){
        	sql.SET("type=#{type}");
        }
        if(department.getState() != null){
        	sql.SET("state=#{state}");
        }
        if(department.getOrgid() != null){ 
            sql.SET("orgid=#{orgid}");
        } 
        if(department.getName() != null){ 
            sql.SET("NAME=#{name}");
        }
        if(department.getThirdpartyhisid() != null){ 
            sql.SET("thirdpartyhisid=#{thirdpartyhisid}");
        }
        //wangsongyuan 暂时先不设更新时间用于区分状态是否人为修改	20190420
        //sql.SET("updatetime=now()");
        sql.WHERE("id = #{id}");
        return sql.toString();
	}
	
	public String getDepartmentsList(Map<String, Object> para){
		SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("s_department");
        if(ValidateUtil.isNotNull(para.get("orgId").toString())){
        	sql.WHERE("orgId="+para.get("orgId"));
        }
        if(para.get("state")!=null){
        	sql.WHERE("state="+para.get("state"));
        }
        return sql.toString();
	}
}
