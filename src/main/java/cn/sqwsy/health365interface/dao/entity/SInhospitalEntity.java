package cn.sqwsy.health365interface.dao.entity;

import java.sql.Timestamp;
import java.util.List;

public class SInhospitalEntity extends PO {
	/**id*/
	private java.lang.Integer id;
	
	/**当前住院科室ID*/
	private java.lang.Integer inhospitaldepartmentid;
	
	/**住院日期*/
	private java.sql.Timestamp inhospitaldate;
	
	/**主治医生*/
	private java.lang.Integer maindoctorid;
	
	/**住院号*/
	private java.lang.String inhospitalid;
	
	/**住院次数**/
	private java.lang.Integer inhospitalcount;
	
	/**入院途径*/
	private java.lang.String inhospitalway;
	
	/**入院情况*/
	private java.lang.String inhospitaldescription;
	
	/**入院诊断*/
	private java.lang.String inhospitaldiagnosis;
	
	/**入院诊断代码*/
	private java.lang.String inhospitaldiagnosiscode;
	
	/**有无药物过敏*/
	private java.lang.String drugallergy;
	
	/**药物过敏史*/
	private java.lang.String allergydrug;
	
	/**就诊号*/
	private java.lang.String visitnum;
	
	/**门诊医生*/
	private java.lang.Integer doordoctorid;
	
	/**createtime*/
	private java.sql.Timestamp createtime;
	
	/**updatetime*/
	private java.sql.Timestamp updatetime;
	
	/**patientid*/
	private SPatientEntity sPatientEntity;
	
	/**orgid*/
	private java.lang.Integer orgid;
	
	/**记录状态*/
	private java.lang.Integer recordstate;
	
	/**住院医嘱*/
	private java.lang.String hospitalizationadvice;
	
	/**管理计划状态*/
	private java.lang.Integer diseaseplanningstatus;
	
	/**病区*/
	private java.lang.String inpatientarea;
	
	/**病房号*/
	private java.lang.String inpatientward;
	
	/**病床号*/
	private java.lang.String hospitalbed;
	
	/**出院日期*/
	private java.sql.Timestamp outhospitaldate;
	
	/**费用类型*/
	private java.lang.String costtype;
	
	/**医疗费用支付方式*/
	private java.lang.String paymentmethod;
	
	private String relationexpect;//家属期望值（高，中，低）
	
	private String patientpexpect;//患者期望值（高，中，低）
	
	private Integer completepre;//院中管理总完成百分比，如：80（代表80%）
	private String inhospitaldepartment;//住院科室名称      //展示
	private String doordoctor;//门诊医生名称			 //展示
	private String maindoctor;//主治医生名称                               //展示
	
	private Integer responsibleNurseId;//责任护士Id
	private String keywords;//关键词^代表关键词
	private java.lang.String outhospitaldescription;//出院情况

	/*
	 *修改字段(栗)
	 */
	//<---------------------------------->
	private Integer inhospitalstatus=1;//住院状态1.住院2.出院
	private Integer schedulingstate;//排期状态1.未排期2.已排期3.勿访4.24小时内出院等5.排期过期(24小时前医生未排期的直接设置成排期过期)
	//<---------------------------------->
	/*
	 * 增加字段(栗)
	 */
	//<---------------------------------->
	private String donotvisitthecause;//勿訪原因
	private Integer managestate=0;//管理状态 0未标记1未完成 2已完成
	private Integer outhospitaldepartmentid;//出院科室ID
	private String longtermadvise;//长期医嘱
	private String outhospitaladvise;//出院医嘱
	private String outhospitaldiagnose;//出院诊断
	private Timestamp outhospitaldateclose;//出院结算时间
	private Integer inhospitaldays;//住院天数
	private Double totalcost;//总花费
	private String rh;//RH
	private String outhospitaltype;//离院方式(医嘱离院,医嘱转院,医嘱转社区卫生服务机构/乡镇卫生院,非医嘱离院,死亡,其他)
	private String pathologydiagnosename;//病理诊断名称
	private String pathologydiagnosecode;//病理诊断码
	//<---------------------------------->
	//展示字段
	//<---------------------------------->
	private String outhospitaldepartmentname;//出院科室名称   
	private List<SManagerGuidanceEntity> SManagerGuidancelist;
	//<---------------------------------->
	
