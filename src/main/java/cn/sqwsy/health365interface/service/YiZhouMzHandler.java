package cn.sqwsy.health365interface.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import cn.sqwsy.health365interface.dao.entity.SDepartmentEntity;
import cn.sqwsy.health365interface.dao.entity.SOrgEntity;
import cn.sqwsy.health365interface.dao.entity.SOutpatientServiceInfoEntity;
import cn.sqwsy.health365interface.dao.entity.SPatientEntity;
import cn.sqwsy.health365interface.dao.entity.SUserEntity;
import cn.sqwsy.health365interface.dao.entity.SUserorgEntity;
import cn.sqwsy.health365interface.dao.mapper.DepartmentMapper;
import cn.sqwsy.health365interface.dao.mapper.MzMapper;
import cn.sqwsy.health365interface.dao.mapper.OrgMapper;
import cn.sqwsy.health365interface.dao.mapper.PatientMapper;
import cn.sqwsy.health365interface.dao.mapper.RoleMapper;
import cn.sqwsy.health365interface.dao.mapper.UserMapper;
import cn.sqwsy.health365interface.dao.mapper.UserOrgMapper;
import cn.sqwsy.health365interface.service.utils.CardNumUtil;
import cn.sqwsy.health365interface.service.utils.DateUtil;
import cn.sqwsy.health365interface.service.utils.HashUtil;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;
@Component
public class YiZhouMzHandler {
	
	
	@Bean
	public TaskScheduler taskScheduler() {
	    ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
	    taskScheduler.setPoolSize(50);
	    return taskScheduler;
	}
	private static final int AGE = 9;
	
	SOrgEntity org = null;
	
	@Autowired
	public OrgMapper orgMapper;
	
	@Autowired
	private DepartmentMapper departMentMapper;
	
	@Autowired
	private UserMapper userInfoMapper;
	
	@Autowired
	private PatientMapper patientMapper;
	
	@Autowired
	private MzMapper mzMapper;
	
	@Autowired
	public UserOrgMapper userOrgMapper;
	
	@Autowired
	public UserMapper userMapper;
	
	@Autowired
	public RoleMapper roleMapper;
	
