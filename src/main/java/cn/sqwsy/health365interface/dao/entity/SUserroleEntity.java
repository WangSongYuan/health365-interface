package cn.sqwsy.health365interface.dao.entity;

public class SUserroleEntity extends PO {
	/**id*/
	private java.lang.Integer id;
	/**userid*/
	
	private SUserEntity sUserEntity;
	private SRoleEntity sRoleEntity;
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public SUserEntity getsUserEntity() {
		return sUserEntity;
	}
	public void setsUserEntity(SUserEntity sUserEntity) {
		this.sUserEntity = sUserEntity;
	}
	public SRoleEntity getsRoleEntity() {
		return sRoleEntity;
	}
	public void setsRoleEntity(SRoleEntity sRoleEntity) {
		this.sRoleEntity = sRoleEntity;
	}
	
	
}
