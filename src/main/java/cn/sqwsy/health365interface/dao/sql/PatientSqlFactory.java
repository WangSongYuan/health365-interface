package cn.sqwsy.health365interface.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.dao.entity.SPatientEntity;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

public class PatientSqlFactory {
	public String getPatinet(Map<String, Object> para){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("s_patient");
        if(ValidateUtil.isNotNull(para.get("cardnum").toString())){
        	sql.WHERE("cardnum='"+para.get("cardnum")+"'");
        }
        if(ValidateUtil.isNotNull(para.get("orgId").toString())){
        	sql.WHERE("orgId='"+para.get("orgId")+"'");
        }
        return sql.toString();
	}
	
	public String setPatient(SPatientEntity patient){
		 SQL sql = new SQL();
	        sql.INSERT_INTO("s_patient");
	        if(ValidateUtil.isNotNull(patient.getName())){
	        	sql.VALUES("name", "#{name}");
	        }
	        
	       if(patient.getAge()!=null){
	    	   sql.VALUES("age", "#{age}");
	       }
	    	/**cardnum*/
	    	
	    	if(ValidateUtil.isNotNull(patient.getCardnum())){
	    		sql.VALUES("cardnum", "#{cardnum}");
	    	}
	    	/**sex*/
	    	
	    	if(ValidateUtil.isNotNull(patient.getSex())){
	    		sql.VALUES("sex", "#{sex}");
	    	}
	    	/**婚姻状况*/
	    	
	    	if(ValidateUtil.isNotNull(patient.getMarry())){
	    		sql.VALUES("marry", "#{marry}");
	    	}
	    	/**职业*/
	    	
	    	if(ValidateUtil.isNotNull(patient.getProfession())){
	    		sql.VALUES("profession", "#{profession}");
	    	}
	    	/**currentaddress*/
	    	
	    	if(ValidateUtil.isNotNull(patient.getCurrentaddress())){
	    		sql.VALUES("currentaddress", "#{currentaddress}");
	    	}
	    	/**teladdress*/
	    	
	    	if(ValidateUtil.isNotNull(patient.getTeladdress())){
	    		sql.VALUES("teladdress", "#{teladdress}");
	    	}
	    	/**company*/
	    	
	    	if(ValidateUtil.isNotNull(patient.getCompany())){
	    		sql.VALUES("company", "#{company}");
	    	}
	    	/**文化程度*/
	    	
	    	if(ValidateUtil.isNotNull(patient.getEducation())){
	    		sql.VALUES("education", "#{education}");
	    	}
	    	/**ageunit*/
	    	
	    	if(ValidateUtil.isNotNull(patient.getAgeunit())){
	    		sql.VALUES("ageunit", "#{ageunit}");
	    	}
	    	
	    	/**createtime*/
	    	sql.VALUES("createtime", "now()");
	    	/**nation*/
	    	
	    	if(ValidateUtil.isNotNull(patient.getNation())){
	    		sql.VALUES("nation", "#{nation}");
	    	}
	    	/**birthday*/
	    	
	    	if(patient.getBirthday()!=null){
	    		sql.VALUES("birthday", "#{birthday}");
	    	}
	    	/**既往史*/
	    	
	    	if(ValidateUtil.isNotNull(patient.getPasthistory())){
	    		sql.VALUES("pasthistory", "#{pasthistory}");
	    	}
	    	/**常住类型*/
	    	
	    	if(ValidateUtil.isNotNull(patient.getPermanenttype())){
	    		sql.VALUES("permanenttype", "#{permanenttype}");
	    	}
	    	/**联系人姓名*/
	    	
	    	if(ValidateUtil.isNotNull(patient.getRelativename())){
	    		sql.VALUES("relativename", "#{relativename}");
	    	}
	    	
	    	if(ValidateUtil.isNotNull(patient.getRelativephone())){
	    		sql.VALUES("relativephone", "#{relativephone}");
	    	}//联系人电话
	    	
	    	/**常驻类型*/
	    	if(ValidateUtil.isNotNull(patient.getResidenttype())){
	    		sql.VALUES("residenttype", "#{residenttype}");
	    	}
	    	
	    	if(ValidateUtil.isNotNull(patient.getPhone())){
	    		sql.VALUES("phone", "#{phone}");
	    	}//最近一次更新的手机号
	    	
	    	if(ValidateUtil.isNotNull(patient.getPhonetwo())){
	    		sql.VALUES("phonetwo", "#{phonetwo}");
	    	}
	    	
	    	if(ValidateUtil.isNotNull(patient.getBloodtype())){
	    		sql.VALUES("bloodtype", "#{bloodtype}");
	    	}//血型
	    	if(ValidateUtil.isNotNull(patient.getPhonethree())){
	    		sql.VALUES("phonethree", "#{phonethree}");
	    	}//病人修改电话
	    	if(ValidateUtil.isNotNull(patient.getRelation())){
	    		sql.VALUES("relation", "#{relation}");
	    	}//与联系人关系
	    	sql.VALUES("orgId", "#{orgId}");
	        return sql.toString();
	}
	
