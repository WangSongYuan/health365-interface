package cn.sqwsy.health365interface.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import cn.sqwsy.health365interface.dao.entity.SDepartmentEntity;
import cn.sqwsy.health365interface.dao.entity.SDepartmentplateEntity;
import cn.sqwsy.health365interface.dao.entity.SInhospitalEntity;
import cn.sqwsy.health365interface.dao.entity.SInhospitalplateEntity;
import cn.sqwsy.health365interface.dao.entity.SOrgEntity;
import cn.sqwsy.health365interface.dao.entity.SPatientEntity;
import cn.sqwsy.health365interface.dao.entity.SPlateEntity;
import cn.sqwsy.health365interface.dao.entity.SProgrammeEntity;
import cn.sqwsy.health365interface.dao.entity.SRoleEntity;
import cn.sqwsy.health365interface.dao.entity.SUserEntity;
import cn.sqwsy.health365interface.dao.entity.SUserorgEntity;
import cn.sqwsy.health365interface.dao.entity.SUserroleEntity;
import cn.sqwsy.health365interface.dao.mapper.DepartmentMapper;
import cn.sqwsy.health365interface.dao.mapper.DepartmentPlateMapper;
import cn.sqwsy.health365interface.dao.mapper.InhospitalMapper;
import cn.sqwsy.health365interface.dao.mapper.InhospitalPlateMapper;
import cn.sqwsy.health365interface.dao.mapper.OrgMapper;
import cn.sqwsy.health365interface.dao.mapper.PatientMapper;
import cn.sqwsy.health365interface.dao.mapper.PlateMapper;
import cn.sqwsy.health365interface.dao.mapper.ProgrammeMapper;
import cn.sqwsy.health365interface.dao.mapper.RoleMapper;
import cn.sqwsy.health365interface.dao.mapper.UserMapper;
import cn.sqwsy.health365interface.dao.mapper.UserOrgMapper;
import cn.sqwsy.health365interface.dao.mapper.UserRoleMapper;
import cn.sqwsy.health365interface.service.utils.CardNumUtil;
import cn.sqwsy.health365interface.service.utils.HashUtil;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

public class HisDateService {
	
	@Autowired
	public OrgMapper orgMapper;
	
	@Autowired
    public DepartmentMapper departmentMapper;
	
	@Autowired
    public InhospitalMapper inhospitalMapper;
	
	@Autowired
    public InhospitalPlateMapper inhospitalPlateMapper;
	
	@Autowired
	public PlateMapper plateMapper;
	
	@Autowired
	public DepartmentPlateMapper departmentPlateMapper;
	
	@Autowired
	public UserMapper userMapper;
	
	@Autowired
	public RoleMapper roleMapper;
	
	@Autowired
	public UserRoleMapper userRoleMapper;
	
	@Autowired
	public UserOrgMapper userOrgMapper;
	
	@Autowired
	public PatientMapper patientMapper;
	
	@Autowired
	public ProgrammeMapper programmeMapper;
	
	public void sysHisByDateBase(){
		
	}
	
