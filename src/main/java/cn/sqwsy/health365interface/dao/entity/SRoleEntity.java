package cn.sqwsy.health365interface.dao.entity;

public class SRoleEntity extends PO {

	private java.lang.Integer id;
	
	private java.sql.Timestamp updatetime;

	private java.sql.Timestamp createtime;
	
	private Integer typeId;//角色类别 1、超级管理员 2、医院管理员 3、院中疾病管理师 4、专科医生 5、主管护士6、院后疾病管理师7、科室管理员 8、院中疾病管理中心管理员 9、院后疾病管理中心管理员
	
	private Integer orgId;//医院Id
	private transient boolean selected = false;
	private String name;
	private transient String orgname;
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.sql.Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(java.sql.Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public java.sql.Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(java.sql.Timestamp createtime) {
		this.createtime = createtime;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
}
