package cn.sqwsy.health365interface.service;

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

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.sqwsy.health365interface.dao.entity.Nurse;
import cn.sqwsy.health365interface.dao.entity.SDepartmentEntity;
import cn.sqwsy.health365interface.dao.entity.SInhospitalEntity;
import cn.sqwsy.health365interface.dao.entity.SOrgEntity;
import cn.sqwsy.health365interface.dao.entity.SRoleEntity;
import cn.sqwsy.health365interface.dao.entity.SUserEntity;
import cn.sqwsy.health365interface.dao.entity.SUserorgEntity;
import cn.sqwsy.health365interface.dao.entity.SUserroleEntity;
import cn.sqwsy.health365interface.dao.mapper.NurseMapper;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

@Component
public class ZhangQiuHisHandler extends HisDateService{
		
		@Autowired
		private NurseMapper nurseMapper;
	
		//@Scheduled(fixedDelay = 600000)
		public void fixedRateJob() {
			Connection conn = getConn();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try {
				//插入科室
				setDepartments(conn);
				setdefaultAdmin();
				setdefaultJbAdmin();
				//插入护士
				setNurse();
			    //String sql = "select * from V_GETOUTHOSPITALLIST where INHOSPITALSTATUS =1";
				String sql = "SELECT * FROM V_GETOUTHOSPITALLIST WHERE MAINDOCTORID IS NOT NULL AND INHOSPITALSTATUS = ?";
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setInt(1, 1);
		        rs = pstmt.executeQuery();
		        while(rs.next()){
		        	Map<String,Object> para = new HashMap<>();
		        	//章丘数据唯一标识
		        	if(ValidateUtil.isNotNull(rs.getString("PATIENTID_HIS"))&&ValidateUtil.isNotNull(rs.getString("inhospitalcount"))){
		        		para.put("patientHisId", rs.getString("PATIENTID_HIS"));
		        		para.put("inhospitalcount", rs.getString("inhospitalcount"));
		        		startGrabDataByResultSet(rs, para, 1,1,false);
		        	}
		        }
		        for(int i=0;i<=7;i++){
		        	Calendar outStartCal = Calendar.getInstance();
					outStartCal.add(Calendar.DAY_OF_MONTH, -i);
					outStartCal.set(Calendar.HOUR_OF_DAY, 0);
					outStartCal.set(Calendar.SECOND, 0);
					outStartCal.set(Calendar.MINUTE, 0);
					Date outSstartTime = outStartCal.getTime();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			        String date = df.format(outSstartTime);
			        sql = "SELECT * FROM V_GETOUTHOSPITALLIST WHERE MAINDOCTORID IS NOT NULL AND INHOSPITALSTATUS = 2 AND OUTHOSPITALDATEHOME >= '"+date+" 00:00:00' AND OUTHOSPITALDATEHOME <= '"+date+" 23:59:59'";
			        pstmt.close();
			        pstmt = conn.prepareStatement(sql);
			        rs = pstmt.executeQuery();
			        while(rs.next()){
			        	Map<String,Object> para = new HashMap<>();
			        	//章丘数据唯一标识
			        	if(ValidateUtil.isNotNull(rs.getString("PATIENTID_HIS"))&&ValidateUtil.isNotNull(rs.getString("inhospitalcount"))){
			        		para.put("patientHisId", rs.getString("PATIENTID_HIS"));
			        		para.put("inhospitalcount", rs.getString("inhospitalcount"));
		        			SInhospitalEntity s = inhospitalMapper.getInhospital(para);
		        	    	if(s!=null){
		        	    		s.setInhospitalstatus(2);
		        				s.setManagestate(2);
		        	    		inhospitalMapper.updateInhospital(s);
		        	    	}
			        	}
			        }
		        }
		        //转科室处理
		        List<Map<String, String>> list = inhospitalMapper.getInhospitalAndDepartmentId();
		        for(Map<String, String> s:list){
			        String zksSql = "SELECT * FROM V_GETOUTHOSPITALLIST WHERE PATIENTID_HIS = ? and INHOSPITALCOUNT=?";
			        pstmt = conn.prepareStatement(zksSql);
			        pstmt.setString(1, s.get("patientHisId"));
			        pstmt.setString(2, s.get("inhospitalcount"));
			        rs = pstmt.executeQuery();
			        if(rs.next()){
			        	if(!rs.getString("INHOSPITALDEPARTMENTID").equals(s.get("THIRDPARTYHISID"))){
			        		Map<String,Object> para = new HashMap<>();
			        		para.put("patientHisId", rs.getString("PATIENTID_HIS"));
			        		para.put("inhospitalcount", rs.getString("inhospitalcount"));
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
		 * 创建默认院中疾病管理师管理员
		 * @throws SQLException 
		 */
		private void setdefaultJbAdmin() throws SQLException {
			Map<String,Object> userPara = new HashMap<>();
			userPara.put("jobnum", "ylzyz");
			SUserEntity user = userMapper.getUser(userPara);
			if(user==null){
				user = new SUserEntity();
				user.setJobnum("ylzyz");
				user.setName("尹丽芝");
				user.setPassword("123456");
				userMapper.setUser(user);
				Map<String,Object> departmentPara = new HashMap<>();
				departmentPara.put("orgId", 1);
				List<SDepartmentEntity> departments = departmentMapper.getDepartmentsList(departmentPara);
				//机构
	    		Map<String,Object> orgPara = new HashMap<>();
	    		orgPara.put("id", 1);
				SOrgEntity org = orgMapper.getOrg(orgPara);
				for(SDepartmentEntity department:departments){
					//默认管理两个科室 内分泌科 0602	神经内一科 0609
					if(department.getThirdpartyhisid().equals("0602")||department.getThirdpartyhisid().equals("0609")){
						SRoleEntity adminRole = getUserRole(org, user, 8);
						//插入医生角色关联表
						setUserRole(user, adminRole);
						//插入医生用户机构科室关联表
						setUserOrg(user, org, department,1);
					}
				}
			}
		}

		public void setDepartments(Connection conn) {
			//默认管理科室第三方科室ID
			List<String> departmentDefaultStateList = new ArrayList<>();
			departmentDefaultStateList.add("0602");
			departmentDefaultStateList.add("0609");
		    String sql = "SELECT DISTINCT INHOSPITALDEPARTMENT, INHOSPITALDEPARTMENTID FROM V_GETOUTHOSPITALLIST ORDER BY INHOSPITALDEPARTMENT";
		    PreparedStatement pstmt=null;
		    ResultSet rs=null;
		    try {
		        pstmt = conn.prepareStatement(sql);
		        rs = pstmt.executeQuery();
		        while(rs.next()){
		        	setDepartment(rs.getString("INHOSPITALDEPARTMENTID"), rs.getString("INHOSPITALDEPARTMENT"), 1,departmentDefaultStateList);
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
			//
			Map<String,Object> userPara = new HashMap<>();
			userPara.put("jobnum", "2");
			SUserEntity user = userMapper.getUser(userPara);
			if(user==null){
				user = new SUserEntity();
				user.setJobnum("2");
				user.setName("医院管理员");
				user.setPassword("123456");
				userMapper.setUser(user);
				Map<String,Object> departmentPara = new HashMap<>();
				departmentPara.put("orgId", 1);
				List<SDepartmentEntity> departments = departmentMapper.getDepartmentsList(departmentPara);
				//机构
				Map<String,Object> orgPara = new HashMap<>();
				orgPara.put("id", 1);
				SOrgEntity org = orgMapper.getOrg(orgPara);
				for(SDepartmentEntity department:departments){
					SRoleEntity adminRole = getUserRole(org, user, 2);
					//插入医生角色关联表
					setUserRole(user, adminRole);
					//插入医生用户机构科室关联表
					setUserOrg(user, org, department,1);
				}
			}
		}
		
		private void setNurse() {
			List<Nurse> list = nurseMapper.getAllNurse();
			for(Nurse nurse:list){
				Map<String,Object> userPara = new HashMap<>();
				userPara.put("thirdpartyhisid", nurse.getNurseId());
				SUserEntity user = userMapper.getUser(userPara);
				if(user==null){
					//插入护士用户
					user = new SUserEntity();
					user.setName(nurse.getNurseName());
					user.setJobnum(nurse.getNurseId());
					user.setThirdpartyhisid(nurse.getNurseId());
					user.setPassword("123456");
					userMapper.setUser(user);
					
					//用户角色
					Map<String,Object> nurseRolePara = new HashMap<>();
	        		nurseRolePara.put("orgId", 1);
					//田玉晶1020  内分泌科护士长 高桂敏 1183 神经内一科护士长 
	        		if(user.getThirdpartyhisid().equals("1020")||user.getThirdpartyhisid().equals("1183")){
	        			nurseRolePara.put("typeId", 8);//护士长存院中疾病管理师管理员
	        		}else{
	        			nurseRolePara.put("typeId", 3);//护士存院中疾病管理师
	        		}
	        		SRoleEntity nurseRole = roleMapper.getRole(nurseRolePara);
					
	        		Map<String,Object> userRolePara = new HashMap<>();
					userRolePara.put("userid", user.getId());
        			userRolePara.put("roleid", nurseRole.getId());
					SUserroleEntity userRole = userRoleMapper.getUserRole(userRolePara);
					if(userRole==null){
						userRole = new SUserroleEntity();
						userRole.setsUserEntity(user);
						userRole.setsRoleEntity(nurseRole);
						userRoleMapper.setUserRole(userRole);
						//插入护士用户机构科室关联表
						Map<String,Object> params = new HashMap<String, Object>();
						params.put("thirdpartyhisid", nurse.getDepartentId());
						params.put("orgId", 1);
						SDepartmentEntity inhospitalDepartment = departmentMapper.getDepartment(params);
						Map<String,Object> userOrgPara = new HashMap<>();
						userOrgPara.put("userid", user.getId());
						userOrgPara.put("departmentid", nurse.getDepartentId());
						userOrgPara.put("orgid", 1);
						userOrgPara.put("typeId",1);
						SUserorgEntity userOrg = userOrgMapper.getUserOrg(userOrgPara);
						if(userOrg==null){
							userOrg = new SUserorgEntity();
							userOrg.setDepartmentid(inhospitalDepartment.getId());
							userOrg.setOrgid(1);
							userOrg.setUserid(user.getId());
							userOrg.setTypeId(1);//住院
							userOrgMapper.setUserOrg(userOrg);
						}
					}
				}
			}
		}
		
		private static Connection getConn() {
			String driver = "oracle.jdbc.driver.OracleDriver";
			String ip = "192.168.1.3";
			String sid = "oracle";
			String port = "1521";
			String dbUser = "jkgl";
			String dbPassword = "jkgl";
		    String url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid;
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
