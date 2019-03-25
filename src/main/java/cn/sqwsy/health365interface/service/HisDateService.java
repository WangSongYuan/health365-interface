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
	
	public void sysHisByXml(List<Element> list,int status){
		
	}
	
	public void sysHisByJson(List<Element> list){
		for (int i = 0; i < list.size(); i++) {
			
		}
	}
	
	/**
	 * 开始抓取数据
	 * @param rs
	 * @throws SQLException
	 */
	public void startGrabData(ResultSet rs,Map<String,Object> para,Integer orgId,Integer status) throws SQLException{
		if(status==1){
			//插入入院科室表
    		SDepartmentEntity inhospitalDepartment = setDepartmentByResultSet(rs,orgId,status);
    		
    		//无需管理
    		if(inhospitalDepartment.getState()==2){
    			return;
    		}
    		
    		//机构
    		Map<String,Object> orgPara = new HashMap<>();
    		orgPara.put("id", orgId);
    		SOrgEntity org = orgMapper.getOrg(orgPara);
    		
			SInhospitalEntity s = inhospitalMapper.getInhospital(para);
	    	if(s==null){
	    		s = new SInhospitalEntity();
	    		validData(rs, s);
	    		//插入医生用户表
	    		SUserEntity doctor = setDoctor(rs, inhospitalDepartment, org);
	    		//插入护士相关
	    		setNurse(rs, orgId, inhospitalDepartment, org, s);
	    		
	    		//验证通过的情况下插入患者表
	    		if(s.getIsValid()==1){
	    			SPatientEntity patient = setPatient(rs, orgId);
	    			s.setsPatientEntity(patient);
	    		}
	    		
	    		//住院信息赋值
	    		setInhospital(rs, orgId, status, inhospitalDepartment, s, doctor);
	    		inhospitalMapper.setInhospital(s);
	    		if(status==1){
	    			//插入住院板块完成度表
	    			setInhosipitalplate(s,inhospitalDepartment.getId());
	    		}
	    	}else{
	    		validData(rs, s);
	    		//插入医生用户表
	    		SUserEntity doctor = setDoctor(rs,inhospitalDepartment, org);
	    		setNurse(rs, orgId, inhospitalDepartment, org, s);
	    		//验证通过的情况下插入患者表
	    		if(s.getIsValid()==1){
	    			SPatientEntity patient = setPatient(rs, orgId);
	    			s.setsPatientEntity(patient);
	    		}
	    		//住院信息赋值
	    		setInhospital(rs, orgId, status, inhospitalDepartment, s, doctor);
	    		inhospitalMapper.updateInhospital(s);
	    	}
		}else if(status==2){
			
		}
	}

	private void setNurse(ResultSet rs, Integer orgId, SDepartmentEntity inhospitalDepartment, SOrgEntity org,
			SInhospitalEntity s) throws SQLException {
		//护士相关信息
		if(ValidateUtil.isNotNull(rs.getString("nursename"))&&ValidateUtil.isNotNull(rs.getString("nurseno"))){
			//插入护士用户表
			SUserEntity nurse = setUserByResultSet(rs, 2);
			//主管护师角色
			SRoleEntity nurseRole = getUserRole(org, nurse,5);
			//插入护士角色关联表
			setUserRole(nurse, nurseRole);
			//插入护士用户机构科室关联表
			setUserOrg(nurse, org, inhospitalDepartment);
			//护士关联住院表
			s.setResponsibleNurseId(nurseRole.getId());
		}
	}

	private void setInhospital(ResultSet rs, Integer orgId, Integer status, SDepartmentEntity inhospitalDepartment,
			SInhospitalEntity s, SUserEntity doctor) throws SQLException {
		//插入住院信息表
		s.setInhospitaldepartment(inhospitalDepartment.getName());
		s.setInhospitaldepartmentid(inhospitalDepartment.getId());
		s.setInhospitaldate(Timestamp.valueOf(rs.getString("INHOSPITALDATE")));
		s.setMaindoctor(doctor.getName());
		s.setMaindoctorid(doctor.getId());
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
		/**
		 * 任务分配
		 */
		SUserEntity randDiseaseManager = userMapper.getOrderDiseaseManager(inhospitalDepartment.getId());
		s.setInDiseaseManagerId(randDiseaseManager.getId());
	}

	private SUserEntity setDoctor(ResultSet rs,SDepartmentEntity inhospitalDepartment, SOrgEntity org)
			throws SQLException {
		SUserEntity doctor = setUserByResultSet(rs,1);
		SRoleEntity doctorRole = getUserRole(org, doctor,4);
		//插入医生角色关联表
		setUserRole(doctor, doctorRole);
		//插入医生用户机构科室关联表
		setUserOrg(doctor, org, inhospitalDepartment);
		return doctor;
	}

	/**
	 * 获取用户角色
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

	private void validData(ResultSet rs, SInhospitalEntity s) throws SQLException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式  注意身份证信息为空
		StringBuffer ap = new StringBuffer( "当前时间："+df.format(new Date())+"您有新的消息:</br>科室:"+rs.getString("inhospitaldepartmentid")+",主治医师:"+rs.getString("maindoctorname")+",患者姓名:"+rs.getString("name")+"</br>住院号:"+rs.getString("inhospitalid")+",住院次数:"+rs.getString("inhospitalcount")+"</br>错误信息如下:</br>");
		if(ValidateUtil.isNull(rs.getString("cardnum"))&&ValidateUtil.isEquals("岁",rs.getString("ageunit"))&& rs.getInt("age")>9){
			System.out.println("身份证为空");
			ap.append("</br>身份证号为空</br>");
			s.setErrorMsg(ap.toString());
		}else if(ValidateUtil.isEquals("岁", rs.getString("ageunit")) && rs.getInt("age")>9 && !CardNumUtil.isValidate18Idcard(rs.getString("cardnum"))){
			System.out.println("身份证填写错误");
			ap.append("</br>身份证号填写错误</br>");
			s.setErrorMsg(ap.toString());
		}else{
			s.setIsValid(1);
		}
	}

	/**
	 * 插住院板块完成度表及治疗方案/处方/指导表s_programme
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
	 * 插入科室
	 * @param rs
	 * @throws SQLException
	 */
	public SDepartmentEntity setDepartmentByResultSet(ResultSet rs,Integer orgId,Integer status) throws SQLException{
		SDepartmentEntity department =null;
		if(status==1&&ValidateUtil.isNotNull(rs.getString("INHOSPITALDEPARTMENTID"))&&ValidateUtil.isNotNull(rs.getString("INHOSPITALDEPARTMENT"))){
			//院中
			department = departmentMapper.getDepartmentByHisId(rs.getString("INHOSPITALDEPARTMENTID"));
			if(department==null){
				department = new SDepartmentEntity();
				setDepartmentData(rs, orgId, department);
				departmentMapper.setDepartment(department);
				Map<String,Object> para = new HashMap<>();
				para.put("state", 1);//查询板块是否启用
				List<SPlateEntity> list = plateMapper.getAllPlate(para);
				for(SPlateEntity plate:list){
					setDepartmentPlate(department, plate);
				}
			}else{
				setDepartmentData(rs, orgId, department);
				departmentMapper.updateDepartment(department);
			}
			
		}else if(status==2&&ValidateUtil.isNotNull(rs.getString("OUTHOSPITALDEPARTMENTID"))&&ValidateUtil.isNotNull(rs.getString("OUTHOSPITALDEPARTMENT"))){
			//院后
			department = departmentMapper.getDepartmentByHisId(rs.getString("OUTHOSPITALDEPARTMENTID"));
			/*if(department==null){
				department = new SDepartmentEntity();
				department.setOrgid(orgId);
				department.setType(1);//章丘默认住院科室type=1
				department.setName(rs.getString("INHOSPITALDEPARTMENT"));
				department.setThirdpartyhisid(rs.getString("INHOSPITALDEPARTMENTID"));
				departmentMapper.setDepartment(department);
				System.out.println(department.getId());
				Map<String,Object> para = new HashMap<>();
				para.put("state", 1);//查询板块是否启用
				List<SPlateEntity> list = plateMapper.getAllPlate(para);
				for(SPlateEntity plate:list){
					setDepartmentPlate(department, plate);
				}
			}else{
				departmentMapper.updateDepartment(department);
			}*/
		}
		return department;
	}

	/**
	 * 科室赋值
	 * @param rs
	 * @param orgId
	 * @param department
	 * @throws SQLException
	 */
	private void setDepartmentData(ResultSet rs, Integer orgId, SDepartmentEntity department) throws SQLException {
		department.setOrgid(orgId);
		department.setType(1);//章丘默认住院科室type=1
		department.setName(rs.getString("INHOSPITALDEPARTMENT"));
		department.setThirdpartyhisid(rs.getString("INHOSPITALDEPARTMENTID"));
		//章丘默认管理两个科室
		if(rs.getString("INHOSPITALDEPARTMENTID").equals("0602")||rs.getString("INHOSPITALDEPARTMENTID").equals("0609")){
			department.setState(1);
		}else{
			department.setState(2);//默认不管理
		}
	}

	/**
	 * 插入科室板块关联表
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
	 * 插入用户
	 * @param rs
	 * @throws SQLException
	 */
	private SUserEntity setUserByResultSet(ResultSet rs,Integer userType) throws SQLException{
		Map<String,Object> userPara = new HashMap<>();
		if(userType==1){
			userPara.put("thirdpartyhisid", rs.getString("maindoctorid"));
		}else{
			userPara.put("thirdpartyhisid", rs.getString("nurseno"));
		}
		SUserEntity user = userMapper.getUser(userPara);
		if(user==null){
			user = new SUserEntity();
			setUserData(rs, user);
			userMapper.setUser(user);
		}else{
			//更新用户
			setUserData(rs, user);
			userMapper.updateUser(user);
		}
		return user;
	}

	private void setUserData(ResultSet rs, SUserEntity user) throws SQLException {
		user.setName(rs.getString("MAINDOCTORNAME"));
		user.setJobnum(rs.getString("MAINDOCTORID"));
		user.setThirdpartyhisid(rs.getString("MAINDOCTORID"));
		user.setPassword("123456");
	}
	
	/**
	 * 插入用户角色关联表
	 * @param rs
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
	 * 插入用户机构关联表
	 * @param rs
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
	 * 插入患者表
	 * @param rs
	 * @throws SQLException
	 */
	private SPatientEntity setPatient(ResultSet rs,Integer orgId) throws SQLException{
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
			setPatientData(rs, orgId, cardnum, patient);
			patientMapper.setPatient(patient);
		}else{
			//更新患者
			setPatientData(rs, orgId, cardnum, patient);
			patientMapper.updatePatient(patient);
		}
		return patient;
	}

	private void setPatientData(ResultSet rs, Integer orgId, String cardnum, SPatientEntity patient) throws SQLException {
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
}
