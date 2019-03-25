package cn.sqwsy.health365interface.dao.entity;

/**   
 * @Title: 科室实体类
 * @Description: s_department
 * @author onlineGenerator
 * @date 2018-11-12 12:25:23
 * @version V1.0   
 *
 */
public class SDepartmentEntity extends PO {
	/**id*/
	private java.lang.Integer id;
	/**name*/
	
	private java.lang.String name;
	/**thirdpartyhisid*/
	
	private java.lang.String thirdpartyhisid;
	/**科室管理状态1管理2不管理*/
	
	private java.lang.Integer state;
	/**createtime*/
	
	private java.sql.Timestamp createtime;
	/**updatetime*/
	
	private java.sql.Timestamp updatetime;
	/**科室类别*/
	
	private java.lang.Integer type;
	/**orgid*/
	
	private java.lang.Integer orgid;
	/**departmentphone*/
	
	private java.lang.String departmentphone;
	/**nursestationphone*/
	
	private java.lang.String nursestationphone;
	/**探视时间*/
	
	private java.lang.String visittime;
	/**探视注意事项*/
	
	private java.lang.String visittip;
	
	/**
	 * 疏散通道图
	 */
	private String escapeRouteUrl;
	
	/**
	 * 楼层平面图
	 */
	private String floorPlanUrl;
	
	private transient boolean selected = false;
	private transient String selectename;
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getThirdpartyhisid() {
		return thirdpartyhisid;
	}
	public void setThirdpartyhisid(java.lang.String thirdpartyhisid) {
		this.thirdpartyhisid = thirdpartyhisid;
	}
	public java.lang.Integer getState() {
		return state;
	}
	public void setState(java.lang.Integer state) {
		this.state = state;
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
	public java.lang.Integer getType() {
		return type;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	public java.lang.Integer getOrgid() {
		return orgid;
	}
	public void setOrgid(java.lang.Integer orgid) {
		this.orgid = orgid;
	}
	public java.lang.String getDepartmentphone() {
		return departmentphone;
	}
	public void setDepartmentphone(java.lang.String departmentphone) {
		this.departmentphone = departmentphone;
	}
	public java.lang.String getNursestationphone() {
		return nursestationphone;
	}
	public void setNursestationphone(java.lang.String nursestationphone) {
		this.nursestationphone = nursestationphone;
	}
	public java.lang.String getVisittime() {
		return visittime;
	}
	public void setVisittime(java.lang.String visittime) {
		this.visittime = visittime;
	}
	public java.lang.String getVisittip() {
		return visittip;
	}
	public void setVisittip(java.lang.String visittip) {
		this.visittip = visittip;
	}
	public String getEscapeRouteUrl() {
		return escapeRouteUrl;
	}
	public void setEscapeRouteUrl(String escapeRouteUrl) {
		this.escapeRouteUrl = escapeRouteUrl;
	}
	public String getFloorPlanUrl() {
		return floorPlanUrl;
	}
	public void setFloorPlanUrl(String floorPlanUrl) {
		this.floorPlanUrl = floorPlanUrl;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getSelectename() {
		return selectename;
	}
	public void setSelectename(String selectename) {
		this.selectename = selectename;
	}
}