	public void startGrabDataByElement(Element element,Map<String,Object> para,Integer orgId,Integer status) throws SQLException{
		//机构
		Map<String,Object> orgPara = new HashMap<>();
		orgPara.put("id", orgId);
		SOrgEntity org = orgMapper.getOrg(orgPara);
		SInhospitalEntity s = inhospitalMapper.getInhospital(para);
		//插入入院and出院科室表
		SDepartmentEntity department = null;
		if(status==1){
			//入院科室表
			department = setDepartment(element.element("inhospitaldepartmentid").getText(),element.element("inhospitaldepartment").getText(),orgId,null);
		}else if(status==2){
			//出院科室表
			department = setDepartment(element.element("outhospitaldepartmentid").getText(),element.element("outhospitaldepartment").getText(),orgId,null);
		}
		//无需管理
		if(department.getState()==2){
			return;
		}
		SUserEntity doctor = null;
		SUserEntity nurse = null;
    	if(s==null){
    		s = new SInhospitalEntity();
    		validDataByElement(element, s);
    		//插入医生用户表
    		if(element.element("maindoctorname") != null&&ValidateUtil.isNotNull(element.element("maindoctorname").getText())&&element.element("maindoctorid") != null&&ValidateUtil.isNotNull(element.element("maindoctorid").getText())){
    			doctor = setUser(element.element("maindoctorname").getText(),element.element("maindoctorid").getText(),department,org,4);
    		}
    		if(element.element("nurseno") != null&&ValidateUtil.isNotNull(element.element("nurseno").getText())&&element.element("nursename") != null&&ValidateUtil.isNotNull(element.element("nursename").getText())){
    			//插入护士相关
    			nurse = setUser(element.element("nursename").getText(),element.element("nurseno").getText(),department, org, 5);
    		}
    		
    		//验证通过的情况下插入患者表
    		if(s.getIsValid()==1){
    			SPatientEntity patient = setPatientByElement(element, orgId);
    			s.setsPatientEntity(patient);
    		}
    		
    		//住院信息赋值
    		setInhospitalDateByElement(element,orgId,status,department,s,doctor,nurse);
    		/**
    		 * 任务分配
    		 */
    		SUserEntity randDiseaseManager = userMapper.getOrderDiseaseManager(department.getId());
    		s.setInDiseaseManagerId(randDiseaseManager.getId());
    		inhospitalMapper.setInhospital(s);
    		if(status==1){
    			//插入住院板块完成度表
    			setInhosipitalplate(s,department.getId());
    		}
    	}else{
    		validDataByElement(element, s);
    		//插入医生用户表
    		if(element.element("maindoctorname") != null&&ValidateUtil.isNotNull(element.element("maindoctorname").getText())&&element.element("maindoctorid") != null&&ValidateUtil.isNotNull(element.element("maindoctorid").getText())){
    			doctor = setUser(element.element("maindoctorname").getText(),element.element("maindoctorid").getText(),department,org,4);
    		}
    		if(element.element("nurseno") != null&&ValidateUtil.isNotNull(element.element("nurseno").getText())&&element.element("nursename") != null&&ValidateUtil.isNotNull(element.element("nursename").getText())){
    			//插入护士相关
    			nurse  = setUser(element.element("nursename").getText(),element.element("nurseno").getText(),department, org, 5);
    		}
    		//验证通过的情况下插入患者表
    		if(s.getIsValid()==1){
    			SPatientEntity patient = setPatientByElement(element, orgId);
    			s.setsPatientEntity(patient);
    		}
    		//住院信息赋值
    		setInhospitalDateByElement(element,orgId,status,department,s,doctor,nurse);
    		inhospitalMapper.updateInhospital(s);
    	}
	}
	
	public void sysHisByJson(List<Element> list){
		for (int i = 0; i < list.size(); i++) {
			
		}
	}
	
	/**
	 * 开始抓取数据(ResultSet)
	 * @param rs
	 * @throws SQLException
	 */
	public void startGrabDataByResultSet(ResultSet rs,Map<String,Object> para,Integer orgId,Integer status) throws SQLException{
		//机构
		Map<String,Object> orgPara = new HashMap<>();
		orgPara.put("id", orgId);
		SOrgEntity org = orgMapper.getOrg(orgPara);
		SInhospitalEntity s = inhospitalMapper.getInhospital(para);
		//插入入院and出院科室表
		SDepartmentEntity department = null;
		if(status==1){
			//入院科室表
			department = setDepartment(rs.getString("inhospitaldepartmentid"),rs.getString("inhospitaldepartment"),orgId,null);
		}else if(status==2){
			//出院科室表
			department = setDepartment(rs.getString("outhospitaldepartmentid"),rs.getString("outhospitaldepartment"),orgId,null);
		}
		//无需管理
		if(department.getState()==2){
			return;
		}
		SUserEntity doctor = null;
		SUserEntity nurse = null;
    	if(s==null){
    		s = new SInhospitalEntity();
    		validDataByResultSet(rs, s);
    		//插入医生相关表
    		if(ValidateUtil.isNotNull(rs.getString("maindoctorid"))&&ValidateUtil.isNotNull(rs.getString("maindoctorname"))){
    			doctor = setUser(rs.getString("maindoctorname"),rs.getString("maindoctorid"),department,org,4);
    		}
    		//插入护士相关
    		if(ValidateUtil.isNotNull(rs.getString("nursename"))&&!ValidateUtil.isNotNull(rs.getString("nurseno"))){
    			nurse = setUser(rs.getString("nursename"), rs.getString("nurseno"), department, org, 5);
    		}
    		//验证通过的情况下插入患者表
    		if(s.getIsValid()==1){
    			SPatientEntity patient = setPatientByResultSet(rs, orgId);
    			s.setsPatientEntity(patient);
    		}
    		//住院信息赋值
    		setInhospitalDateByResultSet(rs, orgId, status, department, s,doctor,nurse);
    		/**
    		 * 任务分配
    		 */
    		SUserEntity randDiseaseManager = userMapper.getOrderDiseaseManager(department.getId());
    		s.setInDiseaseManagerId(randDiseaseManager.getId());
    		inhospitalMapper.setInhospital(s);
    		if(status==1){
    			//插入住院板块完成度表
    			setInhosipitalplate(s,department.getId());
    		}
    	}else{
    		validDataByResultSet(rs, s);
    		//插入医生用户表
    		if(ValidateUtil.isNotNull(rs.getString("maindoctorid"))&&!ValidateUtil.isNotNull(rs.getString("maindoctorname"))){
    			doctor = setUser(rs.getString("maindoctorname"),rs.getString("maindoctorid"),department,org,4);
    		}
    		if(ValidateUtil.isNotNull(rs.getString("nursename"))&&!ValidateUtil.isNotNull(rs.getString("nurseno"))){
    			nurse = setUser(rs.getString("nursename"), rs.getString("nurseno"), department, org, 5);
    		}
    		//验证通过的情况下插入患者表
    		if(s.getIsValid()==1){
    			SPatientEntity patient = setPatientByResultSet(rs, orgId);
    			s.setsPatientEntity(patient);
    		}
    		//住院信息赋值
    		setInhospitalDateByResultSet(rs, orgId, status, department, s,doctor,nurse);
    		inhospitalMapper.updateInhospital(s);
    	}
	}
	
