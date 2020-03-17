package cn.sqwsy.health365interface.service;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.sqwsy.health365interface.dao.entity.SDepartmentEntity;
import cn.sqwsy.health365interface.dao.entity.SInhospitalEntity;
import cn.sqwsy.health365interface.dao.entity.SOrgEntity;
import cn.sqwsy.health365interface.dao.entity.SRoleEntity;
import cn.sqwsy.health365interface.dao.entity.SUserEntity;
import cn.sqwsy.health365interface.dao.entity.SUserorgEntity;
import cn.sqwsy.health365interface.dao.entity.SUserroleEntity;
import cn.sqwsy.health365interface.dao.entity.SelctronicMedicalRecordEntity;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

@Component
public class YiZhouHisHandler extends HisDateService{

	SOrgEntity org = null;

	@Scheduled(fixedDelay = 60000)
	public void fixedRateJob() {
		Connection conn = getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
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
			//插入科室
			setDepartments(conn);
			
			//默认医院管理员创建
			setdefaultAdmin();			
			
			//默认院中疾病与健康管理中心管理员创建
			setdefaultInJbglAdmin();
			
			//默认院后疾病与健康管理中心管理员创建
			setdefaultOutJbglAdmin();
			
			//默认院后疾病管理师创建
			setdefaultOutJbgl();
			
			//院中数据
		    //String sql = "select * from V_GETOUTHOSPITALLIST where INHOSPITALSTATUS =1";
			//1对接视图TODO
			String sql = "SELECT inhospitaldepartment, inhospitaldepartmentid, inhospitaldays, inhospitaldate, totalcost, maindoctorname, maindoctorid, doordoctorname, doordoctorid, costtype, inhospitalid, visitnum, inhospitalcount, outhospitrecordid, inhospitaldescription, age, ageunit, nation, NAME, phone, birthday, sex, cardnum, inpatientarea, hospitalbed, zy_code, nursename, responsibleNurseId, inhospitalway, inhospitaldiagnosis, inhospitaldiagnosiscode, drugallergy, allergydrug, inpatientward, marry, profession, currentaddress, teladdress, company, education, relativename, relativephone, phonetwo, companyphone, bloodtype, relation FROM REPORT.dbo.JKGL_CZYJL WHERE inhospitalstatus = 1 ORDER BY inhospitaldate DESC";
			pstmt = conn.prepareStatement(sql);
	        //pstmt.setInt(1, 1);
	        rs = pstmt.executeQuery();
	        while(rs.next()){
	        	Map<String,Object> para = new HashMap<>();
	        	//2宜州数据唯一标识TODO
	        	if(ValidateUtil.isNotNull(rs.getString("zy_code"))){
	        		para.put("zy_code", rs.getString("zy_code"));
	        		//3插数据TODO
	        		startGrabDataByResultSet(rs, para, 1,1,false);
	        		SInhospitalEntity s = inhospitalMapper.getInhospital(para);
	        		if(s!=null){
	        			//入院情况格式化
	        			String inhospitaldescriptionStr = rs.getString("inhospitaldescription");
	        			if(ValidateUtil.isNotNull(inhospitaldescriptionStr)){
	        				String readerStr = inhospitaldescriptionStr;
	        				SAXReader reader = new SAXReader();
	        				Document document = null;
	        				try {
	        					document = reader.read(new StringReader(readerStr), "ANSI");
	        					List<?> list = document.getRootElement().elements("text");
	        					for (Object element : list) {
	        						Element e = (Element)element;
	        						readerStr = e.getText();
	        						break;
	        					}
	        					if(ValidateUtil.isNotNull(readerStr)){
	        						s.setInhospitaldescription(readerStr);
	        						inhospitalMapper.updateInhospital(s);
	        					}
	        				} catch (Exception e) {
	        					e.printStackTrace();
	        				}
	        			}
	        		}
	        	}
	        }
	        
	        //院后数据
	        //设置抓取日期范围TODO
	        for(int i=0;i<=7;i++){
	        	Calendar outStartCal = Calendar.getInstance();
				outStartCal.add(Calendar.DAY_OF_MONTH, -i);
				outStartCal.set(Calendar.HOUR_OF_DAY, 0);
				outStartCal.set(Calendar.SECOND, 0);
				outStartCal.set(Calendar.MINUTE, 0);
				Date outSstartTime = outStartCal.getTime();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		        String date = df.format(outSstartTime);
		        //1对接视图TODO
		        //sql = "SELECT * FROM REPORT.dbo.JKGL_CZYJL WHERE inhospitalstatus = 2 AND outhospitaldate >= '"+date+" 00:00:00' AND outhospitaldate <= '"+date+" 23:59:59' ORDER BY outhospitaldate DESC";
		        sql="SELECT inhospitalstatus1, inhospitalstatus, departmentid, outhospitaldepartment, outhospitaldepartmentid, inhospitaldepartment, inhospitaldepartmentid, inhospitaldays, inhospitaldate, outhospitaldate, outhospitaldateclose, totalcost, maindoctorname, maindoctorid, doordoctorname, doordoctorid, costtype, inhospitalid, visitnum, inhospitalcount, outhospitaldiagnose, outhospitaldiagnoseicd, outhospitinfo, outhospitrecordid, drugallergy, allergydrug, rh, outhospitaltype, Pathologydiagnosename, pathologydiagnosecode, inhospitalway, inhospitaldiagnosis, inhospitaldiagnosiscode, outhospitalchinadoctordiagnosediseasname, outhospitalchinadoctordiagnosediseascode, outhospitalchinadoctordiagnosecardname, outhospitalchinadoctordiagnosecardcode, mainoperationname, mainoperationcode, otheroperationnameone, otheroperationcodeone, otheroperationnametwo, otheroperationcodetwo, otheroperationnamethree, otheroperationcodethree, otheroperationnamefour, otheroperationcodefour, outhospitalotherdiagnosenameone, outhospitalotherdiagnosecodeone, outhospitalotherdiagnosenametwo, outhospitalotherdiagnosecodetwo, outhospitalotherdiagnosenamethree, outhospitalotherdiagnosecodethree, outhospitalotherdiagnosenamefour, outhospitalotherdiagnosecodefour, outhospitalotherdiagnosenamefive, outhospitalotherdiagnosecodefive, bloodtype, owncost, healthinsurancecost, ageunit, age, nation, name, phone, phonetwo, companyphone, relativephone, birthday, sex, marry, profession, currentaddress, teladdress, company, relativename, relation, education, cardnum, inpatientarea, inpatientward, hospitalbed, outstatus, nursename, responsibleNurseId, outhospitaladvise, zy_code FROM REPORT.dbo.JKGL_CZYJL WHERE inhospitalstatus1 = 1 AND outhospitaldate >= '"+date+" 00:00:00' AND outhospitaldate <= '"+date+" 23:59:59' ORDER BY outhospitaldate DESC";
		        pstmt = conn.prepareStatement(sql);
		        rs = pstmt.executeQuery();
		        while(rs.next()){
		        	Map<String,Object> para = new HashMap<>();
		        	//2宜州数据唯一标识TODO
		        	if(ValidateUtil.isNotNull(rs.getString("zy_code"))){
		        		para.put("zy_code", rs.getString("zy_code"));
		        		//3插数据TODO
        	    		startGrabDataByResultSet(rs, para, 1,2,false);
//        	    		SInhospitalEntity s = inhospitalMapper.getInhospital(para);
//        	    		if(s!=null&&ValidateUtil.isNotNull(s.getOuthospitrecordid())){
//        	    			//电子病历(出院记录)格式化
//        	    			para = new HashMap<>();
//        	    			para.put("outhospitrecordid",s.getOuthospitrecordid());
//        	    			SelctronicMedicalRecordEntity emr = elctronicMedicalRecordMapper.getEMR(para);
//        	    			String dzblSQL = "SELECT * FROM REPORT.dbo.JKGL_DZBL where outhospitrecordid = ?";
//        	    			PreparedStatement dzbl = conn.prepareStatement(dzblSQL);
//        	    			dzbl.setString(1, rs.getString("outhospitrecordid"));
//        	    			ResultSet dzblrs=dzbl.executeQuery();
//        	    			if(dzblrs.next()){
//        	    				String content = dzblrs.getString("leavehospitalcontent");
//        	    				if(ValidateUtil.isNotNull(content)){
//        	    					SAXReader reader = new SAXReader();
//        	    					Document document = null;
//        	    					try {
//        	    						document = reader.read(new StringReader(content), "ANSI");
//        	    						List<?> list = document.getRootElement().elements("text");
//        	    						for (Object element : list) {
//        	    							Element e = (Element)element;
//        	    							content = e.getText();
//        	    							break;
//        	    						}
//        	    						if(emr==null){
//        	    							emr = new SelctronicMedicalRecordEntity();
//        	    							emr.setOuthospitrecordid(dzblrs.getString("outhospitrecordid"));
//        	    							emr.setContent(content);
//        	    							elctronicMedicalRecordMapper.setEMR(emr);
//        	    						}else{
//        	    							emr.setContent(content);
//        	    							elctronicMedicalRecordMapper.updateEMR(emr);
//        	    						}
//        	    					} catch (Exception e) {
//        	    						e.printStackTrace();
//        	    					}
//        	    				}
//        	    			}
//        	    		}
		        	}
		        }
	        }
	        
	        //转科室处理
	        List<Map<String, String>> list = inhospitalMapper.getInhospitalAndDepartmentId();
	        for(Map<String, String> s:list){
	        	//1对接视图TODO
		        String zksSql = "SELECT * FROM REPORT.dbo.JKGL_CZYJL WHERE inhospitalstatus = 1 AND zy_code = ?";
		        pstmt = conn.prepareStatement(zksSql);
		        pstmt.setString(1, s.get("zy_code"));
		        rs = pstmt.executeQuery();
		        if(rs.next()){
		        	//2宜州数据唯一标识TODO
		        	//判断入院科室是否一致
		        	if(!rs.getString("INHOSPITALDEPARTMENTID").equals(s.get("THIRDPARTYHISID"))){
		        		Map<String,Object> para = new HashMap<>();
		        		para.put("zy_code", rs.getString("zy_code"));
		        		//3插数据TODO
		        		startGrabDataByResultSet(rs, para, 1,1,true);
		        	}
		        }
	        }
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally{
            try {
                if(rs !=null)rs.close();
            } catch (Exception e) {
               e.printStackTrace();
            }

            try {
                if(pstmt !=null)pstmt.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }

            try {
                if(conn !=null)conn.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }
        } 
	}
	