	@Scheduled(cron="0 0 12,18,21 * * ?")
	public void fixedRateJob() {
		System.out.println("yizhou mz start");
		Connection conn = getConn();
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		try {
			//设置医院基本信息
			Map<String,Object> orgPara = new HashMap<>();
			orgPara.put("id", 1);
			org = orgMapper.getOrg(orgPara);
			if(org==null){
				org = new SOrgEntity();
				org.setName("宜州区中医院");
				orgMapper.setOrg(org);
			}
			
			//设置管理员科室门诊关联权限
			Map<String,Object> userPara = new HashMap<>();
			userPara.put("jobnum", "2");
			SUserEntity user = userMapper.getUser(userPara);
			if(user!=null){
				Map<String,Object> departmentPara = new HashMap<>();
				departmentPara.put("orgId", 1);
				departmentPara.put("typeId", 2);
				//所有门诊科室
				List<SDepartmentEntity> departments = departMentMapper.getDepartmentsList(departmentPara);
				
				for(SDepartmentEntity department:departments){
					//用户机构科室关联表
					Map<String,Object> userOrgPara = new HashMap<>();
					userOrgPara.put("userid", user.getId());
					userOrgPara.put("departmentid", department.getId());
					userOrgPara.put("orgid", org.getId());
					userOrgPara.put("typeId", 3);
					SUserorgEntity userOrg = userOrgMapper.getUserOrg(userOrgPara);
					if(userOrg==null){
						userOrg = new SUserorgEntity();
						userOrg.setDepartmentid(department.getId());
						userOrg.setOrgid(org.getId());
						userOrg.setUserid(user.getId());
						userOrg.setTypeId(3);//门诊
						userOrgMapper.setUserOrg(userOrg);
					}
				}
			}
			
			Calendar outStartCal = Calendar.getInstance(); 
			outStartCal.add(Calendar.DAY_OF_MONTH,0);
			outStartCal.set(Calendar.HOUR_OF_DAY, 0); 
			outStartCal.set(Calendar.SECOND, 0); 
			outStartCal.set(Calendar.MINUTE, 0); 
			Date outSstartTime = outStartCal.getTime();
			String starttime = DateUtil.format(outSstartTime, "yyyy-MM-dd");
			String sql = "SELECT * FROM REPORT.dbo.JKGL_MZJL  where starttime >= '"+starttime+" 00:00:00' AND starttime <= '"+starttime+" 23:59:59' AND status!='未就诊'";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			rst = pstmt.executeQuery();
			while (rst.next()) {
				//是否有效
				boolean status = true;
				
				//门诊唯一标识
				String mz_code = rst.getString("mz_code");
				
				if(ValidateUtil.isNull(mz_code)){
					continue;
				}
				
				//门诊科室Id(第三方)HISID √
				String visit_dept = rst.getString("departmentid");
				/*
				 * 门诊科室√
				 */
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("thirdpartyhisid", visit_dept);
				params.put("orgId", org.getId());
				SDepartmentEntity department = departMentMapper.getDepartment(params);
				SDepartmentEntity d = new SDepartmentEntity();
				
				if(department!=null){//如果科室存在
					d = department;
					d.setName(rst.getString("departmentname"));
					d.setType(2);
					departMentMapper.updateDepartment(d);
				}else{//如果科室不存在
					d.setThirdpartyhisid(visit_dept);
					d.setName(rst.getString("departmentname"));//科室名称
					d.setState(2);//默认不管理
					d.setType(2);//wangsongyuan 新增科室类别 20180608
					d.setOrgid(org.getId());
					departMentMapper.setDepartment(d);
				}
				
				//无需管理
				if(d!=null&&d.getState()==2){
					continue;
				}
				
				SOutpatientServiceInfoEntity mz = new SOutpatientServiceInfoEntity();
				
				//就诊日期√
				String visit_date = rst.getString("starttime");
				
				StringBuffer sb = new StringBuffer();
				sb.append("</br>错误信息如下:</br>");				
				
				Timestamp visit_dateTimesTamp = null;
				if (ValidateUtil.isNull(visit_date)) {
					sb.append("</br>门诊日期为空！</br>");
					status = false;
				}else{
					Date visit_dateDate = DateUtil.parse(visit_date, "yyyy-MM-dd");
					visit_dateTimesTamp = DateUtil.getSqlTimestamp(visit_dateDate);
					mz.setVisit_date(visit_dateTimesTamp);
				}
				
				Map<String, Object> para = new HashMap<>();
				para.put("mz_code", mz_code);
				para.put("orgId", org.getId());
			    SOutpatientServiceInfoEntity mzOld = mzMapper.getMz(para);
				if (mzOld != null) {
					//系统已经存在这条数据
					mz = mzOld;
				}else{
					//排期状态
					mz.setSchedulingState(1);
					//医院ID
					mz.setOrgid(org.getId());
					mz.setMz_code(mz_code);
				}
				
				//门诊卡号√
				String card_no = rst.getString("card_no");
				mz.setCard_no(card_no);
				
				/**
				 * 患者信息
				 */
				
				//身份证号√
				String cardnum = rst.getString("cardnum");
				
				// 姓名√
				String name = rst.getString("name");
				
				//生日√
				Timestamp newbirthday =null;
				String birthday = rst.getString("birthday");
				if (ValidateUtil.isNotNull(birthday)) {
					SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
			        DateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");   
			        birthday = sf2.format(sf1.parse(birthday));
			        birthday = birthday+ " 00:00:00";
			        newbirthday = Timestamp.valueOf(birthday);
				}	
				
				//年龄√
				int age  = getAgeByCardNum(cardnum,newbirthday);
				
				SPatientEntity  patient = null;
				SPatientEntity  patientOld =null;
				SPatientEntity  patientNew =null;
				//如果是儿童
				if(age<=AGE){
					String oldCardNum="";
					if(newbirthday==null){
						oldCardNum = "temp"+HashUtil.MD5Hashing(name);
					}else{
						oldCardNum = "temp"+HashUtil.MD5Hashing(name+DateUtil.format(newbirthday, "yyyyMMdd"));
					}
					//身份证正确
					if(CardNumUtil.isValidate18Idcard(cardnum)){
						para = new HashMap<>();
						para.put("cardnum", cardnum);
						para.put("orgId", org.getId());
						patientOld = patientMapper.getPatient(para);
						//存在
						if(patientOld!=null){
							para = new HashMap<>();
							para.put("cardnum", cardnum);
							para.put("orgId", org.getId());
							patientNew = patientMapper.getPatient(para);
							if(patientNew==null){
								patient=patientOld;
								setPatientData(rst, cardnum, name, newbirthday, age, patient);
							}else{
								//身份证儿童匹配成功
								patient=patientNew;
								setPatientData(rst, cardnum, name, newbirthday, age, patient);
							}
						
						}else{//不存在
							para = new HashMap<>();
							para.put("cardnum", cardnum);
							para.put("orgId", org.getId());
							patientNew = patientMapper.getPatient(para);
							if(patientNew==null){
								patient = new SPatientEntity();
								setPatientData(rst, cardnum, name, newbirthday, age, patient);
							}else{
								patient = patientNew;
								setPatientData(rst, cardnum, name, newbirthday, age, patient);
							}
						}
					}else{//身份证不正确
						para = new HashMap<>();
						para.put("cardnum", oldCardNum);
						para.put("orgId", org.getId());
						patientOld = patientMapper.getPatient(para);
						if(patientOld!=null){
							patient = patientOld;
							setPatientData(rst, oldCardNum, name, newbirthday, age, patient);
						}else{
							patient = new SPatientEntity();
							setPatientData(rst, oldCardNum, name, newbirthday, age, patient);
						}
					}
				}else{
					//如果不是小孩且身份证号为空
					if(ValidateUtil.isNull(cardnum)){
						sb.append("</br>身份证号码为空！</br>");
						cardnum="";
						status = false;
					}else if(!CardNumUtil.isValidate18Idcard(cardnum)&&age>AGE ){
						sb.append("</br>门诊患者身份证校验失败！</br>");
						status = false;
					}else{
						para = new HashMap<>();
						para.put("cardnum", cardnum);
						para.put("orgId", org.getId());
						patientOld = patientMapper.getPatient(para);
						if (patientOld != null) {
							patient = patientOld;
							setPatientData(rst, cardnum, name, newbirthday, age, patient);
						}else{
							patient = new SPatientEntity();
							setPatientData(rst, cardnum, name, newbirthday, age, patient);
						}	
					}
				}
				
				//手机号为空
				if(ValidateUtil.isNull(rst.getString("patientphoneone")) && ValidateUtil.isNull(rst.getString("relationphone"))){
					sb.append("</br>手机号为空</br>");
					status = false;
				}else{
					//手机号错误
					if(!isNum(rst.getString("patientphoneone")) && !isNum(rst.getString("relationphone"))){
						sb.append("</br>手机号填写错误</br>");
						status = false;
					}
				}
				
				//门诊医师ID√
				String doctor_no = rst.getString("doordoctorid");
				
				//门诊医师姓名√
				String doctor_name =rst.getString("doordoctorname");
				
				/*
				 * 门诊医生√
				 */
				SUserEntity doctor = new SUserEntity();
				para = new HashMap<>();
				para.put("thirdpartyhisid", doctor_no);
				SUserEntity oldDoctor = userInfoMapper.getUser(para);
				if (oldDoctor != null) {//如果医生存在
					doctor = oldDoctor;
					//根据用户id获取用户和科室的中间表
					Map<String,Object> parms = new HashMap<>(3);
					parms.put("userid", doctor.getId());
					parms.put("departmentid", d.getId());
					parms.put("orgid", org.getId());
					parms.put("typeId", 3);//门诊
					SUserorgEntity dmrinfo = userOrgMapper.getUserOrg(parms);
					if(dmrinfo==null){//科室权限不存在就创建
						SUserorgEntity userOrg = new SUserorgEntity();
						userOrg.setDepartmentid(d.getId());
						userOrg.setOrgid(org.getId());
						userOrg.setUserid(doctor.getId());
						userOrg.setTypeId(3);
						userOrgMapper.setUserOrg(userOrg);
					}else{//科室权限存在就更新

					}
				}else if(oldDoctor == null){//如果医生不存在
					doctor.setName(doctor_name);
					doctor.setJobnum(doctor_no);
					doctor.setThirdpartyhisid(doctor_no);
					doctor.setPassword("123456");
					userMapper.setUser(doctor);//创建新医生
					//科室权限
					SUserorgEntity userOrg = new SUserorgEntity();
					userOrg.setDepartmentid(d.getId());
					userOrg.setOrgid(org.getId());
					userOrg.setUserid(doctor.getId());
					userOrg.setTypeId(3);
					userOrgMapper.setUserOrg(userOrg);
				}
				mz.setDoctor_no(doctor_no);//门诊医师Id(第三方)HIS
				mz.setDoctorinfo(doctor);////用户ID(也是门诊医生信息数据)
				mz.setVisit_dept(visit_dept);//门诊科室赋值
				//创建时间√
				/*String createtime = rst.getString("createtime");
				if (ValidateUtil.isNotNull(createtime)) {
					Date createtimeDate = DateUtil.parse(createtime, "yyyy-MM-dd HH:mm:ss");
					Timestamp createtimeDateTimesTamp = DateUtil.getSqlTimestamp(createtimeDate);
					patient.setCreatetime(createtimeDateTimesTamp);
				}*/

				//更新时间√
				/*String updatetime = rst.getString("updatetime");
				if (ValidateUtil.isNotNull(updatetime)) {
					Date updatetimeDate = DateUtil.parse(updatetime, "yyyy-MM-dd HH:mm:ss");
					Timestamp updatetimeDateTimesTamp = DateUtil.getSqlTimestamp(updatetimeDate);
					patient.setUpdatetime(updatetimeDateTimesTamp);
				}*/
				
				//总花费√
				String costs = rst.getString("totalcost");
				mz.setCosts(costs);
				
				//门诊诊断√
				String diag_desc = rst.getString("diag_desc");
				mz.setDiag_desc(diag_desc);
				
				mz.setClinic_label(rst.getString("clinic_label"));//号别
				
				mz.setDepartment(d);
				
				/**
				 * V2.1新增单病种管理start
				 */
				
				//管理类型  1.正常排期管理2.感染病管理3.未知（例：慢性病管理  例：风险性管理等）
				if(true){
					mz.setManagertype(1);
				}
				
				//病种表ID
				if(true){
					mz.setChronicDiseaseId(null);
				}
				
				//确诊情况 1.未确诊2.疑似3.确诊
				if(true){
					mz.setConfiredCases(3);
				}
				/**
				 * V2.1新增单病种管理end
				 */
				
				Timestamp updateTime = DateUtil.getSqlTimestamp(new Date());//更新时间

				//数据有效时再更新信息
				if(status==false){//无效
					mz.setErrorMsg(sb.toString());
					mz.setIsValid(0);
				}else if(status==true){//有效
					mz.setIsValid(1);
					if (patientOld != null||patientNew!=null) {
						patient.setUpdatetime(updateTime);
						patientMapper.updatePatient(patient);
					}else {
						patientMapper.setPatient(patient);
					}
					mz.setPatient(patient);
					mz.setCardnum(patient.getCardnum());
				}
				
				mz.setUserinfo(doctor);
				
				mz.setUpdateTime(updateTime);
				if(mzOld!=null){
					mzMapper.updateMz(mz);
				}else{
					mzMapper.setMz(mz);
				}
			}
			System.out.println("yizhou mz end");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rst != null)
					rst.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}
	
