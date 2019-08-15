package cn.sqwsy.health365interface.dao.entity;

public class SPatientEntity extends PO {
	/**id*/
	private java.lang.Integer id;
	/**name*/
	
	private java.lang.String name;
	/**age*/
	
	private java.lang.Integer age;
	/**cardnum*/
	
	private java.lang.String cardnum;
	/**sex*/
	
	private java.lang.String sex;
	/**婚姻状况*/
	
	private java.lang.String marry;
	/**职业*/
	
	private java.lang.String profession;
	/**currentaddress*/
	
	private java.lang.String currentaddress;
	/**teladdress*/
	
	private java.lang.String teladdress;
	/**company*/
	
	private java.lang.String company;
	/**文化程度*/
	
	private java.lang.String education;
	/**ageunit*/
	
	private java.lang.String ageunit;
	/**createtime*/
	
	private java.sql.Timestamp createtime;
	/**updatetime*/
	
	private java.sql.Timestamp updatetime;
	/**nation*/
	
	private java.lang.String nation;
	/**birthday*/
	
	private java.util.Date birthday;
	/**既往史*/
	
	private java.lang.String pasthistory;
	/**常住类型*/
	
	private java.lang.String permanenttype;
	/**联系人姓名*/
	
	private java.lang.String relativename;
	
	private java.lang.String relativephone;//联系人电话
	
	/**常驻类型*/
	private java.lang.String residenttype;
	
	private java.lang.String phone;//最近一次更新的手机号
	
	private java.lang.String phonetwo;//患者手机2
	
	private java.lang.String bloodtype;//血型
	private String phonethree;//病人修改电话
	/*
	 * 增加字段(栗)
	 */
	private String relation;//与联系人关系
	
	private Integer orgId;//医院ID
	
	private String companyphone;//公司电话

	public java.lang.Integer getId(){
		return this.id;
	}

	public void setId(java.lang.Integer id){
		this.id = id;
	}

	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}

	public java.lang.Integer getAge(){
		return this.age;
	}

	public void setAge(java.lang.Integer age){
		this.age = age;
	}

	public java.lang.String getCardnum(){
		return this.cardnum;
	}

	public void setCardnum(java.lang.String cardnum){
		this.cardnum = cardnum;
	}

	public java.lang.String getSex(){
		return this.sex;
	}

	public void setSex(java.lang.String sex){
		this.sex = sex;
	}

	public java.lang.String getMarry(){
		return this.marry;
	}

	public void setMarry(java.lang.String marry){
		this.marry = marry;
	}

	public java.lang.String getProfession(){
		return this.profession;
	}

	public void setProfession(java.lang.String profession){
		this.profession = profession;
	}

	public java.lang.String getCurrentaddress(){
		return this.currentaddress;
	}

	public void setCurrentaddress(java.lang.String currentaddress){
		this.currentaddress = currentaddress;
	}

	public java.lang.String getTeladdress(){
		return this.teladdress;
	}

	public void setTeladdress(java.lang.String teladdress){
		this.teladdress = teladdress;
	}

	public java.lang.String getCompany(){
		return this.company;
	}

	public void setCompany(java.lang.String company){
		this.company = company;
	}

	public java.lang.String getEducation(){
		return this.education;
	}

	public void setEducation(java.lang.String education){
		this.education = education;
	}

	public java.lang.String getAgeunit(){
		return this.ageunit;
	}

	public void setAgeunit(java.lang.String ageunit){
		this.ageunit = ageunit;
	}

	public java.sql.Timestamp getCreatetime(){
		return this.createtime;
	}

	public void setCreatetime(java.sql.Timestamp createtime){
		this.createtime = createtime;
	}

	public java.sql.Timestamp getUpdatetime(){
		return this.updatetime;
	}

	public void setUpdatetime(java.sql.Timestamp updatetime){
		this.updatetime = updatetime;
	}

	public java.lang.String getNation(){
		return this.nation;
	}

	public void setNation(java.lang.String nation){
		this.nation = nation;
	}

	public java.util.Date getBirthday(){
		return this.birthday;
	}

	public void setBirthday(java.util.Date birthday){
		this.birthday = birthday;
	}

	public java.lang.String getPasthistory(){
		return this.pasthistory;
	}

	public void setPasthistory(java.lang.String pasthistory){
		this.pasthistory = pasthistory;
	}

	public java.lang.String getPermanenttype(){
		return this.permanenttype;
	}

	public void setPermanenttype(java.lang.String permanenttype){
		this.permanenttype = permanenttype;
	}

	public java.lang.String getRelativename(){
		return this.relativename;
	}

	public void setRelativename(java.lang.String relativename){
		this.relativename = relativename;
	}

	public java.lang.String getResidenttype(){
		return this.residenttype;
	}

	public void setResidenttype(java.lang.String residenttype){
		this.residenttype = residenttype;
	}

	public java.lang.String getRelativephone() {
		return relativephone;
	}

	public void setRelativephone(java.lang.String relativephone) {
		this.relativephone = relativephone;
	}

	public java.lang.String getPhone() {
		return phone;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public java.lang.String getBloodtype() {
		return bloodtype;
	}

	public void setBloodtype(java.lang.String bloodtype) {
		this.bloodtype = bloodtype;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getPhonethree() {
		return phonethree;
	}

	public void setPhonethree(String phonethree) {
		this.phonethree = phonethree;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public java.lang.String getPhonetwo() {
		return phonetwo;
	}

	public void setPhonetwo(java.lang.String phonetwo) {
		this.phonetwo = phonetwo;
	}

	public String getCompanyphone() {
		return companyphone;
	}

	public void setCompanyphone(String companyphone) {
		this.companyphone = companyphone;
	}
}