	/**
	 * 创建默认院中疾病与健康管理中心管理员
	 * @throws SQLException 
	 */
	private void setdefaultInJbglAdmin() throws SQLException {
		// 院中疾病管理师
		// k:工号 v:姓名空格科室第三方id
		Map<String, String> map = new HashMap<String, String>();
		map.put("080674","廖慧芳,2");
		map.put("080531","黄  艳,853");
		map.put("080515","韦满香,61");
		map.put("080136","覃孝影,570");
		map.put("080129","黄英,690");
		map.put("080111","韦柳芹,861");
		map.put("080504","黄桂芳,62");
		map.put("080629","李孟香,551");
		map.put("080510","蓝艳,7");
		map.put("080609","谭桂芬,44");
		map.put("080618","黄春燕,261");
		map.put("080625","侯深玲,57");
		map.put("080418","覃  谊,13");
		for (String key : map.keySet()) {
			String[] data = map.get(key).split(",");
			String name = data[0];
			String ksId = data[1];
			Map<String, Object> userPara = new HashMap<>();
			userPara.put("jobnum", key.toString());
			userPara.put("thirdpartyhisid", key.toString());
			SUserEntity user = userMapper.getUser(userPara);
			if (user == null) {
				user = new SUserEntity();
				user.setName(name);
				user.setJobnum(key);
				user.setPassword("123456");
				user.setThirdpartyhisid(key);
				userMapper.setUser(user);
				// 用户角色
				Map<String, Object> para = new HashMap<>();
				para.put("userid", user.getId());
				para.put("roleid", 8);// 院中疾病与健康管理中心管理员
				SUserroleEntity userRole = userRoleMapper.getUserRole(para);
				if (userRole == null) {
					// 插入用户角色管理表
					para = new HashMap<>();
					para.put("orgId", 1);
					para.put("typeId", 8);// 院中疾病与健康管理中心管理员
					SRoleEntity nurseRole = roleMapper.getRole(para);
					userRole = new SUserroleEntity();
					userRole.setsUserEntity(user);
					userRole.setsRoleEntity(nurseRole);
					userRoleMapper.setUserRole(userRole);
					// 插入用户机构科室关联表
					Map<String,Object> params = new HashMap<String, Object>();
					params.put("thirdpartyhisid", ksId);
					params.put("orgId", org.getId());
					SDepartmentEntity inhospitalDepartment = departmentMapper.getDepartment(params);
					para = new HashMap<>();
					para.put("userid", user.getId());
					para.put("departmentid", inhospitalDepartment.getId());
					para.put("orgid", 1);
					para.put("typeId", 1);
					SUserorgEntity userOrg = userOrgMapper.getUserOrg(para);
					if (userOrg == null) {
						userOrg = new SUserorgEntity();
						userOrg.setDepartmentid(inhospitalDepartment.getId());
						userOrg.setOrgid(1);
						userOrg.setUserid(user.getId());
						userOrg.setTypeId(1);
						userOrgMapper.setUserOrg(userOrg);
					}
				}
			}
		}
	}
	
