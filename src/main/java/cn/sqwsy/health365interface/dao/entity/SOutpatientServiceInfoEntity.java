package cn.sqwsy.health365interface.dao.entity;

import java.sql.Timestamp;

/**
 * 门诊
 * @author WangSongYuan
 *
 */
public class SOutpatientServiceInfoEntity extends PO{

	private Integer id;//Id
	private SPatientEntity patient;//患者Id
	private SDepartmentEntity department;//科室Id
	private SUserEntity doctorinfo;//用户ID(也是门诊医生信息数据)
	private SUserEntity userinfo;//操作用户ID(本条数据操作人)
	private String cardnum;//身份证号
	private Timestamp visit_date;//门诊日期
	private String costtype;//费用类型
	private String costs;//总花费
	private String diag_desc;//门诊诊断
	private String diag_code;//门诊诊断编码
	private String patient_HisId;//患者HisId
	private String card_no;//门诊卡号                   
	private String visit_dept;//门诊科室Id(第三方)HIS
	private String doctor_no;//门诊医师Id(第三方)HIS
	private Integer isValid=0;//是否有效 (1是有效0是无效)
	private Integer schedulingState=1;//排期状态1.未排期2.已排期3.勿访4.24小时内出院等5.排期过期6.无需排期(24小时前医生未排期的直接设置成排期过期)
	private String donotvisitthecause_mz;//勿訪原因
	private String errorMsg;//错误原因
	private String visit_no;//就诊序号
	private String clinic_label;//号别
	private Integer orgid;//组织ID
	private Integer status=0;//门诊记录状态(0.未知1.就诊 2.结账 3.退号等[也许每家医院不一样])
	private Integer managertype=1;//管理类型  1.正常排期管理2.感染病管理3.未知（例：慢性病管理  例：风险性管理等）
	private Integer chronicDiseaseId;//病种表ID
	private Integer confiredCases;//确诊情况 1.未确诊2.疑似3.确诊
	private Timestamp createTime =  new Timestamp(System.currentTimeMillis());//门诊记录创建时间
	private Timestamp updateTime =  new Timestamp(System.currentTimeMillis());//门诊记录更新时间
	private String mz_code;//新增数据唯一标识
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public SPatientEntity getPatient() {
		return patient;
	}
	public void setPatient(SPatientEntity patient) {
		this.patient = patient;
	}
	public SDepartmentEntity getDepartment() {
		return department;
	}
	public void setDepartment(SDepartmentEntity department) {
		this.department = department;
	}
	public SUserEntity getDoctorinfo() {
		return doctorinfo;
	}
	public void setDoctorinfo(SUserEntity doctorinfo) {
		this.doctorinfo = doctorinfo;
	}
	public SUserEntity getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(SUserEntity userinfo) {
		this.userinfo = userinfo;
	}
	public Timestamp getVisit_date() {
		return visit_date;
	}
	public void setVisit_date(Timestamp visit_date) {
		this.visit_date = visit_date;
	}
	public String getCosts() {
		return costs;
	}
	public void setCosts(String costs) {
		this.costs = costs;
	}
	public String getDiag_desc() {
		return diag_desc;
	}
	public void setDiag_desc(String diag_desc) {
		this.diag_desc = diag_desc;
	}
	public String getPatient_HisId() {
		return patient_HisId;
	}
	public void setPatient_HisId(String patient_HisId) {
		this.patient_HisId = patient_HisId;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getVisit_dept() {
		return visit_dept;
	}
	public void setVisit_dept(String visit_dept) {
		this.visit_dept = visit_dept;
	}
	public String getDoctor_no() {
		return doctor_no;
	}
	public void setDoctor_no(String doctor_no) {
		this.doctor_no = doctor_no;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public Integer getSchedulingState() {
		return schedulingState;
	}
	public void setSchedulingState(Integer schedulingState) {
		this.schedulingState = schedulingState;
	}
	public String getDonotvisitthecause_mz() {
		return donotvisitthecause_mz;
	}
	public void setDonotvisitthecause_mz(String donotvisitthecause_mz) {
		this.donotvisitthecause_mz = donotvisitthecause_mz;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getVisit_no() {
		return visit_no;
	}
	public void setVisit_no(String visit_no) {
		this.visit_no = visit_no;
	}
	public String getClinic_label() {
		return clinic_label;
	}
	public void setClinic_label(String clinic_label) {
		this.clinic_label = clinic_label;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getCosttype() {
		return costtype;
	}
	public void setCosttype(String costtype) {
		this.costtype = costtype;
	}
	public String getDiag_code() {
		return diag_code;
	}
	public void setDiag_code(String diag_code) {
		this.diag_code = diag_code;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCardnum() {
		return cardnum;
	}
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}
	public Integer getOrgid() {
		return orgid;
	}
	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
	public Integer getManagertype() {
		return managertype;
	}
	public void setManagertype(Integer managertype) {
		this.managertype = managertype;
	}
	public Integer getChronicDiseaseId() {
		return chronicDiseaseId;
	}
	public void setChronicDiseaseId(Integer chronicDiseaseId) {
		this.chronicDiseaseId = chronicDiseaseId;
	}
	public Integer getConfiredCases() {
		return confiredCases;
	}
	public void setConfiredCases(Integer confiredCases) {
		this.confiredCases = confiredCases;
	}
	public String getMz_code() {
		return mz_code;
	}
	public void setMz_code(String mz_code) {
		this.mz_code = mz_code;
	}
}