	private Integer inDiseaseManagerId;//院中疾病管理师Id
	
	private Integer outDiseaseManagerId;//院后疾病管理师Id
	
	private String patientHisId;//HIS患者ID
	
	private Integer isValid=0;//是否有效 (1是有效0是无效)
	
	private String errorMsg;//错误原因
	
	private String outhospitaldiagnoseicd;//出院诊断ICD
	
	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getInhospitaldepartmentid() {
		return inhospitaldepartmentid;
	}

	public void setInhospitaldepartmentid(java.lang.Integer inhospitaldepartmentid) {
		this.inhospitaldepartmentid = inhospitaldepartmentid;
	}

	public java.sql.Timestamp getInhospitaldate() {
		return inhospitaldate;
	}

	public void setInhospitaldate(java.sql.Timestamp inhospitaldate) {
		this.inhospitaldate = inhospitaldate;
	}

	public java.lang.Integer getMaindoctorid() {
		return maindoctorid;
	}

	public void setMaindoctorid(java.lang.Integer maindoctorid) {
		this.maindoctorid = maindoctorid;
	}

	public java.lang.String getInhospitalid() {
		return inhospitalid;
	}

	public void setInhospitalid(java.lang.String inhospitalid) {
		this.inhospitalid = inhospitalid;
	}

	public java.lang.Integer getInhospitalcount() {
		return inhospitalcount;
	}

	public void setInhospitalcount(java.lang.Integer inhospitalcount) {
		this.inhospitalcount = inhospitalcount;
	}

	public java.lang.String getInhospitalway() {
		return inhospitalway;
	}

	public void setInhospitalway(java.lang.String inhospitalway) {
		this.inhospitalway = inhospitalway;
	}

	public java.lang.String getInhospitaldescription() {
		return inhospitaldescription;
	}

	public void setInhospitaldescription(java.lang.String inhospitaldescription) {
		this.inhospitaldescription = inhospitaldescription;
	}

	public java.lang.String getInhospitaldiagnosis() {
		return inhospitaldiagnosis;
	}

	public void setInhospitaldiagnosis(java.lang.String inhospitaldiagnosis) {
		this.inhospitaldiagnosis = inhospitaldiagnosis;
	}

	public java.lang.String getInhospitaldiagnosiscode() {
		return inhospitaldiagnosiscode;
	}

	public void setInhospitaldiagnosiscode(java.lang.String inhospitaldiagnosiscode) {
		this.inhospitaldiagnosiscode = inhospitaldiagnosiscode;
	}

	public java.lang.String getDrugallergy() {
		return drugallergy;
	}

	public void setDrugallergy(java.lang.String drugallergy) {
		this.drugallergy = drugallergy;
	}

	public java.lang.String getAllergydrug() {
		return allergydrug;
	}

	public void setAllergydrug(java.lang.String allergydrug) {
		this.allergydrug = allergydrug;
	}

	public java.lang.String getVisitnum() {
		return visitnum;
	}

	public void setVisitnum(java.lang.String visitnum) {
		this.visitnum = visitnum;
	}

	public java.lang.Integer getDoordoctorid() {
		return doordoctorid;
	}

	public void setDoordoctorid(java.lang.Integer doordoctorid) {
		this.doordoctorid = doordoctorid;
	}

