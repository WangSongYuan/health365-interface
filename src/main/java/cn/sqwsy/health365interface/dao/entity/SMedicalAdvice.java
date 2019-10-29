package cn.sqwsy.health365interface.dao.entity;

import java.sql.Timestamp;

/**
 * 医嘱
 * @author wangsongyuan
 */
public class SMedicalAdvice extends PO{
	private Integer id;//ID
	private String drug_name;//医嘱名称
	private String drug_dosage;//剂量
	private String visit_date;//下达时间
	private String drug_unit;//单位
	private String drug_way;//用法
	private String drug_freq;//频率
	private Integer dept_Code;//科室No
	private String doctor_code;//医生id
	private String doctor_name;//医生名称
	private Timestamp execution_time;//执行时间
	private Timestamp over_time;//结束时间
	private String medication_purpose;//用药目的
	private String doctor_explanation;//医生说明
	private String stop_doctor;//停止医生ID
	private String stop_nurse;//停止护士id
	private Integer doctor_advice_type;//医嘱类型
	private Integer medical_advice_time;//医嘱时长
	private Integer type=0;//数据类型
	private Timestamp createTime =  new Timestamp(System.currentTimeMillis());//创建时间
	private Timestamp updateTime =  new Timestamp(System.currentTimeMillis());//更新时间
	private String datacode;//数据唯一标识
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDrug_name() {
		return drug_name;
	}
	public void setDrug_name(String drug_name) {
		this.drug_name = drug_name;
	}
	public String getDrug_dosage() {
		return drug_dosage;
	}
	public void setDrug_dosage(String drug_dosage) {
		this.drug_dosage = drug_dosage;
	}
	public String getVisit_date() {
		return visit_date;
	}
	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}
	public String getDrug_unit() {
		return drug_unit;
	}
	public void setDrug_unit(String drug_unit) {
		this.drug_unit = drug_unit;
	}
	public String getDrug_way() {
		return drug_way;
	}
	public void setDrug_way(String drug_way) {
		this.drug_way = drug_way;
	}
	public String getDrug_freq() {
		return drug_freq;
	}
	public void setDrug_freq(String drug_freq) {
		this.drug_freq = drug_freq;
	}
	public Integer getDept_Code() {
		return dept_Code;
	}
	public void setDept_Code(Integer dept_Code) {
		this.dept_Code = dept_Code;
	}
	public String getDoctor_code() {
		return doctor_code;
	}
	public void setDoctor_code(String doctor_code) {
		this.doctor_code = doctor_code;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public Timestamp getExecution_time() {
		return execution_time;
	}
	public void setExecution_time(Timestamp execution_time) {
		this.execution_time = execution_time;
	}
	public Timestamp getOver_time() {
		return over_time;
	}
	public void setOver_time(Timestamp over_time) {
		this.over_time = over_time;
	}
	public String getMedication_purpose() {
		return medication_purpose;
	}
	public void setMedication_purpose(String medication_purpose) {
		this.medication_purpose = medication_purpose;
	}
	public String getDoctor_explanation() {
		return doctor_explanation;
	}
	public void setDoctor_explanation(String doctor_explanation) {
		this.doctor_explanation = doctor_explanation;
	}
	public String getStop_doctor() {
		return stop_doctor;
	}
	public void setStop_doctor(String stop_doctor) {
		this.stop_doctor = stop_doctor;
	}
	public String getStop_nurse() {
		return stop_nurse;
	}
	public void setStop_nurse(String stop_nurse) {
		this.stop_nurse = stop_nurse;
	}
	public Integer getDoctor_advice_type() {
		return doctor_advice_type;
	}
	public void setDoctor_advice_type(Integer doctor_advice_type) {
		this.doctor_advice_type = doctor_advice_type;
	}
	public Integer getMedical_advice_time() {
		return medical_advice_time;
	}
	public void setMedical_advice_time(Integer medical_advice_time) {
		this.medical_advice_time = medical_advice_time;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public String getDatacode() {
		return datacode;
	}
	public void setDatacode(String datacode) {
		this.datacode = datacode;
	}
}