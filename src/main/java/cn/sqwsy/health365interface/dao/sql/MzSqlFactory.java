package cn.sqwsy.health365interface.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.dao.entity.SOutpatientServiceInfoEntity;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

public class MzSqlFactory {

	public String getMz(Map<String, Object> para){
		 SQL sql = new SQL();
	        sql.SELECT("*");
	        sql.FROM("s_mz_info");
	        sql.WHERE("mz_code='"+para.get("mz_code")+"'");
	        sql.WHERE("orgid='"+para.get("orgId")+"'");
	        return sql.toString();
	}
	
	public String setMz(SOutpatientServiceInfoEntity out){
		 SQL sql = new SQL();
	        sql.INSERT_INTO("s_mz_info");
	        //患者ID
	        if(out.getPatient()!=null){
	    		sql.VALUES("patientId", "#{patient.id}");
	    	}
	        //科室ID
	        if(out.getDepartment()!=null){
	    		sql.VALUES("departmentId", "#{department.id}");
	    	}
	        //门诊医生
	        if(out.getDoctorinfo()!=null){
	    		sql.VALUES("doctorId", "#{doctorinfo.id}");
	    	}
	        //门诊日期
	        if(out.getVisit_date()!=null){
	    		sql.VALUES("visit_date", "#{visit_date}");
	    	}
	        //总花费
	        if(out.getCosts()!=null){
	    		sql.VALUES("costs", "#{costs}");
	    	}
	        //门诊诊断
	        if(out.getDiag_desc()!=null){
	    		sql.VALUES("diag_desc", "#{diag_desc}");
	    	}
	        //患者HisId
	        if(out.getPatient_HisId()!=null){
	    		sql.VALUES("patient_HisId", "#{patient_HisId}");
	    	}
	        //门诊卡号
	        if(out.getCard_no()!=null){
	    		sql.VALUES("card_no", "#{card_no}");
	    	}
	        //门诊科室ID
	        if(out.getVisit_dept()!=null){
	    		sql.VALUES("visit_dept", "#{visit_dept}");
	    	}
	        //门诊医师ID
	        if(out.getDoctor_no()!=null){
	    		sql.VALUES("doctor_no", "#{doctor_no}");
	    	}
	        //错误原因
	        if(out.getErrorMsg()!=null){
	    		sql.VALUES("errorMsg", "#{errorMsg}");
	    	}
	        //就诊序号
	        if(out.getVisit_no()!=null){
	    		sql.VALUES("visit_no", "#{visit_no}");
	    	}
	        //号别
	        if(out.getClinic_label()!=null){
	    		sql.VALUES("clinic_label", "#{clinic_label}");
	    	}
	        sql.VALUES("schedulingState", "#{schedulingState}");
    		sql.VALUES("createTime", "#{createTime}");
    		sql.VALUES("isValid", "#{isValid}");
    		/*
    		 * V2.1门诊新增
    		 */
    		sql.VALUES("orgid", "#{orgid}");
    		sql.VALUES("mz_code", "#{mz_code}");
    		sql.VALUES("status", "#{status}");
    		//身份证号码
			if(ValidateUtil.isNotNull(out.getCardnum())){
				sql.VALUES("cardnum", "#{cardnum}");
			}
			//管理类型  1.正常排期管理2.感染病管理3.未知（例：慢性病管理  例：风险性管理等）
			if(out.getManagertype()!=null){
				sql.VALUES("managertype", "#{managertype}");
			}
			//病种表ID
			if(out.getChronicDiseaseId()!=null){
				sql.VALUES("chronicDiseaseId", "#{chronicDiseaseId}");
			}
			//确诊情况 1.未确诊2.疑似3.确诊
			if(out.getConfiredCases()!=null){
				sql.VALUES("confiredCases", "#{confiredCases}");
			}
	        return sql.toString();
	}
	
	public String updateMz(SOutpatientServiceInfoEntity out){
		SQL sql = new SQL();
		sql.UPDATE("s_mz_info");
			 //患者ID
	        if(out.getPatient()!=null){
	    		sql.SET("patientId = #{patient.id}");
	    	}
	        //科室ID
	        if(out.getDepartment()!=null){
	    		sql.SET("departmentId = #{department.id}");
	    	}
	        //门诊医生
	        if(out.getDoctorinfo()!=null){
	    		sql.SET("doctorId = #{doctorinfo.id}");
	    	}
	        //门诊日期
	        if(out.getVisit_date()!=null){
	    		sql.SET("visit_date = #{visit_date}");
	    	}
	        //总花费
	        if(out.getCosts()!=null){
	    		sql.SET("costs = #{costs}");
	    	}
	        //门诊诊断
	        if(out.getDiag_desc()!=null){
	    		sql.SET("diag_desc = #{diag_desc}");
	    	}
	        //患者HisId
	        if(out.getPatient_HisId()!=null){
	    		sql.SET("patient_HisId = #{patient_HisId}");
	    	}
	        //门诊卡号
	        if(out.getCard_no()!=null){
	    		sql.SET("card_no = #{card_no}");
	    	}
	        //门诊科室ID
	        if(out.getVisit_dept()!=null){
	    		sql.SET("visit_dept = #{visit_dept}");
	    	}
	        //门诊医师ID
	        if(out.getDoctor_no()!=null){
	    		sql.SET("doctor_no = #{doctor_no}");
	    	}
	        //错误原因
	        if(out.getErrorMsg()!=null){
	    		sql.SET("errorMsg = #{errorMsg}");
	    	}
	        //就诊序号
	        if(out.getVisit_no()!=null){
	    		sql.SET("visit_no = #{visit_no}");
	    	}
	        //号别
	        if(out.getClinic_label()!=null){
	    		sql.SET("clinic_label = #{clinic_label}");
	    	}
			sql.SET("updateTime = #{updateTime}");
			sql.SET("isValid = #{isValid}");
			/*
    		 * V2.1门诊新增
    		 */
			sql.SET("status = #{status}");
			//身份证号码
			if(ValidateUtil.isNotNull(out.getCardnum())){
				sql.SET("cardnum = #{cardnum}");
			}
			//管理类型  1.正常排期管理2.感染病管理3.未知（例：慢性病管理  例：风险性管理等）
			if(out.getManagertype()!=null){
				sql.SET("managertype = #{managertype}");
			}
			//病种表ID
			if(out.getChronicDiseaseId()!=null){
				sql.SET("chronicDiseaseId = #{chronicDiseaseId}");
			}
			//确诊情况 1.未确诊2.疑似3.确诊
			if(out.getConfiredCases()!=null){
				sql.SET("confiredCases = #{confiredCases}");
			}
	    	sql.WHERE("id=#{id}");
		return sql.toString();
	}
}
