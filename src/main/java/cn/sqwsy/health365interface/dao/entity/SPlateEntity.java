package cn.sqwsy.health365interface.dao.entity;

import java.sql.Timestamp;

public class SPlateEntity extends PO {
	/**id*/
	private java.lang.Integer id;
	/**板块名称*/
	private java.lang.String name;
	/**板块类别*/
	private java.lang.String type;
	/**板块顺序*/
	private java.lang.String sort;
	private Timestamp createtime = new Timestamp(System.currentTimeMillis());//用户创建时间
	private Timestamp updatetime = new Timestamp(System.currentTimeMillis());//用户信息更新时间
	/**是否启用*/
	private java.lang.Integer state;
	/**管理阶段(入院初始...)*/
	private java.lang.Integer stage;
	/**是否通用*/
	private java.lang.Integer currency;
	/**url*/
	private java.lang.String url;
	
	private java.lang.Integer status=3;
	
	/**板块子类型*/
	private java.lang.Integer childtype;
	private transient String  currencyname;
	private transient String  statename;
	private transient String  stagename;
	private transient String  typename;
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
	public java.lang.String getType() {
		return type;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	public java.lang.String getSort() {
		return sort;
	}
	public void setSort(java.lang.String sort) {
		this.sort = sort;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public java.lang.Integer getState() {
		return state;
	}
	public void setState(java.lang.Integer state) {
		this.state = state;
	}
	public java.lang.Integer getStage() {
		return stage;
	}
	public void setStage(java.lang.Integer stage) {
		this.stage = stage;
	}
	public java.lang.Integer getCurrency() {
		return currency;
	}
	public void setCurrency(java.lang.Integer currency) {
		this.currency = currency;
	}
	public java.lang.String getUrl() {
		return url;
	}
	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	public java.lang.Integer getStatus() {
		return status;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	public java.lang.Integer getChildtype() {
		return childtype;
	}
	public void setChildtype(java.lang.Integer childtype) {
		this.childtype = childtype;
	}
	public String getCurrencyname() {
		return currencyname;
	}
	public void setCurrencyname(String currencyname) {
		this.currencyname = currencyname;
	}
	public String getStatename() {
		return statename;
	}
	public void setStatename(String statename) {
		this.statename = statename;
	}
	public String getStagename() {
		return stagename;
	}
	public void setStagename(String stagename) {
		this.stagename = stagename;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
}
