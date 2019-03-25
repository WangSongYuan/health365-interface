package cn.sqwsy.health365interface.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class SUserEntity extends PO {
	/**id*/
	private java.lang.Integer id;
	/**jobnum*/
	
	private java.lang.String jobnum;
	
	/**name*/
	
	private java.lang.String name;
	/**password*/
	
	private java.lang.String thirdpartyhisid;
	
	private java.lang.String password;
	/**createtime*/
	
	private java.sql.Timestamp createtime;
	/**updatetime*/
	
	private java.sql.Timestamp updatetime;
	/**headportrait*/
	
	private java.lang.String headportrait;
	private transient String departmentname;
	private transient String rolename;
	
	private List<SUserroleEntity> SUserroleEntity = new ArrayList<SUserroleEntity>();
	
	private String remoteAddr;//用户当前ip地址
	
	private List<SUserorgEntity> SUserorgEntityList = new ArrayList<SUserorgEntity>();
	
	public java.lang.Integer getId(){
		return this.id;
	}

	public java.lang.String getThirdpartyhisid() {
		return thirdpartyhisid;
	}

	public void setThirdpartyhisid(java.lang.String thirdpartyhisid) {
		this.thirdpartyhisid = thirdpartyhisid;
	}

	public java.lang.String getJobnum() {
		return jobnum;
	}

	public void setJobnum(java.lang.String jobnum) {
		this.jobnum = jobnum;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
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

	public java.lang.String getHeadportrait() {
		return headportrait;
	}

	public void setHeadportrait(java.lang.String headportrait) {
		this.headportrait = headportrait;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public List<SUserroleEntity> getSUserroleEntity() {
		return SUserroleEntity;
	}

	public void setSUserroleEntity(List<SUserroleEntity> sUserroleEntity) {
		SUserroleEntity = sUserroleEntity;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public List<SUserorgEntity> getSUserorgEntityList() {
		return SUserorgEntityList;
	}

	public void setSUserorgEntityList(List<SUserorgEntity> sUserorgEntityList) {
		SUserorgEntityList = sUserorgEntityList;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}
}