	public java.sql.Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.sql.Timestamp createtime) {
		this.createtime = createtime;
	}

	public java.sql.Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(java.sql.Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public SPatientEntity getsPatientEntity() {
		return sPatientEntity;
	}

	public void setsPatientEntity(SPatientEntity sPatientEntity) {
		this.sPatientEntity = sPatientEntity;
	}

	public java.lang.Integer getOrgid() {
		return orgid;
	}

	public void setOrgid(java.lang.Integer orgid) {
		this.orgid = orgid;
	}

	public java.lang.Integer getRecordstate() {
		return recordstate;
	}

	public void setRecordstate(java.lang.Integer recordstate) {
		this.recordstate = recordstate;
	}

	public java.lang.String getHospitalizationadvice() {
		return hospitalizationadvice;
	}

	public void setHospitalizationadvice(java.lang.String hospitalizationadvice) {
		this.hospitalizationadvice = hospitalizationadvice;
	}

	public java.lang.Integer getDiseaseplanningstatus() {
		return diseaseplanningstatus;
	}

	public void setDiseaseplanningstatus(java.lang.Integer diseaseplanningstatus) {
		this.diseaseplanningstatus = diseaseplanningstatus;
	}

	public java.lang.String getInpatientarea() {
		return inpatientarea;
	}

	public void setInpatientarea(java.lang.String inpatientarea) {
		this.inpatientarea = inpatientarea;
	}

	public java.lang.String getInpatientward() {
		return inpatientward;
	}

	public void setInpatientward(java.lang.String inpatientward) {
		this.inpatientward = inpatientward;
	}

	public java.lang.String getHospitalbed() {
		return hospitalbed;
	}

	public void setHospitalbed(java.lang.String hospitalbed) {
		this.hospitalbed = hospitalbed;
	}

	public java.sql.Timestamp getOuthospitaldate() {
		return outhospitaldate;
	}

	public void setOuthospitaldate(java.sql.Timestamp outhospitaldate) {
		this.outhospitaldate = outhospitaldate;
	}

	public java.lang.String getCosttype() {
		return costtype;
	}

	public void setCosttype(java.lang.String costtype) {
		this.costtype = costtype;
	}

	public java.lang.String getPaymentmethod() {
		return paymentmethod;
	}

	public void setPaymentmethod(java.lang.String paymentmethod) {
		this.paymentmethod = paymentmethod;
	}

	public String getRelationexpect() {
		return relationexpect;
	}

	public void setRelationexpect(String relationexpect) {
		this.relationexpect = relationexpect;
	}

	public String getPatientpexpect() {
		return patientpexpect;
	}

	public void setPatientpexpect(String patientpexpect) {
		this.patientpexpect = patientpexpect;
	}

	public Integer getCompletepre() {
		return completepre;
	}

	public void setCompletepre(Integer completepre) {
		this.completepre = completepre;
	}

	public String getInhospitaldepartment() {
		return inhospitaldepartment;
	}

	public void setInhospitaldepartment(String inhospitaldepartment) {
		this.inhospitaldepartment = inhospitaldepartment;
	}

	public String getDoordoctor() {
		return doordoctor;
	}

	public void setDoordoctor(String doordoctor) {
		this.doordoctor = doordoctor;
	}

	public String getMaindoctor() {
		return maindoctor;
	}

	public void setMaindoctor(String maindoctor) {
		this.maindoctor = maindoctor;
	}

	public Integer getResponsibleNurseId() {
		return responsibleNurseId;
	}

	public void setResponsibleNurseId(Integer responsibleNurseId) {
		this.responsibleNurseId = responsibleNurseId;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public java.lang.String getOuthospitaldescription() {
		return outhospitaldescription;
	}

	public void setOuthospitaldescription(java.lang.String outhospitaldescription) {
		this.outhospitaldescription = outhospitaldescription;
	}

	public Integer getInhospitalstatus() {
		return inhospitalstatus;
	}

	public void setInhospitalstatus(Integer inhospitalstatus) {
		this.inhospitalstatus = inhospitalstatus;
	}

	public Integer getSchedulingstate() {
		return schedulingstate;
	}

	public void setSchedulingstate(Integer schedulingstate) {
		this.schedulingstate = schedulingstate;
	}

	public String getDonotvisitthecause() {
		return donotvisitthecause;
	}

	public void setDonotvisitthecause(String donotvisitthecause) {
		this.donotvisitthecause = donotvisitthecause;
	}

	public Integer getManagestate() {
		return managestate;
	}

	public void setManagestate(Integer managestate) {
		this.managestate = managestate;
	}

	public Integer getOuthospitaldepartmentid() {
		return outhospitaldepartmentid;
	}

	public void setOuthospitaldepartmentid(Integer outhospitaldepartmentid) {
		this.outhospitaldepartmentid = outhospitaldepartmentid;
	}

	public String getLongtermadvise() {
		return longtermadvise;
	}

	public void setLongtermadvise(String longtermadvise) {
		this.longtermadvise = longtermadvise;
	}

	public String getOuthospitaladvise() {
		return outhospitaladvise;
	}

	public void setOuthospitaladvise(String outhospitaladvise) {
		this.outhospitaladvise = outhospitaladvise;
	}

	public String getOuthospitaldiagnose() {
		return outhospitaldiagnose;
	}

	public void setOuthospitaldiagnose(String outhospitaldiagnose) {
		this.outhospitaldiagnose = outhospitaldiagnose;
	}

	public Timestamp getOuthospitaldateclose() {
		return outhospitaldateclose;
	}

	public void setOuthospitaldateclose(Timestamp outhospitaldateclose) {
		this.outhospitaldateclose = outhospitaldateclose;
	}

	public Integer getInhospitaldays() {
		return inhospitaldays;
	}

	public void setInhospitaldays(Integer inhospitaldays) {
		this.inhospitaldays = inhospitaldays;
	}

	public Double getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(Double totalcost) {
		this.totalcost = totalcost;
	}

	public String getRh() {
		return rh;
	}

	public void setRh(String rh) {
		this.rh = rh;
	}

	public String getOuthospitaltype() {
		return outhospitaltype;
	}

	public void setOuthospitaltype(String outhospitaltype) {
		this.outhospitaltype = outhospitaltype;
	}

	public String getPathologydiagnosename() {
		return pathologydiagnosename;
	}

	public void setPathologydiagnosename(String pathologydiagnosename) {
		this.pathologydiagnosename = pathologydiagnosename;
	}

	public String getPathologydiagnosecode() {
		return pathologydiagnosecode;
	}

	public void setPathologydiagnosecode(String pathologydiagnosecode) {
		this.pathologydiagnosecode = pathologydiagnosecode;
	}

	public String getOuthospitaldepartmentname() {
		return outhospitaldepartmentname;
	}

	public void setOuthospitaldepartmentname(String outhospitaldepartmentname) {
		this.outhospitaldepartmentname = outhospitaldepartmentname;
	}

	public List<SManagerGuidanceEntity> getSManagerGuidancelist() {
		return SManagerGuidancelist;
	}

	public void setSManagerGuidancelist(List<SManagerGuidanceEntity> sManagerGuidancelist) {
		SManagerGuidancelist = sManagerGuidancelist;
	}

	public Integer getInDiseaseManagerId() {
		return inDiseaseManagerId;
	}

	public void setInDiseaseManagerId(Integer inDiseaseManagerId) {
		this.inDiseaseManagerId = inDiseaseManagerId;
	}

	public Integer getOutDiseaseManagerId() {
		return outDiseaseManagerId;
	}

	public void setOutDiseaseManagerId(Integer outDiseaseManagerId) {
		this.outDiseaseManagerId = outDiseaseManagerId;
	}

	public String getPatientHisId() {
		return patientHisId;
	}

	public void setPatientHisId(String patientHisId) {
		this.patientHisId = patientHisId;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getOuthospitaldiagnoseicd() {
		return outhospitaldiagnoseicd;
	}

	public void setOuthospitaldiagnoseicd(String outhospitaldiagnoseicd) {
		this.outhospitaldiagnoseicd = outhospitaldiagnoseicd;
	}
}
