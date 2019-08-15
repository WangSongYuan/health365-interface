package cn.sqwsy.health365interface.dao.entity;

import java.sql.Timestamp;

/**
 * 电子病历
 * @author wangsongyuan
 */
public class SelctronicMedicalRecordEntity extends PO{
	private Integer id;//ID
	private String outhospitrecordid;//出院记录唯一标识
	private String content;//内容
	private Timestamp createtime;//创建时间
	private Timestamp updatetime;//更新时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOuthospitrecordid() {
		return outhospitrecordid;
	}
	public void setOuthospitrecordid(String outhospitrecordid) {
		this.outhospitrecordid = outhospitrecordid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
}