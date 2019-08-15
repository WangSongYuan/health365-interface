package cn.sqwsy.health365interface.dao.entity;

import java.sql.Timestamp;

public class SMessageListEntity extends PO{
	private Integer id;
	
	private String title;//标题
	
	private Integer isRead=0; //是否阅读 0未读 1已读
	
	private Integer senderId;//发送者
	
	private Integer recipientId;//接收者
	
	private String url;//链接
	
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());//创建时间
	
	private Integer type;//1、通知消息 2、医生护士回复 3、处方 4、随访计划
	
	private Integer orgId;//医院ID
	
	private Integer inhospitalId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public Integer getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Integer recipientId) {
		this.recipientId = recipientId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getInhospitalId() {
		return inhospitalId;
	}

	public void setInhospitalId(Integer inhospitalId) {
		this.inhospitalId = inhospitalId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
}