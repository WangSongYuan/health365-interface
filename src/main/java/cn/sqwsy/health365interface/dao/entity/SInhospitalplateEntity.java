package cn.sqwsy.health365interface.dao.entity;

public class SInhospitalplateEntity extends PO{

	/**id*/
	private java.lang.Integer id;
	/**住院表ID*/
	
	private SInhospitalEntity sInhospitalEntity;
	/**板块ID*/
	
	private SPlateEntity sPlateEntity;
	/**完成状态*/
	
	private java.lang.Integer status=3;//1未完成 2完成 3无状态 4医生已完成
	/**createtime*/
	
	private java.sql.Timestamp createtime;
	/**updatetime*/
	
	private java.sql.Timestamp updatetime;
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public SInhospitalEntity getsInhospitalEntity() {
		return sInhospitalEntity;
	}
	public void setsInhospitalEntity(SInhospitalEntity sInhospitalEntity) {
		this.sInhospitalEntity = sInhospitalEntity;
	}
	public SPlateEntity getsPlateEntity() {
		return sPlateEntity;
	}
	public void setsPlateEntity(SPlateEntity sPlateEntity) {
		this.sPlateEntity = sPlateEntity;
	}
	public java.lang.Integer getStatus() {
		return status;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
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

