package cn.sqwsy.health365interface.dao.entity;

public class SUserorgEntity extends PO {
	/**id*/
	private java.lang.Integer id;
	/**userid*/
	
	private java.lang.Integer userid;
	/**departmentid*/
	
	private java.lang.Integer departmentid;
	/**orgid*/
	
	private java.lang.Integer orgid;
	
	private java.lang.Integer typeId;//1住院 2出院 3门诊
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.Integer getUserid() {
		return userid;
	}
	public void setUserid(java.lang.Integer userid) {
		this.userid = userid;
	}
	public java.lang.Integer getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(java.lang.Integer departmentid) {
		this.departmentid = departmentid;
	}
	public java.lang.Integer getOrgid() {
		return orgid;
	}
	public void setOrgid(java.lang.Integer orgid) {
		this.orgid = orgid;
	}
	public java.lang.Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(java.lang.Integer typeId) {
		this.typeId = typeId;
	}
}