	public String updatePatient(SPatientEntity patient){
		SQL sql = new SQL();
		sql.UPDATE("s_patient");
		if(ValidateUtil.isNotNull(patient.getName())){
        	sql.SET("name=#{name}");
        }
        
       if(patient.getAge()!=null){
    	   sql.SET("age=#{age}");
       }
    	/**cardnum*/
    	
    	if(ValidateUtil.isNotNull(patient.getCardnum())){
    		sql.SET("cardnum=#{cardnum}");
    	}
    	/**sex*/
    	
    	if(ValidateUtil.isNotNull(patient.getSex())){
    		sql.SET("sex=#{sex}");
    	}
    	/**婚姻状况*/
    	
    	if(ValidateUtil.isNotNull(patient.getMarry())){
    		sql.SET("marry=#{marry}");
    	}
    	/**职业*/
    	
    	if(ValidateUtil.isNotNull(patient.getProfession())){
    		sql.SET("profession=#{profession}");
    	}
    	/**currentaddress*/
    	
    	if(ValidateUtil.isNotNull(patient.getCurrentaddress())){
    		sql.SET("currentaddress=#{currentaddress}");
    	}
    	/**teladdress*/
    	
    	if(ValidateUtil.isNotNull(patient.getTeladdress())){
    		sql.SET("teladdress=#{teladdress}");
    	}
    	/**company*/
    	
    	if(ValidateUtil.isNotNull(patient.getCompany())){
    		sql.SET("company=#{company}");
    	}
    	/**文化程度*/
    	
    	if(ValidateUtil.isNotNull(patient.getEducation())){
    		sql.SET("education=#{education}");
    	}
    	/**ageunit*/
    	
    	if(ValidateUtil.isNotNull(patient.getAgeunit())){
    		sql.SET("ageunit=#{ageunit}");
    	}
    	/**createtime*/
    	
    	if(patient.getCreatetime()==null){
    		sql.SET("createtime=now()");
    	}
    	/**updatetime*/
    	
    	sql.SET("updatetime=now()");
    	/**nation*/
    	
    	if(ValidateUtil.isNotNull(patient.getNation())){
    		sql.SET("nation=#{nation}");
    	}
    	/**birthday*/
    	
    	if(patient.getBirthday()!=null){
    		sql.SET("birthday=#{birthday}");
    	}
    	/**既往史*/
    	
    	if(ValidateUtil.isNotNull(patient.getPasthistory())){
    		sql.SET("pasthistory=#{pasthistory}");
    	}
    	/**常住类型*/
    	
    	if(ValidateUtil.isNotNull(patient.getPermanenttype())){
    		sql.SET("permanenttype=#{permanenttype}");
    	}
    	/**联系人姓名*/
    	
    	if(ValidateUtil.isNotNull(patient.getRelativename())){
    		sql.SET("relativename=#{relativename}");
    	}
    	
    	if(ValidateUtil.isNotNull(patient.getRelativephone())){
    		sql.SET("relativephone=#{relativephone}");
    	}//联系人电话
    	
    	/**常驻类型*/
    	if(ValidateUtil.isNotNull(patient.getResidenttype())){
    		sql.SET("residenttype=#{residenttype}");
    	}
    	
    	if(ValidateUtil.isNotNull(patient.getPhone())){
    		sql.SET("phone=#{phone}");
    	}//最近一次更新的手机号
    	
    	if(ValidateUtil.isNotNull(patient.getPhonetwo())){
    		sql.SET("phonetwo=#{phonetwo}");
    	}
    	
    	if(ValidateUtil.isNotNull(patient.getBloodtype())){
    		sql.SET("bloodtype=#{bloodtype}");
    	}//血型
    	if(ValidateUtil.isNotNull(patient.getPhonethree())){
    		sql.SET("phonethree=#{phonethree}");
    	}//病人修改电话
    	if(ValidateUtil.isNotNull(patient.getRelation())){
    		sql.SET("relation=#{relation}");
    	}//与联系人关系
    	sql.WHERE("id=#{id}");
		return sql.toString();
	}
}
