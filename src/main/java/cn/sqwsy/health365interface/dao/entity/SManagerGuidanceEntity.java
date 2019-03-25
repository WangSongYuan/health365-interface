package cn.sqwsy.health365interface.dao.entity;

import java.sql.Timestamp;

public class SManagerGuidanceEntity extends PO {
	private java.lang.Integer id;//主键id
	private java.lang.Integer inhospitalid;//住院表ID
	private java.lang.String content;//内容{医嘱及生活方式指导】
	private java.lang.Integer type;//指导类型(1.医嘱2.饮食3.运动4.心理5.睡眠6.健康教育)
	private java.lang.Integer operator;//操作人——userid
	private java.sql.Timestamp createtime=new Timestamp(System.currentTimeMillis());//问卷版块住院填写记录创建时间
	private java.sql.Timestamp updatetime=new Timestamp(System.currentTimeMillis());//问卷版块住院填写记录更新时间
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.Integer getInhospitalid() {
		return inhospitalid;
	}
	public void setInhospitalid(java.lang.Integer inhospitalid) {
		this.inhospitalid = inhospitalid;
	}
	public java.lang.String getContent() {
		return content;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	public java.lang.Integer getType() {
		return type;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	public java.lang.Integer getOperator() {
		return operator;
	}
	public void setOperator(java.lang.Integer operator) {
		this.operator = operator;
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

}
