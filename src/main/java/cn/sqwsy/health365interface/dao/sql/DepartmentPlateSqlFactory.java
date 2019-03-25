package cn.sqwsy.health365interface.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.dao.entity.SDepartmentplateEntity;

public class DepartmentPlateSqlFactory {
	public String setDepartmentPlate(SDepartmentplateEntity departmentPlate){
        SQL sql = new SQL();
        sql.INSERT_INTO("s_departmentplate");
        if(departmentPlate.getDepartmentid() != null){
        	sql.VALUES("departmentid", "#{departmentid}");
        }
        if(departmentPlate.getOrgid() != null){
        	sql.VALUES("orgid", "#{orgid}");
        }
        if(departmentPlate.getPlateid() != null){ 
        	sql.VALUES("plateid", "#{plateid}");
        }
        if(departmentPlate.getCreatetime() == null){ 
            sql.VALUES("createtime", "now()");
        } 
        return sql.toString();
	}
	
	public String getDepartmentPlate(Map<String, Object> para){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("s_departmentplate");
        if(!para.get("departmentid").equals("")){
        	sql.WHERE("departmentid="+para.get("departmentid"));
        }
        if(!para.get("orgid").equals("")){
        	sql.WHERE("orgid="+para.get("orgid"));
        }
        if(!para.get("plateid").equals("")){
        	sql.WHERE("plateid="+para.get("plateid"));
        }
        return sql.toString();
	}
}
