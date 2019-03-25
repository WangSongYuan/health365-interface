package cn.sqwsy.health365interface.dao.entity;

/**   
 * @Title: Entity
 * @Description: s_departmentplate
 * @author onlineGenerator
 * @date 2018-11-12 12:25:35
 * @version V1.0   
 *
 */
public class SDepartmentplateEntity extends PO {
	/**id*/
	private java.lang.Integer id;
	/**departmentid*/
	
	private java.lang.Integer departmentid;
	/**orgid*/
	
	private java.lang.Integer orgid;
	/**plateid*/
	
	private java.lang.Integer plateid;
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
	public java.lang.Integer getPlateid() {
		return plateid;
	}
	public void setPlateid(java.lang.Integer plateid) {
		this.plateid = plateid;
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
