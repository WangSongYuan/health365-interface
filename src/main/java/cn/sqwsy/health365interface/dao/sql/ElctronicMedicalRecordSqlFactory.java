package cn.sqwsy.health365interface.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.dao.entity.SelctronicMedicalRecordEntity;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

public class ElctronicMedicalRecordSqlFactory {

	public String getEMR(Map<String, Object> para){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("s_emr");
        if(para.get("outhospitrecordid")!=null){
        	sql.WHERE("outhospitrecordid="+para.get("outhospitrecordid"));
        }
        return sql.toString();
	}
	
	public String setEMR(SelctronicMedicalRecordEntity emr){
		 SQL sql = new SQL();
	        sql.INSERT_INTO("s_emr");
	        if(ValidateUtil.isNotNull(emr.getOuthospitrecordid())){
	        	sql.VALUES("outhospitrecordid", "#{outhospitrecordid}");
	        }
	        if(ValidateUtil.isNotNull(emr.getContent())){
	        	sql.VALUES("content", "#{content}");
	        }
	        if(ValidateUtil.isNotNull(emr.getOuthospitrecordid())){
	        	sql.VALUES("createtime", "now()");
	        }
	        return sql.toString();
	}
	
	public String updateEMR(SelctronicMedicalRecordEntity emr){
        SQL sql = new SQL();
        sql.UPDATE("s_emr");
        if(ValidateUtil.isNotNull(emr.getContent())){
        	sql.SET("content=#{content}");
        }
        sql.SET("updatetime=now()");
        sql.WHERE("outhospitrecordid=#{outhospitrecordid}");
        return sql.toString();
	}
}
