package cn.sqwsy.health365interface.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.dao.entity.SInhospitalEntity;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

public class InhospitalSqlFactory {
	public String getInhospital(Map<String, Object> para){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("s_inhospital");
        if(!para.get("patientHisId").equals("")){
        	sql.WHERE("patientHisId='"+para.get("patientHisId")+"'");
        }
        if(!para.get("inhospitalcount").equals("")){
        	sql.WHERE("inhospitalcount="+para.get("inhospitalcount"));
        }
        if(!para.get("visitnum").equals("")){
        	sql.WHERE("visitnum="+para.get("visitnum"));
        }
        return sql.toString();
	}
	
	public String setInhospital(SInhospitalEntity inhospital){
		 SQL sql = new SQL();
	        sql.INSERT_INTO("s_inhospital");
	        
	        /**当前住院科室ID*/
	    	if(inhospital.getInhospitaldepartmentid()!=null){
	    		sql.VALUES("inhospitaldepartmentid", "#{inhospitaldepartmentid}");
	    	}
	    	
	    	/**住院日期*/
	    	if(inhospital.getInhospitaldate()!=null){
	    		sql.VALUES("inhospitaldate", "#{inhospitaldate}");
	    	}
	    	
	    	/**主治医生*/
	    	if(inhospital.getMaindoctorid()!=null){
	    		sql.VALUES("maindoctorid", "#{maindoctorid}");
	    	}
	    	
	    	/**住院号*/
	    	if(ValidateUtil.isNotNull(inhospital.getInhospitalid())){ 
	    		sql.VALUES("inhospitalid", "#{inhospitalid}");
	    	}
	    	
	    	/**住院次数**/
	    	if(inhospital.getInhospitalcount()!=null){
	    		sql.VALUES("inhospitalcount", "#{inhospitalcount}");
	    	}
	    	
	    	/**入院途径*/
	    	if(ValidateUtil.isNotNull(inhospital.getInhospitalway())){
	    		sql.VALUES("inhospitalway", "#{inhospitalway}");
	    	}
	    	
	    	/**入院情况*/
	    	if(ValidateUtil.isNotNull(inhospital.getInhospitaldescription())){ 
	    		sql.VALUES("inhospitaldescription", "#{inhospitaldescription}");
	    	}
	    	
	    	/**入院诊断*/
	    	if(ValidateUtil.isNotNull(inhospital.getInhospitaldiagnosis())){
	    		sql.VALUES("inhospitaldiagnosis", "#{inhospitaldiagnosis}");
	    	}
	    	
	    	/**入院诊断代码*/
	    	if(ValidateUtil.isNotNull(inhospital.getInhospitaldiagnosiscode())){
	    		sql.VALUES("inhospitaldiagnosiscode", "#{inhospitaldiagnosiscode}");
	    	}
	    	
	    	/**有无药物过敏*/
	    	if(ValidateUtil.isNotNull(inhospital.getDrugallergy())){
	    		sql.VALUES("drugallergy", "#{drugallergy}");
	    	}
	    	
	    	/**药物过敏史*/
	    	if(ValidateUtil.isNotNull(inhospital.getAllergydrug())){
	    		sql.VALUES("allergydrug", "#{allergydrug}");
	    	}
	    	
	    	/**就诊号*/
	    	if(ValidateUtil.isNotNull(inhospital.getVisitnum())){ 
	    		sql.VALUES("visitnum", "#{visitnum}");
	    	}
	    	
	    	/**门诊医生*/
	    	if(inhospital.getDoordoctorid()!=null){ 
	    		sql.VALUES("doordoctorid", "#{doordoctorid}");
	    	}
	    	
	    	/**createtime*/
	    	if(inhospital.getCreatetime()==null){
	    		sql.VALUES("createtime", "now()");
	    	}
	    	
	    	/**updatetime*/
	    	if(inhospital.getUpdatetime()!=null){
	    		sql.VALUES("updatetime", "now()");
	    	}
	    	
	    	/**patientid*/
	    	if(inhospital.getsPatientEntity()!=null){ 
	    		sql.VALUES("patientid", "#{sPatientEntity.id}");
	    	}
	    	
	    	/**orgid*/
	    	if(inhospital.getOrgid()!=null){
	    		sql.VALUES("orgid", "#{orgid}");
	    	}
	    	
	    	/**记录状态*/
	    	if(inhospital.getRecordstate()!=null){ 
	    		sql.VALUES("recordstate", "#{recordstate}");
	    	}
	    	
	    	/**住院医嘱*/
	    	if(ValidateUtil.isNotNull(inhospital.getHospitalizationadvice())){
	    		sql.VALUES("hospitalizationadvice", "#{hospitalizationadvice}");
	    	}
	    	
	    	/**管理计划状态*/
	    	if(inhospital.getDiseaseplanningstatus()!=null){
	    		sql.VALUES("diseaseplanningstatus", "#{diseaseplanningstatus}");
	    	}
	    	
	    	/**病区*/
	    	if(ValidateUtil.isNotNull(inhospital.getInpatientarea())){
	    		sql.VALUES("inpatientarea", "#{inpatientarea}");
	    	}
	    	
	    	/**病房号*/
	    	if(ValidateUtil.isNotNull(inhospital.getInpatientward())){
	    		sql.VALUES("inpatientward", "#{inpatientward}");
	    	}
	    	
	    	/**病床号*/
	    	if(ValidateUtil.isNotNull(inhospital.getHospitalbed())){
	    		sql.VALUES("hospitalbed", "#{hospitalbed}");
	    	}
	    	
	    	/**出院日期*/
	    	if(inhospital.getOuthospitaldate()!=null){
	    		sql.VALUES("outhospitaldate", "#{outhospitaldate}");
	    	}
	    	
	    	/**费用类型*/
	    	if(ValidateUtil.isNotNull(inhospital.getCosttype())){
	    		sql.VALUES("costtype", "#{costtype}");
	    	}
	    	
	    	/**医疗费用支付方式*/
	    	if(ValidateUtil.isNotNull(inhospital.getPaymentmethod())){ 
	    		sql.VALUES("paymentmethod", "#{paymentmethod}");
	    	}
	    	
	    	if(ValidateUtil.isNotNull(inhospital.getRelationexpect())){
	    		sql.VALUES("relationexpect", "#{relationexpect}");
	    	}//家属期望值（高，中，低）
	    	
	    	if(ValidateUtil.isNotNull(inhospital.getPatientpexpect())){
	    		sql.VALUES("patientpexpect", "#{patientpexpect}");
	    	}//患者期望值（高，中，低）
	    	
	    	if(inhospital.getCompletepre()!=null){ 
	    		sql.VALUES("completepre", "#{completepre}");
	    	}//院中管理总完成百分比，如：80（代表80%）
	    	if(ValidateUtil.isNotNull(inhospital.getInhospitaldepartment())){ 
	    		sql.VALUES("inhospitaldepartment", "#{inhospitaldepartment}");
	    	}//住院科室名称      //展示
	    	if(ValidateUtil.isNotNull(inhospital.getDoordoctor())){ 
	    		sql.VALUES("doordoctor", "#{doordoctor}");
	    	}//门诊医生名称			 //展示
	    	if(ValidateUtil.isNotNull(inhospital.getMaindoctor())){
	    		sql.VALUES("maindoctor", "#{maindoctor}");
	    	}//主治医生名称                               //展示
	    	if(inhospital.getResponsibleNurseId()!=null){
	    		sql.VALUES("responsibleNurseId", "#{responsibleNurseId}");
	    	}//责任护士Id
	    	if(ValidateUtil.isNotNull(inhospital.getKeywords())){
	    		sql.VALUES("keywords", "#{keywords}");
	    	}//关键词^代表关键词
	    	if(ValidateUtil.isNotNull(inhospital.getOuthospitaldescription())){ 
	    		sql.VALUES("outhospitaldescription", "#{outhospitaldescription}");
	    	}//出院情况

	    	/*
	    	 *修改字段(栗)
	    	 */
	    	//<---------------------------------->
	    	if(inhospital.getInhospitalstatus()!=null){
	    		sql.VALUES("inhospitalstatus", "#{inhospitalstatus}");
	    	}//住院状态1.住院2.出院
	    	if(inhospital.getSchedulingstate()!=null){
	    		sql.VALUES("schedulingstate", "#{schedulingstate}");
	    		
	    	}//排期状态1.未排期2.已排期3.勿访4.24小时内出院等5.排期过期(24小时前医生未排期的直接设置成排期过期)
	    	//<---------------------------------->
	    	/*
	    	 * 增加字段(栗)
	    	 */
	    	//<---------------------------------->
	    	if(ValidateUtil.isNotNull(inhospital.getDonotvisitthecause())){ 
	    		sql.VALUES("donotvisitthecause", "#{donotvisitthecause}");
	    	}//勿訪原因
	    	if(inhospital.getManagestate()!=null){ 
	    		sql.VALUES("managestate", "#{managestate}");
	    	}//管理状态 0未标记1未完成 2已完成
	    	if(inhospital.getOuthospitaldepartmentid()!=null){
	    		sql.VALUES("outhospitaldepartmentid", "#{outhospitaldepartmentid}");
	    	}//出院科室ID
	    	if(ValidateUtil.isNotNull(inhospital.getLongtermadvise())){
	    		sql.VALUES("longtermadvise", "#{longtermadvise}");
	    	}//长期医嘱
	    	if(ValidateUtil.isNotNull(inhospital.getOuthospitaladvise())){
	    		sql.VALUES("outhospitaladvise", "#{outhospitaladvise}");
	    	}//出院医嘱
	    	if(ValidateUtil.isNotNull(inhospital.getOuthospitaldiagnose())){
	    		sql.VALUES("outhospitaldiagnose", "#{outhospitaldiagnose}");
	    	}//出院诊断
	    	if(inhospital.getOuthospitaldateclose()!=null){
	    		sql.VALUES("outhospitaldateclose", "#{outhospitaldateclose}");
	    	}//出院结算时间
	    	if(inhospital.getInhospitaldays()!=null){ 
	    		sql.VALUES("inhospitaldays", "#{inhospitaldays}");
	    	}//住院天数
	    	
	    	if(inhospital.getTotalcost()!=null){//总花费
	    		sql.VALUES("totalcost", "#{totalcost}");
	    	}

	    	if(ValidateUtil.isNotNull(inhospital.getRh())){
	    		sql.VALUES("rh", "#{rh}");
	    	}//RH
	    	
	    	if(ValidateUtil.isNotNull(inhospital.getOuthospitaltype())){
	    		sql.VALUES("outhospitaltype", "#{outhospitaltype}");
	    	}//离院方式(医嘱离院,医嘱转院,医嘱转社区卫生服务机构/乡镇卫生院,非医嘱离院,死亡,其他)
	    	if(ValidateUtil.isNotNull(inhospital.getPathologydiagnosename())){
	    		sql.VALUES("pathologydiagnosename", "#{pathologydiagnosename}");
	    	}//病理诊断名称
	    	if(ValidateUtil.isNotNull(inhospital.getPathologydiagnosecode())){
	    		sql.VALUES("pathologydiagnosecode", "#{pathologydiagnosecode}");
	    	}//病理诊断码
	    	//<---------------------------------->
	    	//展示字段
	    	//<---------------------------------->
	    	if(ValidateUtil.isNotNull(inhospital.getOuthospitaldepartmentname())){ 
	    		sql.VALUES("outhospitaldepartmentname", "#{outhospitaldepartmentname}");
	    	}//出院科室名称   
	    	//private List<SManagerGuidanceEntity> SManagerGuidancelist()){ }
	    	//<---------------------------------->
	    	
	    	if(inhospital.getInDiseaseManagerId()!=null){ 
	    		sql.VALUES("inDiseaseManagerId", "#{inDiseaseManagerId}");
	    	}//院中疾病管理师Id
	    	
	    	if(inhospital.getOutDiseaseManagerId()!=null){
	    		sql.VALUES("outDiseaseManagerId", "#{outDiseaseManagerId}");
	    	}//院后疾病管理师Id
	    	
	    	if(ValidateUtil.isNotNull(inhospital.getPatientHisId())){ 
	    		sql.VALUES("patientHisId", "#{patientHisId}");
	    	}//HIS患者ID
	    	
	    	if(inhospital.getIsValid()!=null){
	    		sql.VALUES("isValid", "#{isValid}");
	    	}//是否有效 (1是有效0是无效)
	    	
	    	if(ValidateUtil.isNotNull(inhospital.getErrorMsg())){
	    		sql.VALUES("errorMsg", "#{errorMsg}");
	    	}//错误原因
	        return sql.toString();
	}
	