	/**
	 * 创建默认院后疾病管理师
	 * @throws SQLException 
	 */
	private void setdefaultOutJbgl() throws SQLException {
		// 院后疾病管理师
		Map<String, String> map = new HashMap<String, String>();
		map.put("080501","韦吉英");
		map.put("080908","兰爱和");
		map.put("080909","姚雪玉");
		for (String key : map.keySet()) {
			Map<String, Object> userPara = new HashMap<>();
			userPara.put("jobnum", key.toString());
			userPara.put("thirdpartyhisid", key.toString());
			SUserEntity user = userMapper.getUser(userPara);
			if (user == null) {
				user = new SUserEntity();
				user.setName(map.get(key));
				user.setJobnum(key);
				user.setPassword("123456");
				user.setThirdpartyhisid(key);
				userMapper.setUser(user);
				// 用户角色
				Map<String, Object> para = new HashMap<>();
				para.put("userid", user.getId());
				para.put("roleid", 6);// 院后疾病管理师
				SUserroleEntity userRole = userRoleMapper.getUserRole(para);
				if (userRole == null) {
					// 插入用户角色管理表
					para = new HashMap<>();
					para.put("orgId", 1);
					para.put("typeId", 6);// 院后疾病管理师
					SRoleEntity nurseRole = roleMapper.getRole(para);
					userRole = new SUserroleEntity();
					userRole.setsUserEntity(user);
					userRole.setsRoleEntity(nurseRole);
					userRoleMapper.setUserRole(userRole);
					// 插入用户机构科室关联表
					para=new HashMap<>();
					para.put("orgId", 1);
					para.put("state", 1);
					List<SDepartmentEntity> departmentList = departmentMapper.getDepartmentsList(para);
					for(SDepartmentEntity department:departmentList){
						para = new HashMap<>();
						para.put("userid", user.getId());
						para.put("departmentid", department.getId());
						para.put("orgid", 1);
						para.put("typeId", 2);
						SUserorgEntity userOrg = userOrgMapper.getUserOrg(para);
						if (userOrg == null) {
							userOrg = new SUserorgEntity();
							userOrg.setDepartmentid(department.getId());
							userOrg.setOrgid(1);
							userOrg.setUserid(user.getId());
							userOrg.setTypeId(2);
							userOrgMapper.setUserOrg(userOrg);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 创建默认院后疾病与健康管理中心管理员
	 * @throws SQLException 
	 */
	private void setdefaultOutJbglAdmin() throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("080505","韦丽芳");
		for (String key : map.keySet()) {
			Map<String, Object> para = new HashMap<>();
			para.put("jobnum", key.toString());
			para.put("thirdpartyhisid", key.toString());
			SUserEntity user = userMapper.getUser(para);
			if (user == null) {
				user = new SUserEntity();
				user.setName(map.get(key));
				user.setJobnum(key);
				user.setPassword("123456");
				user.setThirdpartyhisid(key);
				userMapper.setUser(user);
				// 用户角色
				para = new HashMap<>();
				para.put("userid", user.getId());
				para.put("roleid", 9);// 院后疾病与健康管理中心管理员
				SUserroleEntity userRole = userRoleMapper.getUserRole(para);
				if (userRole == null) {
					// 插入用户角色管理表
					para = new HashMap<>();
					para.put("orgId", 1);
					para.put("typeId", 9);// 院后疾病与健康管理中心管理员
					SRoleEntity nurseRole = roleMapper.getRole(para);
					userRole = new SUserroleEntity();
					userRole.setsUserEntity(user);
					userRole.setsRoleEntity(nurseRole);
					userRoleMapper.setUserRole(userRole);
					// 插入用户机构科室关联表
					para=new HashMap<>();
					para.put("orgId", 1);
					para.put("state", 1);
					List<SDepartmentEntity> departmentList = departmentMapper.getDepartmentsList(para);
					for(SDepartmentEntity department:departmentList){
						para = new HashMap<>();
						para.put("userid", user.getId());
						para.put("departmentid", department.getId());
						para.put("orgid", 1);
						para.put("typeId", 2);
						SUserorgEntity userOrg = userOrgMapper.getUserOrg(para);
						if (userOrg == null) {
							userOrg = new SUserorgEntity();
							userOrg.setDepartmentid(department.getId());
							userOrg.setOrgid(1);
							userOrg.setUserid(user.getId());
							userOrg.setTypeId(2);
							userOrgMapper.setUserOrg(userOrg);
						}
					}
				}
			}
		}
	}

	public void setDepartments(Connection conn) {
		//默认管理科室第三方科室ID
		List<String> departmentDefaultStateList = new ArrayList<>();
		departmentDefaultStateList.add("62");
		departmentDefaultStateList.add("261");
		departmentDefaultStateList.add("44");
		departmentDefaultStateList.add("853");
		departmentDefaultStateList.add("61");
		departmentDefaultStateList.add("570");
		departmentDefaultStateList.add("690");
		departmentDefaultStateList.add("861");
		departmentDefaultStateList.add("2");
		departmentDefaultStateList.add("7");
		departmentDefaultStateList.add("551");
		departmentDefaultStateList.add("13");
		departmentDefaultStateList.add("57");
	    String sql = "SELECT DISTINCT OUTHOSPITALDEPARTMENT, OUTHOSPITALDEPARTMENTID FROM REPORT.dbo.JKGL_CZYJL WHERE outhospitaldepartment IS NOT NULL AND outhospitaldepartmentid IS NOT NULL ORDER BY OUTHOSPITALDEPARTMENT";
	    PreparedStatement pstmt=null;
	    ResultSet rs=null;
	    try {
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        while(rs.next()){
	        	//宜州默认管理所有科室
	        	//departmentDefaultStateList.add(rs.getString("OUTHOSPITALDEPARTMENTID"));
	        	setDepartment(rs.getString("OUTHOSPITALDEPARTMENTID"), rs.getString("OUTHOSPITALDEPARTMENT"), 1,departmentDefaultStateList);
	        }
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally{
            try {
                if(rs !=null)rs.close();
            } catch (Exception e) {
               e.printStackTrace();
            }

            try {
                if(pstmt !=null)pstmt.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }
        } 
	}
	
	/**
	 * 创建默认医院管理员账号
	 * @throws SQLException 
	 */
	private void setdefaultAdmin() throws SQLException{
		//创建默认医院管理员
		Map<String,Object> userPara = new HashMap<>();
		userPara.put("jobnum", "2");
		SUserEntity user = userMapper.getUser(userPara);
		if(user==null){
			user = new SUserEntity();
			user.setJobnum("2");
			user.setName("医院管理员");
			user.setPassword("123456");
			user.setThirdpartyhisid("2");
			userMapper.setUser(user);
			Map<String,Object> departmentPara = new HashMap<>();
			departmentPara.put("orgId", 1);
			List<SDepartmentEntity> departments = departmentMapper.getDepartmentsList(departmentPara);
			
			for(SDepartmentEntity department:departments){
				SRoleEntity adminRole = getUserRole(org, user, 2);
				//插入医生角色关联表
				setUserRole(user, adminRole);
				//插入医生用户机构科室关联表
				setUserOrg(user, org, department,1);
			}
		}
		
		
		//新增自用管理员
		userPara = new HashMap<>();
		userPara.put("jobnum", "9801");
		userPara.put("thirdpartyhisid", "9801");
		SUserEntity luo = userMapper.getUser(userPara);
		if(luo==null){
			luo = new SUserEntity();
			luo.setJobnum("9801");
			luo.setName("罗建文");
			luo.setPassword("123456");
			luo.setThirdpartyhisid("9801");
			userMapper.setUser(luo);
			Map<String,Object> departmentPara = new HashMap<>();
			departmentPara.put("orgId", 1);
			List<SDepartmentEntity> departments = departmentMapper.getDepartmentsList(departmentPara);
			
			for(SDepartmentEntity department:departments){
				SRoleEntity adminRole = getUserRole(org, luo, 2);
				//插入医生角色关联表
				setUserRole(luo, adminRole);
				//插入医生用户机构科室关联表
				setUserOrg(luo, org, department,1);
			}
		}	
		
		//新增专用管理员
		userPara = new HashMap<>();
		userPara.put("jobnum", "3");
		userPara.put("thirdpartyhisid", "3");
		SUserEntity zy = userMapper.getUser(userPara);
		if(zy==null){
			zy = new SUserEntity();
			zy.setJobnum("3");
			zy.setName("专用管理员");
			zy.setPassword("123456");
			zy.setThirdpartyhisid("3");
			userMapper.setUser(zy);
			Map<String,Object> departmentPara = new HashMap<>();
			departmentPara.put("orgId", 1);
			List<SDepartmentEntity> departments = departmentMapper.getDepartmentsList(departmentPara);
			for(SDepartmentEntity department:departments){
				SRoleEntity adminRole = getUserRole(org, zy, 2);
				//插入医生角色关联表
				setUserRole(zy, adminRole);
				//插入医生用户机构科室关联表
				setUserOrg(zy, org, department,1);
			}
		}	
	}
	
	private static Connection getConn() {
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		//String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
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
}