	/**
	 * 校验数据(ResultSet)
	 * @param rs
	 * @param s
	 * @throws SQLException
	 */
	private void validDataByResultSet(ResultSet rs, SInhospitalEntity s) throws SQLException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式  注意身份证信息为空
		StringBuffer ap = new StringBuffer( "当前时间："+df.format(new Date())+"您有新的消息:</br>科室:"+rs.getString("inhospitaldepartmentid")+",主治医师:"+rs.getString("maindoctorname")+",患者姓名:"+rs.getString("name")+"</br>住院号:"+rs.getString("inhospitalid")+",住院次数:"+rs.getString("inhospitalcount")+"</br>错误信息如下:</br>");
		if(ValidateUtil.isNull(rs.getString("maindoctorid"))){
			ap.append("</br>主管医师工号为空</br>");
		}else if(ValidateUtil.isNull(rs.getString("cardnum"))&&ValidateUtil.isEquals("岁",rs.getString("ageunit"))&& rs.getInt("age")>9){
			ap.append("</br>身份证号为空</br>");
		}else if(ValidateUtil.isEquals("岁", rs.getString("ageunit")) && rs.getInt("age")>9 && !CardNumUtil.isValidate18Idcard(rs.getString("cardnum"))){
			ap.append("</br>身份证号填写错误</br>");
		}else{
			s.setIsValid(1);
		}
		if(s.getIsValid()==0){
			s.setErrorMsg(ap.toString());
		}
	}
	
	/**
	 * 校验数据(Element)
	 * @param rs
	 * @param s
	 * @throws SQLException
	 */
	private void validDataByElement(Element element, SInhospitalEntity s) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式  注意身份证信息为空
		StringBuffer ap = new StringBuffer( "当前时间："+df.format(new Date())+"您有新的消息:</br>科室:"+element.element("inhospitaldepartmentid").getText()+",主治医师:"+element.element("maindoctorname").getText()+",患者姓名:"+element.element("name").getText()+"</br>住院号:"+element.element("inhospitalid").getText()+",住院次数:"+element.element("inhospitalcount").getText()+"</br>错误信息如下:</br>");
		if(element.element("maindoctorid")==null||ValidateUtil.isNull(element.element("maindoctorid").getText())){
			ap.append("</br>主管医师工号为空</br>");
		}else if(ValidateUtil.isNull(element.element("cardnum").getText())&&ValidateUtil.isEquals("岁",element.element("ageunit").getText())&& Integer.valueOf(element.element("age").getText())>9){
			ap.append("</br>身份证号为空</br>");
		}else if(ValidateUtil.isEquals("岁", element.element("ageunit").getText()) && Integer.valueOf(element.element("age").getText())>9 && !CardNumUtil.isValidate18Idcard(element.element("cardnum").getText())){
			ap.append("</br>身份证号填写错误</br>");
		}else{
			s.setIsValid(1);
		}
		if(s.getIsValid()==0){
			s.setErrorMsg(ap.toString());
		}
	}

	/**
	 * 插入用户相关表(通用)
	 * @param name
	 * @param thirdpartyhisid
	 * @param inhospitalDepartment
	 * @param org
	 * @return
	 * @throws SQLException
	 */
	private SUserEntity setUser(String name,String thirdpartyhisid,SDepartmentEntity department, SOrgEntity org,Integer roleTypeId)
			throws SQLException {
		SUserEntity user = setUser(name,thirdpartyhisid);
		SRoleEntity userRole = getUserRole(org, user,roleTypeId);
		//插入角色关联表
		setUserRole(user, userRole);
		//插入用户机构科室关联表
		setUserOrg(user, org, department);
		return user;
	}

	/**
	 * 获取用户角色(通用)
	 * @param inhospitalDepartment
	 * @param org
	 * @param doctor
	 * @param typeId
	 * @return
	 * @throws SQLException
	 */
	protected SRoleEntity getUserRole(SOrgEntity org,SUserEntity doctor,Integer typeId) throws SQLException {
		Map<String,Object> doctorRolePara = new HashMap<>();
		doctorRolePara.put("orgId", org.getId());
		doctorRolePara.put("typeId", typeId);
		return roleMapper.getRole(doctorRolePara);
	}

	/**
	 * 插住院板块完成度表及治疗方案/处方/指导表s_programme(通用)
	 * @param s
	 * @param departmentId
	 */
	private void setInhosipitalplate(SInhospitalEntity s ,Integer departmentId) {
		//获取这个科室需要做的板块ID
		List<SPlateEntity> list = inhospitalPlateMapper.getDepartmentPlate(departmentId);
		for (SPlateEntity sPlateEntity : list) {
			SInhospitalplateEntity sip = new SInhospitalplateEntity();
			sip.setsInhospitalEntity(s);
			sip.setsPlateEntity(sPlateEntity);
			if(sPlateEntity.getType().equals("4")){
				sip.setStatus(3);
			}else if(sPlateEntity.getType().equals("5")){
				//插入治疗方案/处方/指导表s_programme
				SProgrammeEntity programme = new SProgrammeEntity();
				programme.setInhospitalid(s.getId());
				programme.setPlateid(sPlateEntity.getId());
				programme.setState(1);
				programmeMapper.setProgramme(programme);
				sip.setStatus(1);
			}else{
				sip.setStatus(1);
			}
			inhospitalPlateMapper.setInhospitalPlate(sip);
		}
	}

	/**
	 * 插入科室(通用)
	 * @param departmentId
	 * @param departmentName
	 * @param orgId
	 * @return
	 * @throws SQLException
	 */
	public SDepartmentEntity setDepartment(String departmentId,String departmentName,Integer orgId,List<String> departmentDefaultStateList) throws SQLException{
		SDepartmentEntity department =null;
		if(ValidateUtil.isNotNull(departmentId)&&ValidateUtil.isNotNull(departmentName)){
			department = departmentMapper.getDepartmentByHisId(departmentId);
			if(department==null){
				department = new SDepartmentEntity();
				setDepartmentData(departmentId,departmentName,orgId,department,departmentDefaultStateList);
				departmentMapper.setDepartment(department);
				Map<String,Object> para = new HashMap<>();
				para.put("state", 1);//查询板块是否启用
				List<SPlateEntity> list = plateMapper.getAllPlate(para);
				for(SPlateEntity plate:list){
					setDepartmentPlate(department, plate);
				}
			}else{
				setDepartmentData(departmentId,departmentName, orgId, department,departmentDefaultStateList);
				departmentMapper.updateDepartment(department);
			}
		}
		return department;
	}

	/**
	 * 科室赋值(通用)
	 * @param departmentId
	 * @param departmentName
	 * @param orgId
	 * @param department
	 * @throws SQLException
	 */
	private void setDepartmentData(String departmentId,String departmentName,Integer orgId, SDepartmentEntity department,List<String> departmentDefaultStateList) throws SQLException {
		department.setOrgid(orgId);
		department.setType(1);//章丘默认住院科室type=1
		department.setName(departmentName);
		department.setThirdpartyhisid(departmentId);
		if(departmentDefaultStateList!=null&&department.getUpdatetime()==null){
			for(String s:departmentDefaultStateList){
				if(s.equals(departmentId)){
					department.setState(1);
				}else{
					department.setState(2);//默认不管理
				}
			}
		}
	}

	/**
	 * 插入科室板块关联表(通用)
	 * @param department
	 * @param plate
	 */
	private void setDepartmentPlate(SDepartmentEntity department, SPlateEntity plate) {
		Map<String,Object> departmentPlatePara = new HashMap<>();
		departmentPlatePara.put("departmentid", department.getId());
		departmentPlatePara.put("orgid", 1);
		departmentPlatePara.put("plateid", plate.getId());
		SDepartmentplateEntity departmentPlate = departmentPlateMapper.getDepartmentPlate(departmentPlatePara);
		if(departmentPlate==null){
			departmentPlate = new SDepartmentplateEntity();
			departmentPlate.setOrgid(1);
			departmentPlate.setDepartmentid(department.getId());
			departmentPlate.setPlateid(plate.getId());
			departmentPlateMapper.setDepartmentPlate(departmentPlate);
		}
	}
	
	/**
	 * 插入用户(通用)
	 * @param name
	 * @param thirdpartyhisid
	 * @return
	 * @throws SQLException
	 */
	private SUserEntity setUser(String name,String thirdpartyhisid) throws SQLException{
		Map<String,Object> userPara = new HashMap<>();
		userPara.put("thirdpartyhisid", thirdpartyhisid);
		SUserEntity user = userMapper.getUser(userPara);
		if(user==null){
			user = new SUserEntity();
			setUserData(name,thirdpartyhisid,user);
			userMapper.setUser(user);
		}else{
			//更新用户
			setUserData(name,thirdpartyhisid,user);
			userMapper.updateUser(user);
		}
		return user;
	}

	/**
	 * 用户赋值(通用)
	 * @param name
	 * @param thirdpartyhisid
	 * @param user
	 */
	private void setUserData(String name,String thirdpartyhisid, SUserEntity user) {
		user.setName(name);
		user.setJobnum(thirdpartyhisid);
		user.setThirdpartyhisid(thirdpartyhisid);
		user.setPassword("123456");
	}

	/**
	 * 插入用户角色关联表(通用)
	 * @param user
	 * @param role
	 * @throws SQLException
	 */
	protected void setUserRole(SUserEntity user,SRoleEntity role) throws SQLException{
		Map<String,Object> userRolePara = new HashMap<>();
		userRolePara.put("userid", user.getId());
		userRolePara.put("roleid", role.getId());
		SUserroleEntity userRole = userRoleMapper.getUserRole(userRolePara);
		if(userRole==null){
			userRole = new SUserroleEntity();
			userRole.setsUserEntity(user);
			userRole.setsRoleEntity(role);
			userRoleMapper.setUserRole(userRole);
		}else{
			//更新用户角色关联表
		}
	}
	
	/**
	 * 插入用户机构关联表(通用)
	 * @param user
	 * @param org
	 * @param department
	 * @throws SQLException
	 */
	protected void setUserOrg(SUserEntity user,SOrgEntity org,SDepartmentEntity department) throws SQLException{
		Map<String,Object> userOrgPara = new HashMap<>();
		userOrgPara.put("userid", user.getId());
		userOrgPara.put("departmentid", department.getId());
		userOrgPara.put("orgid", org.getId());
		SUserorgEntity userOrg = userOrgMapper.getUserOrg(userOrgPara);
		if(userOrg==null){
			userOrg = new SUserorgEntity();
			userOrg.setDepartmentid(department.getId());
			userOrg.setOrgid(org.getId());
			userOrg.setUserid(user.getId());
			userOrgMapper.setUserOrg(userOrg);
		}else{
			//更新用户机构关联
		}
	}
	
	/**
	 * 插入患者表(ResultSet)
	 * @param rs
	 * @throws SQLException
	 */
	private SPatientEntity setPatientByResultSet(ResultSet rs,Integer orgId) throws SQLException{
		String cardnum = rs.getString("cardnum");
		//如果是新儿童,且没有身份证号
		if((!CardNumUtil.isValidate18Idcard(cardnum)) && (((ValidateUtil.isEquals("岁",rs.getString("ageunit"))) && rs.getInt("age")<=9)||(!ValidateUtil.isEquals("岁", rs.getString("ageunit"))))){
			if(ValidateUtil.isNotNull(rs.getString("birthday"))){
				cardnum = "temp"+HashUtil.MD5Hashing(rs.getString("name"));
			}else{
				cardnum = "temp"+HashUtil.MD5Hashing(rs.getString("name")+rs.getString("birthday"));
			}
		}
		Map<String,Object> patientPara = new HashMap<>();
		patientPara.put("orgId", orgId);
		patientPara.put("cardnum",cardnum);
		SPatientEntity patient = patientMapper.getPatinet(patientPara);
		if(patient==null){			
			patient = new SPatientEntity();
			setPatientDataByResultSet(rs, orgId, cardnum, patient);
			patientMapper.setPatient(patient);
		}else{
			//更新患者
			setPatientDataByResultSet(rs, orgId, cardnum, patient);
			patientMapper.updatePatient(patient);
		}
		return patient;
	}
	
	/**
	 * 插入患者表(Element)
	 * @param element
	 * @param orgId
	 * @return
	 */
	private SPatientEntity setPatientByElement(Element element, Integer orgId) {
		String cardnum = element.element("cardnum").getText();
		//如果是新儿童,且没有身份证号
		if((!CardNumUtil.isValidate18Idcard(cardnum)) && (((ValidateUtil.isEquals("岁",element.element("ageunit").getText())) && Integer.valueOf(element.element("ageunit").getText())<=9)||(!ValidateUtil.isEquals("岁", element.element("ageunit").getText())))){
			if(ValidateUtil.isNotNull(element.element("birthday").getText())){
				cardnum = "temp"+HashUtil.MD5Hashing(element.element("name").getText());
			}else{
				cardnum = "temp"+HashUtil.MD5Hashing(element.element("name").getText()+element.element("birthday").getText());
			}
		}
		Map<String,Object> patientPara = new HashMap<>();
		patientPara.put("orgId", orgId);
		patientPara.put("cardnum",cardnum);
		SPatientEntity patient = patientMapper.getPatinet(patientPara);
		if(patient==null){			
			patient = new SPatientEntity();
			setPatientDataByElement(element, orgId, cardnum, patient);
			patientMapper.setPatient(patient);
		}else{
			//更新患者
			setPatientDataByElement(element, orgId, cardnum, patient);
			patientMapper.updatePatient(patient);
		}
		return patient;
	}
	
	
	/**
	 * 患者表赋值(Element)
	 * @param element
	 * @param orgId
	 * @param cardnum
	 * @param patient
	 */
	private void setPatientDataByElement(Element element, Integer orgId, String cardnum, SPatientEntity patient) {
		patient.setName(element.element("name").getText());
		patient.setAge(Integer.valueOf(element.element("age").getText()));
		patient.setCardnum(cardnum);
		patient.setSex(element.element("sex").getText());
		if(element.element("marry") != null){
			patient.setMarry(element.element("marry").getText());
		}
		if(element.element("profession") != null){
			patient.setProfession(element.element("profession").getText());
		}
		if(element.element("currentaddress") != null){
			patient.setCurrentaddress(element.element("currentaddress").getText());
		}
		if(element.element("teladdress") != null){
			patient.setTeladdress(element.element("teladdress").getText());
		}
		if(element.element("company") != null){
			patient.setCompany(element.element("company").getText());
		}
		if(element.element("education") != null){
			patient.setEducation(element.element("education").getText());
		}
		if(element.element("ageunit")!=null){
			patient.setAgeunit(element.element("ageunit").getText());
		}
		if(element.element("nation")!=null){
			patient.setNation(element.element("nation").getText());
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			patient.setBirthday(sdf.parse(element.element("birthday").getText()));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//patient.setPasthistory(element.element("").getText());
		//patient.setPermanenttype(element.element("permanenttype").getText());
		if(element.element("telname")!=null){
			patient.setRelativename(element.element("telname").getText());
		}
		if(element.element("relationphone")!=null){
			patient.setRelativephone(element.element("relationphone").getText());
		}
		if(element.element("patientphoneone")!=null){
			patient.setPhone(element.element("patientphoneone").getText());
		}
		//residenttype
		if(element.element("patientphonetwo")!=null){
			patient.setPhonetwo(element.element("patientphonetwo").getText());
		}
		if(element.element("bloodtype")!=null){
			patient.setBloodtype(element.element("bloodtype").getText());
		}
		//patient.setPhonethree(element.element("phonethree").getText());
		if(element.element("relation")!=null){
			patient.setRelation(element.element("relation").getText());
		}
		patient.setOrgId(orgId);
	
	}

	/**
	 * 患者表赋值(ResultSet)
	 * @param rs
	 * @param orgId
	 * @param cardnum
	 * @param patient
	 * @throws SQLException
	 */
	private void setPatientDataByResultSet(ResultSet rs, Integer orgId, String cardnum, SPatientEntity patient) throws SQLException {
		patient.setName(rs.getString("Name"));
		patient.setAge(rs.getInt("Age"));
		patient.setCardnum(cardnum);
		patient.setSex(rs.getString("Sex"));
		patient.setMarry(rs.getString("Marry"));
		patient.setProfession(rs.getString("Profession"));
		patient.setCurrentaddress(rs.getString("Currentaddress"));
		patient.setTeladdress(rs.getString("Teladdress"));
		patient.setCompany(rs.getString("Company"));
		patient.setEducation(rs.getString("Education"));
		patient.setAgeunit(rs.getString("Ageunit"));
		patient.setNation(rs.getString("Nation"));
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			patient.setBirthday(sdf.parse(rs.getString("Birthday")));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//patient.setPasthistory(rs.getString(""));
		//patient.setPermanenttype(rs.getString("permanenttype"));
		patient.setRelativename(rs.getString("Telname"));
		patient.setRelativephone(rs.getString("Relationphone"));
		//residenttype
		patient.setPhone(rs.getString("PATIENTPHONEONE"));
		patient.setPhonetwo(rs.getString("PATIENTPHONETWO"));
		patient.setBloodtype(rs.getString("bloodtype"));
		//patient.setPhonethree(rs.getString("phonethree"));
		patient.setRelation(rs.getString("Relation"));
		patient.setOrgId(orgId);
	}
	
	/**
	 * 住院信息表赋值(Element)
	 * @param element
	 * @param orgId
	 * @param status
	 * @param inhospitalDepartment
	 * @param s
	 */
	private void setInhospitalDateByElement(Element element, Integer orgId, Integer status,
			SDepartmentEntity inhospitalDepartment, SInhospitalEntity s,SUserEntity doctor,SUserEntity nurse) {
		//插入住院信息表
		s.setInhospitaldepartment(inhospitalDepartment.getName());
		s.setInhospitaldepartmentid(inhospitalDepartment.getId());
		s.setInhospitaldate(Timestamp.valueOf(element.element("inhospitaldate").getText()));
		if(doctor!=null){
			s.setMaindoctor(doctor.getName());
			s.setMaindoctorid(doctor.getId());
		}
		if(nurse!=null){
			s.setResponsibleNurseId(nurse.getId());
		}
		s.setInhospitalid(element.element("inhospitalid").getText());
		if(element.element("inhospitalcount")!=null){
			s.setInhospitalcount(Integer.valueOf(element.element("inhospitalcount").getText()));
		}
		if(element.element("inhospitalway")!=null){
			s.setInhospitalway(element.element("inhospitalway").getText());
		}
		if(element.element("inhospitaldescription")!=null){
			s.setInhospitaldescription(element.element("inhospitaldescription").getText());
		}
		if(element.element("inhospitaldiagnosis")!=null){
			s.setInhospitaldiagnosis(element.element("inhospitaldiagnosis").getText());
		}
		if(element.element("inhospitaldiagnosiscode")!=null){
			s.setInhospitaldiagnosiscode(element.element("inhospitaldiagnosiscode").getText());
		}
		if(element.element("drugallergy")!=null){
			s.setDrugallergy(element.element("drugallergy").getText());
		}
		if(element.element("allergydrug")!=null){
			s.setAllergydrug(element.element("allergydrug").getText());
		}
		if(element.element("visitnum")!=null){
			s.setVisitnum(element.element("visitnum").getText());
		}
		//s.setDoordoctor();
		s.setOrgid(orgId);
		//记录状态s.setRecordstate();
		//住院医嘱
		//s.setHospitalizationadvice(element.element("hospitalizationadvice"));
		//管理计划s.setDiseaseplanningstatus();
		if(element.element("inpatientarea")!=null){
			s.setInpatientarea(element.element("inpatientarea").getText());
		}
		if(element.element("inpatientward")!=null){
			s.setInpatientward(element.element("inpatientward").getText());
		}
		if(element.element("hospitalbed")!=null){
			s.setHospitalbed(element.element("hospitalbed").getText());
		}
		if(element.element("costtype")!=null){
			s.setCosttype(element.element("costtype").getText());
		}
		s.setSchedulingstate(1);
		if(element.element("inhospitaldays")!=null&&!element.element("inhospitaldays").getText().equals("")){
			s.setInhospitaldays(Integer.valueOf(element.element("inhospitaldays").getText()));
		}
		
		/**
		 * 院后
		 */
		if(status==2){
			//出院时间
			if(element.element("outhospitaldatehome")!=null&&ValidateUtil.isNotNull(element.element("outhospitaldatehome").getText())){
				s.setOuthospitaldate(Timestamp.valueOf(element.element("outhospitaldatehome").getText()+ "00:00:00"));
			}
			//出院结算时间
			s.setOuthospitaldateclose(Timestamp.valueOf(element.element("outhospitaldateclose").getText()));
			//出院情况
			if(element.element("outhospitaldescription")!=null){
				s.setOuthospitaldescription(element.element("outhospitaldescription").getText());
			}
			//出院诊断
			if(element.element("outhospitaldiagnose")!=null){
				s.setOuthospitaldiagnose(element.element("outhospitaldiagnose").getText());
			}
			//出院诊断ICD
			if(element.element("outhospitaldiagnoseicd")!=null){
				s.setOuthospitaldiagnoseicd(element.element("outhospitaldiagnoseicd").getText());
			}
			s.setInhospitalstatus(2);
			//院中管理状态(出院 自动设为2已完成)
			s.setManagestate(2);
		}else{
			//院中管理状态(应设为 0未标记暂设为1未完成)
			s.setManagestate(1);
		}
		if(element.element("patientid_his")!=null){
			s.setPatientHisId(element.element("patientid_his").getText());
		}
	}
	
	/**
	 * 住院信息表赋值(ResultSet)
	 * @param rs
	 * @param orgId
	 * @param status
	 * @param inhospitalDepartment
	 * @param s
	 * @param doctor
	 * @throws SQLException
	 */
	private void setInhospitalDateByResultSet(ResultSet rs, Integer orgId, Integer status, SDepartmentEntity inhospitalDepartment,
			SInhospitalEntity s,SUserEntity doctor,SUserEntity nurse) throws SQLException {
		//插入住院信息表
		s.setInhospitaldepartment(inhospitalDepartment.getName());
		s.setInhospitaldepartmentid(inhospitalDepartment.getId());
		s.setInhospitaldate(Timestamp.valueOf(rs.getString("INHOSPITALDATE")));
		if(doctor!=null){
			s.setMaindoctor(doctor.getName());
			s.setMaindoctorid(doctor.getId());
		}
		if(nurse!=null){
			s.setResponsibleNurseId(nurse.getId());
		}
		s.setInhospitalid(rs.getString("inhospitalid"));
		s.setInhospitalcount(rs.getInt("inhospitalcount"));
		s.setInhospitalway(rs.getString("inhospitalway"));
		s.setInhospitaldescription(rs.getString("inhospitaldescription"));
		s.setInhospitaldiagnosis(rs.getString("inhospitaldiagnosis"));
		s.setInhospitaldiagnosiscode(rs.getString("inhospitaldiagnosiscode"));
		s.setDrugallergy(rs.getString("drugallergy"));
		s.setAllergydrug(rs.getString("allergydrug"));
		s.setVisitnum(rs.getString("visitnum"));
		//s.setDoordoctor();
		s.setOrgid(orgId);
		//记录状态s.setRecordstate();
		//住院医嘱
		//s.setHospitalizationadvice(rs.getString("hospitalizationadvice"));
		//管理计划s.setDiseaseplanningstatus();
		s.setInpatientarea(rs.getString("inpatientarea"));
		s.setInpatientward(rs.getString("inpatientward"));
		s.setHospitalbed(rs.getString("hospitalbed"));
		s.setCosttype(rs.getString("costtype"));
		s.setSchedulingstate(1);
		s.setInhospitaldays(rs.getInt("inhospitaldays"));
		//管理状态(暂时自动分配)
		s.setManagestate(1);
		
		/**
		 * 院后
		 */
		if(status==2){
			s.setOuthospitaldate(Timestamp.valueOf(rs.getString("OUTHOSPITALDATEHOME")));
			s.setOuthospitaldescription(rs.getString("outhospitaldescription"));
			s.setInhospitalstatus(2);
			//出院科室s.setOuthospitaldepartmentid();
		}
		s.setPatientHisId(rs.getString("PATIENTID_HIS"));
	}
}