	public String updateInhospital(SInhospitalEntity inhospital){
		SQL sql = new SQL();
		sql.UPDATE("s_inhospital");
		 	/**当前住院科室ID*/
			 if (inhospital.getInhospitaldepartmentid()!=null) {
	             sql.SET("inhospitaldepartmentid = #{inhospitaldepartmentid}");
	         }
		
	    	/**住院日期*/
	    	if(inhospital.getInhospitaldate()!=null){
	    		sql.SET("inhospitaldate= #{inhospitaldate}");
	    	}
	    	
	    	/**主治医生*/
	    	if(inhospital.getMaindoctorid()!=null){
	    		sql.SET("maindoctorid= #{maindoctorid}");
	    	}
	    	
	    	/**住院号*/
	    	if(ValidateUtil.isNotNull(inhospital.getInhospitalid())){ 
	    		sql.SET("inhospitalid= #{inhospitalid}");
	    	}
	    	
	    	/**住院次数**/
	    	if(inhospital.getInhospitalcount()!=null){
	    		sql.SET("inhospitalcount= #{inhospitalcount}");
	    	}
	    	
	    	/**入院途径*/
	    	if(ValidateUtil.isNotNull(inhospital.getInhospitalway())){
	    		sql.SET("inhospitalway= #{inhospitalway}");
	    	}
	    	
	    	/**入院情况*/
	    	if(ValidateUtil.isNotNull(inhospital.getInhospitaldescription())){ 
	    		sql.SET("inhospitaldescription= #{inhospitaldescription}");
	    	}
	    	
	    	/**入院诊断*/
	    	if(ValidateUtil.isNotNull(inhospital.getInhospitaldiagnosis())){
	    		sql.SET("inhospitaldiagnosis= #{inhospitaldiagnosis}");
	    	}
	    	
	    	/**入院诊断代码*/
	    	if(ValidateUtil.isNotNull(inhospital.getInhospitaldiagnosiscode())){
	    		sql.SET("inhospitaldiagnosiscode= #{inhospitaldiagnosiscode}");
	    	}
	    	
	    	/**有无药物过敏*/
	    	if(ValidateUtil.isNotNull(inhospital.getDrugallergy())){
	    		sql.SET("drugallergy= #{drugallergy}");
	    	}
	    	
	    	/**药物过敏史*/
	    	if(ValidateUtil.isNotNull(inhospital.getAllergydrug())){
	    		sql.SET("allergydrug= #{allergydrug}");
	    	}
	    	
	    	/**就诊号*/
	    	if(ValidateUtil.isNotNull(inhospital.getVisitnum())){ 
	    		sql.SET("visitnum= #{visitnum}");
	    	}
	    	
	    	/**门诊医生*/
	    	if(inhospital.getDoordoctorid()!=null){ 
	    		sql.SET("doordoctorid= #{doordoctorid}");
	    	}
	    	
	    	/**updatetime*/
	    	sql.SET("updatetime=now()");
	    	
	    	/**patientid*/
	    	if(inhospital.getsPatientEntity()!=null){ 
	    		sql.SET("patientid= #{sPatientEntity.id}");
	    	}
	    	
	    	/**记录状态*/
	    	if(inhospital.getRecordstate()!=null){ 
	    		sql.SET("recordstate= #{recordstate}");
	    	}
	    	
	    	/**住院医嘱*/
	    	if(ValidateUtil.isNotNull(inhospital.getHospitalizationadvice())){
	    		sql.SET("hospitalizationadvice= #{hospitalizationadvice}");
	    	}
	    	
	    	/**管理计划状态*/
	    	if(inhospital.getDiseaseplanningstatus()!=null){
	    		sql.SET("diseaseplanningstatus= #{diseaseplanningstatus}");
	    	}
	    	
	    	/**病区*/
	    	if(ValidateUtil.isNotNull(inhospital.getInpatientarea())){
	    		sql.SET("inpatientarea= #{inpatientarea}");
	    	}
	    	
	    	/**病房号*/
	    	if(ValidateUtil.isNotNull(inhospital.getInpatientward())){
	    		sql.SET("inpatientward= #{inpatientward}");
	    	}
	    	
	    	/**病床号*/
	    	if(ValidateUtil.isNotNull(inhospital.getHospitalbed())){
	    		sql.SET("hospitalbed= #{hospitalbed}");
	    	}
	    	
	    	/**出院日期*/
	    	if(inhospital.getOuthospitaldate()!=null){
	    		sql.SET("outhospitaldate= #{outhospitaldate}");
	    	}
	    	
	    	/**费用类型*/
	    	if(ValidateUtil.isNotNull(inhospital.getCosttype())){
	    		sql.SET("costtype= #{costtype}");
	    	}
	    	
	    	/**医疗费用支付方式*/
	    	if(ValidateUtil.isNotNull(inhospital.getPaymentmethod())){ 
	    		sql.SET("paymentmethod= #{paymentmethod}");
	    	}
	    	
	    	if(ValidateUtil.isNotNull(inhospital.getRelationexpect())){
	    		sql.SET("relationexpect= #{relationexpect}");
	    	}//家属期望值（高，中，低）
	    	
	    	if(ValidateUtil.isNotNull(inhospital.getPatientpexpect())){
	    		sql.SET("patientpexpect= #{patientpexpect}");
	    	}//患者期望值（高，中，低）
	    	
	    	if(inhospital.getCompletepre()!=null){ 
	    		sql.SET("completepre= #{completepre}");
	    	}//院中管理总完成百分比，如：80（代表80%）
	    	if(ValidateUtil.isNotNull(inhospital.getInhospitaldepartment())){ 
	    		sql.SET("inhospitaldepartment= #{inhospitaldepartment}");
	    	}//住院科室名称      //展示
	    	if(ValidateUtil.isNotNull(inhospital.getDoordoctor())){ 
	    		sql.SET("doordoctor= #{doordoctor}");
	    	}//门诊医生名称			 //展示
	    	if(ValidateUtil.isNotNull(inhospital.getMaindoctor())){
	    		sql.SET("maindoctor= #{maindoctor}");
	    	}//主治医生名称                               //展示
	    	if(inhospital.getResponsibleNurseId()!=null){
	    		sql.SET("responsibleNurseId= #{responsibleNurseId}");
	    	}//责任护士Id
	    	if(ValidateUtil.isNotNull(inhospital.getKeywords())){
	    		sql.SET("keywords= #{keywords}");
	    	}//关键词^代表关键词
	    	if(ValidateUtil.isNotNull(inhospital.getOuthospitaldescription())){ 
	    		sql.SET("outhospitaldescription= #{outhospitaldescription}");
	    	}//出院情况

	    	/*
	    	 *修改字段(栗)
	    	 */
	    	//<---------------------------------->
	    	if(inhospital.getInhospitalstatus()!=null){
	    		sql.SET("inhospitalstatus= #{inhospitalstatus}");
	    	}//住院状态1.住院2.出院
	    	if(inhospital.getSchedulingstate()!=null){
	    		sql.SET("schedulingstate= #{schedulingstate}");
	    		
	    	}//排期状态1.未排期2.已排期3.勿访4.24小时内出院等5.排期过期(24小时前医生未排期的直接设置成排期过期)
	    	//<---------------------------------->
	    	/*
	    	 * 增加字段(栗)
	    	 */
	    	//<---------------------------------->
	    	if(ValidateUtil.isNotNull(inhospital.getDonotvisitthecause())){ 
	    		sql.SET("donotvisitthecause= #{donotvisitthecause}");
	    	}//勿訪原因
	    	if(inhospital.getManagestate()!=null){ 
	    		sql.SET("managestate= #{managestate}");
	    	}//管理状态 0未标记1未完成 2已完成
	    	if(inhospital.getOuthospitaldepartmentid()!=null){
	    		sql.SET("outhospitaldepartmentid= #{outhospitaldepartmentid}");
	    	}//出院科室ID
	    	if(ValidateUtil.isNotNull(inhospital.getLongtermadvise())){
	    		sql.SET("longtermadvise= #{longtermadvise}");
	    	}//长期医嘱
	    	if(ValidateUtil.isNotNull(inhospital.getOuthospitaladvise())){
	    		sql.SET("outhospitaladvise= #{outhospitaladvise}");
	    	}//出院医嘱
	    	if(ValidateUtil.isNotNull(inhospital.getOuthospitaldiagnose())){
	    		sql.SET("outhospitaldiagnose= #{outhospitaldiagnose}");
	    	}//出院诊断
	    	if(inhospital.getOuthospitaldateclose()!=null){
	    		sql.SET("outhospitaldateclose= #{outhospitaldateclose}");
	    	}//出院结算时间
	    	if(inhospital.getInhospitaldays()!=null){ 
	    		sql.SET("inhospitaldays= #{inhospitaldays}");
	    	}//住院天数
	    	
	    	if(inhospital.getTotalcost()!=null){//总花费
	    		sql.SET("totalcost= #{totalcost}");
	    	}

	    	if(ValidateUtil.isNotNull(inhospital.getRh())){
	    		sql.SET("rh= #{rh}");
	    	}//RH
	    	
	    	if(ValidateUtil.isNotNull(inhospital.getOuthospitaltype())){
	    		sql.SET("outhospitaltype= #{outhospitaltype}");
	    	}//离院方式(医嘱离院,医嘱转院,医嘱转社区卫生服务机构/乡镇卫生院,非医嘱离院,死亡,其他)
	    	if(ValidateUtil.isNotNull(inhospital.getPathologydiagnosename())){
	    		sql.SET("pathologydiagnosename= #{pathologydiagnosename}");
	    	}//病理诊断名称
	    	if(ValidateUtil.isNotNull(inhospital.getPathologydiagnosecode())){
	    		sql.SET("pathologydiagnosecode= #{pathologydiagnosecode}");
	    	}//病理诊断码
	    	//<---------------------------------->
	    	//展示字段
	    	//<---------------------------------->
	    	if(ValidateUtil.isNotNull(inhospital.getOuthospitaldepartmentname())){ 
	    		sql.SET("outhospitaldepartmentname= #{outhospitaldepartmentname}");
	    	}//出院科室名称   
	    	//private List<SManagerGuidanceEntity> SManagerGuidancelist()){ }
	    	//<---------------------------------->
	    	
	    	if(inhospital.getInDiseaseManagerId()!=null){ 
	    		sql.SET("inDiseaseManagerId= #{inDiseaseManagerId}");
	    	}//院中疾病管理师Id
	    	
	    	if(inhospital.getOutDiseaseManagerId()!=null){
	    		sql.SET("outDiseaseManagerId= #{outDiseaseManagerId}");
	    	}//院后疾病管理师Id
	    	
	    	if(ValidateUtil.isNotNull(inhospital.getPatientHisId())){ 
	    		sql.SET("patientHisId= #{patientHisId}");
	    	}//HIS患者ID
	    	
	    	if(inhospital.getIsValid()!=null){
	    		sql.SET("isValid= #{isValid}");
	    	}//是否有效 (1是有效0是无效)
	    	
	    	if(ValidateUtil.isNotNull(inhospital.getErrorMsg())){
	    		sql.SET("errorMsg= #{errorMsg}");
	    	}//错误原因
	    	sql.WHERE("id=#{id}");
		return sql.toString();
	}
}
