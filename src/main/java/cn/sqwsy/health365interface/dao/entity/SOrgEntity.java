package cn.sqwsy.health365interface.dao.entity;


public class SOrgEntity extends PO {
	/**id*/
	private java.lang.Integer id;
	/**name*/
	
	private java.lang.String name;
	/**updatetime*/
	
	private java.sql.Timestamp updatetime;
	/**address*/
	
	private java.lang.String address;
	/**logourl*/
	
	private java.lang.String logourl;
	/**grade*/
	
	private java.lang.String grade;
	/**createtime*/
	
	private java.sql.Timestamp createtime;
	/**投诉电话*/
	
	private java.lang.String complaintphone;
	/**客服电话*/
	
	private java.lang.String customerservicephone;
	
	/**
	 * 就医流程图url
	 */
	private String medicalTreatmentProcessUrl;
	
	/**
	 * 医院宣传片url
	 */
	private String advertisingVideoUrl;
	
	/**
	 *满意度卷首语
	 */
	private String volumeinitials;

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

	public java.sql.Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(java.sql.Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getLogourl() {
		return logourl;
	}

	public void setLogourl(java.lang.String logourl) {
		this.logourl = logourl;
	}

	public java.lang.String getGrade() {
		return grade;
	}

	public void setGrade(java.lang.String grade) {
		this.grade = grade;
	}

	public java.sql.Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.sql.Timestamp createtime) {
		this.createtime = createtime;
	}

	public java.lang.String getComplaintphone() {
		return complaintphone;
	}

	public void setComplaintphone(java.lang.String complaintphone) {
		this.complaintphone = complaintphone;
	}

	public java.lang.String getCustomerservicephone() {
		return customerservicephone;
	}

	public void setCustomerservicephone(java.lang.String customerservicephone) {
		this.customerservicephone = customerservicephone;
	}

	public String getMedicalTreatmentProcessUrl() {
		return medicalTreatmentProcessUrl;
	}

	public void setMedicalTreatmentProcessUrl(String medicalTreatmentProcessUrl) {
		this.medicalTreatmentProcessUrl = medicalTreatmentProcessUrl;
	}

	public String getAdvertisingVideoUrl() {
		return advertisingVideoUrl;
	}

	public void setAdvertisingVideoUrl(String advertisingVideoUrl) {
		this.advertisingVideoUrl = advertisingVideoUrl;
	}

	public String getVolumeinitials() {
		return volumeinitials;
	}

	public void setVolumeinitials(String volumeinitials) {
		this.volumeinitials = volumeinitials;
	}
}
