package cn.sqwsy.health365interface.dao.entity;

import java.sql.Timestamp;
/**   
 * @Title: 治疗方案/处方/指导表
 * @Description: s_programme
 * @author lizhenghong
 * @date 2018-11-23 12:24:39
 * @version V2.0   
 *
 */
public class SProgrammeEntity extends PO {
	private java.lang.Integer id;//主键id
	private java.lang.Integer inhospitalid;//住院表ID
	private java.lang.Integer plateid;//版块ID
	private java.lang.Integer state=1;//是否编辑(1.未编辑2.已编辑)
	private java.lang.String content;//内容{治疗方案/处方/指导】
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
	public java.lang.Integer getPlateid() {
		return plateid;
	}
	public void setPlateid(java.lang.Integer plateid) {
		this.plateid = plateid;
	}
	public java.lang.Integer getState() {
		return state;
	}
	public void setState(java.lang.Integer state) {
		this.state = state;
	}
	public java.lang.String getContent() {
		return content;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
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
