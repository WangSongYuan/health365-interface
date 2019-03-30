package cn.sqwsy.health365interface.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.sqwsy.health365interface.dao.entity.Nurse;
import cn.sqwsy.health365interface.dao.entity.SDepartmentEntity;
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
	
		//@Scheduled(fixedDelay = 3000)
		public void fixedRateJob() {
			Connection conn = getConn();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try {
				//插入科室
				setDepartments(conn);
				setdefaultAdmin();
				//插入护士
				setNurse();
			    //String sql = "select * from V_GETOUTHOSPITALLIST where INHOSPITALSTATUS =1";
				String sql = "SELECT * FROM V_GETOUTHOSPITALLIST WHERE INHOSPITALSTATUS = 1 AND MAINDOCTORID IS NOT NULL";
		        pstmt = conn.prepareStatement(sql);
		        rs = pstmt.executeQuery();
		        while(rs.next()){
		        	Map<String,Object> para = new HashMap<>();
		        	//章丘数据唯一标识
		        	if(ValidateUtil.isNotNull(rs.getString("PATIENTID_HIS"))&&ValidateUtil.isNotNull(rs.getString("inhospitalcount"))){
		        		para.put("patientHisId", rs.getString("PATIENTID_HIS"));
		        		para.put("inhospitalcount", rs.getString("inhospitalcount"));
		        		startGrabDataByResultSet(rs, para, 1,1);
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
		
		public void setDepartments(Connection conn) {
		    String sql = "SELECT DISTINCT INHOSPITALDEPARTMENT, INHOSPITALDEPARTMENTID FROM V_GETOUTHOSPITALLIST ORDER BY INHOSPITALDEPARTMENT";
		    PreparedStatement pstmt=null;
		    ResultSet rs=null;
		    try {
		        pstmt = conn.prepareStatement(sql);
		        rs = pstmt.executeQuery();
		        while(rs.next()){
		        	setDepartment(rs.getString("INHOSPITALDEPARTMENTID"), rs.getString("INHOSPITALDEPARTMENT"), 1);
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
			}
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
				setUserOrg(user, org, department);
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
					Map<String,Object> userRolePara = new HashMap<>();
					userRolePara.put("userid", user.getId());
					userRolePara.put("roleid", 3);//实际是护士存疾病管理师
					SUserroleEntity userRole = userRoleMapper.getUserRole(userRolePara);
					if(userRole==null){
						//插入用户角色管理表
						Map<String,Object> nurseRolePara = new HashMap<>();
		        		nurseRolePara.put("orgId", 1);
		        		nurseRolePara.put("typeId", 3);
		        		SRoleEntity nurseRole = roleMapper.getRole(nurseRolePara);
						userRole = new SUserroleEntity();
						userRole.setsUserEntity(user);
						userRole.setsRoleEntity(nurseRole);
						userRoleMapper.setUserRole(userRole);
						//插入护士用户机构科室关联表
						SDepartmentEntity inhospitalDepartment = departmentMapper.getDepartmentByHisId(nurse.getDepartentId());
						Map<String,Object> userOrgPara = new HashMap<>();
						userOrgPara.put("userid", user.getId());
						userOrgPara.put("departmentid", nurse.getDepartentId());
						userOrgPara.put("orgid", 1);
						SUserorgEntity userOrg = userOrgMapper.getUserOrg(userOrgPara);
						if(userOrg==null){
							userOrg = new SUserorgEntity();
							userOrg.setDepartmentid(inhospitalDepartment.getId());
							userOrg.setOrgid(1);
							userOrg.setUserid(user.getId());
							userOrgMapper.setUserOrg(userOrg);
						}
					}
				}else{
					//更新用户
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
