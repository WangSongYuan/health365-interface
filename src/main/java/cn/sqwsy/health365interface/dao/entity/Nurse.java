package cn.sqwsy.health365interface.dao.entity;

public class Nurse{
	private int id;
	private String nurseId;
	private String nurseName;
	private String departentId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNurseId() {
		return nurseId;
	}
	public void setNurseId(String nurseId) {
		this.nurseId = nurseId;
	}
	public String getNurseName() {
		return nurseName;
	}
	public void setNurseName(String nurseName) {
		this.nurseName = nurseName;
	}
	public String getDepartentId() {
		return departentId;
	}
	public void setDepartentId(String departentId) {
		this.departentId = departentId;
	}
}