	private static boolean isNum(String str) {
		if(str==null||str.equals("")){
			return false;
		}
	    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	    return pattern.matcher(str).matches();  
	}

	/**
	 * 根据身份证号码获取年龄
	 * 
	 * @param CardNum
	 * @param newbirthday
	 * @return
	 */
	public static int getAgeByCardNum(String CardNum, Date newbirthday) {
		int age = 0;
		try {
			if (CardNumUtil.isValidate18Idcard(CardNum) && !CardNum.substring(4).equals("temp")) {
				String year = CardNum.substring(6).substring(0, 4);// 得到年份
				String yue = CardNum.substring(10).substring(0, 2);// 得到月份
				Date date = new Date();// 得到当前的系统时间
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String fyear = format.format(date).substring(0, 4);// 当前年份
				String fyue = format.format(date).substring(5, 7);// 月份
				if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
					age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
				} else {// 当前用户还没过生
					age = Integer.parseInt(fyear) - Integer.parseInt(year);
				}
			} else {
				age = getAgeByBirth(newbirthday);
			}
			return age;
		} catch (Exception e) {
			// System.out.println("身份证号码不合法：【"+CardNum+"】");
			e.printStackTrace();
			age = getAgeByBirth(newbirthday);
			return age;
			// System.out.println("尝试使用生日获取年龄...【"+age+"】");
		}
	}

	/**
	 * 根据生日获取年龄
	 * 
	 * @param birthday
	 * @return
	 */
	public static int getAgeByBirth(Date birthday) {
		int age = 0;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());// 当前时间

			Calendar birth = Calendar.getInstance();
			birth.setTime(birthday);

			if (birth.after(now)) {// 如果传入的时间，在当前时间的后面，返回0岁
				age = 0;
			} else {
				age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
				if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
					age += 1;
				}
			}
			return age;
		} catch (Exception e) {// 兼容性更强,异常后返回数据
			e.printStackTrace();
			return 0;
		}
	}

	private static Connection getConn() {
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbUser = "jkgl";
		String dbPassword = "123456";
		String url ="jdbc:sqlserver://172.17.106.6;DatabaseName=REPORT;";
	    Connection conn = null;
	    try {
	        Class.forName(driver);
	        conn =  DriverManager.getConnection(url, dbUser, dbPassword);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	private void setPatientData(ResultSet rst, String cardnum, String name, Timestamp newbirthday, int age,
			SPatientEntity  patient) throws SQLException {
		//年龄√
		patient.setAge(age);
		//年龄单位√
		patient.setAgeunit(rst.getString("ageunit"));
		
		
		//设置患者身份证号码		
		patient.setCardnum(cardnum);	
		//生日
		patient.setBirthday(newbirthday);
		
		// 民族√
		String nation = rst.getString("nation");
		if(nation!=null&&!nation.equals("")){
			patient.setNation(nation);
		}		
		
		//设置患者姓名
		patient.setName(name.trim());
		
		//性别√
		patient.setSex(rst.getString("sex"));
		
		//婚姻状况X
		String marry = rst.getString("marry");
		if(marry!=null&&!marry.equals("")){
			patient.setMarry(marry);
		}
		
		//职业√
		String profession = rst.getString("profession");
		if(profession!=null&&!profession.equals("")){
			patient.setProfession(profession);
		}
		
		//现住址√
		String currentaddress = rst.getString("currentaddress");
		if(currentaddress!=null&&!currentaddress.equals("")){
			patient.setCurrentaddress(currentaddress);
		}
		
		//联系住址
		String teladdress = rst.getString("teladdress");
		if(teladdress!=null&&!teladdress.equals("")){
			patient.setTeladdress(teladdress);
		}
		
		//工作单位
		String company = rst.getString("company");
		if(company!=null&&!company.equals("")){
			patient.setCompany(company);
		}
		
		//教育程度
		String education = rst.getString("education");
		if(education!=null&&!education.equals("")){
			patient.setEducation(education);
		}
		
		//病人电话√
		String phoneOne = rst.getString("patientphoneone");
		if(ValidateUtil.isNotNull(phoneOne)){
			patient.setPhone(phoneOne);
		}

		//联系人名称
		patient.setRelativename(rst.getString("telname"));
		
		//联系人电话√
		String relationPhone = rst.getString("companyphone");
		if(ValidateUtil.isNotNull(relationPhone)){
			patient.setRelativephone(relationPhone);
		}
		
		//联系关系
		patient.setRelation(rst.getString("relation"));
		
		//单位电话√
		String companyphone = rst.getString("companyphone");
		if(companyphone!=null&&!companyphone.equals("")){
			patient.setCompanyphone(companyphone);
		}
		
		patient.setOrgId(org.getId());
	}
}
