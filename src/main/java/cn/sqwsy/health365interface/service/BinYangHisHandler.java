package cn.sqwsy.health365interface.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
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
public class BinYangHisHandler extends HisDateService{
	SOrgEntity org = null;

	@Scheduled(fixedDelay = 3000)
	public void fixedRateJob() {
		try {
			//设置医院基本信息
			Map<String,Object> orgPara = new HashMap<>();
			orgPara.put("id", 1);
			org = orgMapper.getOrg(orgPara);
			if(org==null){
				org = new SOrgEntity();
				org.setName("宾阳县人民医院");
				orgMapper.setOrg(org);
			}
			setKs();
			//创建医院管理员账号
			setdefaultAdmin();
			//创建默认院中疾病管理师\管理员
			setdefaultInJbglAdmin();
			//创建院后疾病与健康管理中心管理员
			setdefaultOutJbglAdmin();
			//创建默认院后疾病管理师
			setdefaultOutJbgl();
			Map<String, Object> departmentPara = new HashMap<>();
			departmentPara.put("orgId", 1);
			departmentPara.put("state", 1);
			List<SDepartmentEntity> departments = departmentMapper.getDepartmentsList(departmentPara);
			//住院记录
			for(SDepartmentEntity department:departments){
				StringBuffer sb = new StringBuffer();
				sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request><departmentid>"+department.getThirdpartyhisid()+"</departmentid><inhospitalstatus>1</inhospitalstatus></request>");	
				toXml(callinginterface("getOuthospitalList",sb),1);
			}
			
			//出院记录
			for(int i=1;i<=2;i++){
				Calendar outStartCal = Calendar.getInstance();
				outStartCal.add(Calendar.DAY_OF_MONTH, -i);
				outStartCal.set(Calendar.HOUR_OF_DAY, 0);
				outStartCal.set(Calendar.SECOND, 0);
				outStartCal.set(Calendar.MINUTE, 0);
				Date outSstartTime = outStartCal.getTime();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String startTime = df.format(outSstartTime);
				
				Calendar outEndCal = Calendar.getInstance();
				outEndCal.add(Calendar.DAY_OF_MONTH, -i);
				outEndCal.set(Calendar.HOUR_OF_DAY, 0);
				outEndCal.set(Calendar.SECOND, 0);
				outEndCal.set(Calendar.MINUTE, 0);
				Date outEndTime = outEndCal.getTime();
				String endTime = df.format(outEndTime);
				for (SDepartmentEntity department : departments) {
					if(department.getState()==2){
						continue;
					}
					StringBuffer sb = new StringBuffer();
					sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request><starttime>"+startTime+" 00:00:00</starttime><endtime>"+endTime+" 23:59:59</endtime><departmentid>"+department.getThirdpartyhisid()+"</departmentid><inhospitalstatus>2</inhospitalstatus></request>");
					toXml(callinginterface("getOuthospitalList",sb), 2);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setKs() {
		//默认管理科室
		List<String> departmentDefaultStateList = new ArrayList<>();
		//departmentDefaultStateList.add("1521");//康复医学科
		//departmentDefaultStateList.add("1587");//妇科
		departmentDefaultStateList.add("1621");//神经内科、消化内科
		departmentDefaultStateList.add("1585");//神经外科、胸外科
		departmentDefaultStateList.add("1586");//骨科、烧伤外科
		departmentDefaultStateList.add("1501");//肿瘤科一区
		departmentDefaultStateList.add("1588");//产科
		departmentDefaultStateList.add("202");//中医科
		String fileToBeRead = "D:\\by.xls";
		try {
			InputStream inp = new FileInputStream(new File(fileToBeRead));
			HSSFWorkbook workbook = new HSSFWorkbook(inp);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int rowNumber = sheet.getPhysicalNumberOfRows();
			for(int rowIndex = 0; rowIndex < rowNumber; rowIndex++){
				HSSFRow row = sheet.getRow(rowIndex);
				HSSFCell cell = row.getCell(0);
				cell.setCellType(CellType.STRING);
				String id = cell.getStringCellValue();
				cell = row.getCell(1);
				String name = cell.getStringCellValue();
				setDepartment(id, name, 1,departmentDefaultStateList);
			}
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	private Object callinginterface(String method,StringBuffer sb) {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://172.16.11.19:8015/WebServicePHMHIS.asmx?wsdl");
		Object[] objects = new Object[0];
		try {
			//System.out.println(sb.toString());
			objects = client.invoke(method, sb.toString());
			client.close();
			//System.out.println(objects[0]);
			return objects[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private void toXml(Object object, Integer status){
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new StringReader(object.toString()), "ANSI");
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(object.toString());
			return;
		}
		Element root = document.getRootElement();
		Iterator<Element> it = root.elementIterator("item");
		while (it.hasNext()) {
			Element element = it.next();
			Map<String, Object> para = new HashMap<>(); 
			// 宾阳数据唯一标识
			if (ValidateUtil.isNotNull(element.element("zy_code").getText())) {
				para.put("zy_code", element.element("zy_code").getText());
				try {
					startGrabDataByElement(element, para, 1, status);
					SInhospitalEntity s = inhospitalMapper.getInhospital(para);
					if(s!=null&&status==1){
						//入院情况
						if(element.element("inhospitaldescription")!=null){
							try {
								s.setInhospitaldescription(element.element("inhospitaldescription").getText());
								inhospitalMapper.updateInhospital(s);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}else if(s!=null&&status==2){
						if(s!=null&&ValidateUtil.isNotNull(s.getOuthospitrecordid())&&s.getInhospitalcount()!=null){
							//电子病历
							para = new HashMap<>();
							para.put("outhospitrecordid",s.getOuthospitrecordid());
							SelctronicMedicalRecordEntity emr = elctronicMedicalRecordMapper.getEMR(para);
							StringBuffer sb = new StringBuffer();
							sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><request><inhospitalid>"+s.getOuthospitrecordid()+"</inhospitalid><inhospitalcount>"+s.getInhospitalcount()+"</inhospitalcount><outhospitrecordid></outhospitrecordid><visitnum></visitnum></request>");
							Object emrXml = callinginterface("getLeavehospitalcontent", sb);
							SAXReader emrReader = new SAXReader();
				            Document emrDocument = null;
				            try {
				            	emrDocument = emrReader.read(new StringReader(emrXml.toString()), "ANSI");
								List<?> list = emrDocument.getRootElement().elements("item");
								String content = null;
						        for (Object emrObject : list) {
									Element emrElement = (Element)emrObject;
									content = emrElement.element("leavehospitalcontent").getText();
									break;
						        }
						        if(ValidateUtil.isNotNull(content)){
						        	if(emr==null){
						        		emr = new SelctronicMedicalRecordEntity();
						        		emr.setOuthospitrecordid(s.getZy_code());
						        		emr.setContent(content);
						        		elctronicMedicalRecordMapper.setEMR(emr);
						        	}else{
						        		emr.setContent(content);
						        		elctronicMedicalRecordMapper.updateEMR(emr);
						        	}
						        }
				            } catch (Exception e) {
			                    e.printStackTrace();
				            }
						}
					}else{
						System.out.println(element.element("zy_code").getText());
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 创建医院管理员账号
	 * @throws SQLException
	 */
	private void setdefaultAdmin() throws SQLException {
		Map<String, Object> adminMap = new HashMap<>();
		adminMap.put("2", "医院管理员");
		adminMap.put("wlglzx", "网络管理中心");
		adminMap.put("U0001", "吴正球");
		adminMap.put("U0004", "杨灵慧");
		for(String key:adminMap.keySet()){
			 String name = adminMap.get(key).toString();//
			 Map<String, Object> userPara = new HashMap<>();
			 userPara.put("jobnum", key);
			 SUserEntity user = userMapper.getUser(userPara);
			 if (user == null) {
				 user = new SUserEntity();
				 user.setJobnum(key);
				 user.setPassword("123456");
				 user.setName(name);
				 user.setThirdpartyhisid(key);
				 userMapper.setUser(user);
			 }
			 Map<String, Object> departmentPara = new HashMap<>();
			 departmentPara.put("orgId", 1);
			 List<SDepartmentEntity> departments = departmentMapper.getDepartmentsList(departmentPara);
			 // 机构
			 Map<String, Object> orgPara = new HashMap<>();
			 orgPara.put("id", 1);
			 SOrgEntity org = orgMapper.getOrg(orgPara);
			 for (SDepartmentEntity department : departments) {
				 SRoleEntity adminRole = getUserRole(org, user, 2);
				 // 插入角色关联表
				 setUserRole(user, adminRole);
				 // 插入用户机构科室关联表
				 setUserOrg(user, org, department,1);
				 setUserOrg(user, org, department,2);
			 }
		}
	}	
	
	/**
	 * 创建默认院中疾病管理师\管理员
	 * @throws SQLException 
	 */
	private void setdefaultInJbglAdmin() throws SQLException {
		String fileToBeRead = "D:\\yzjbgls.xls";
		try {
			InputStream inp = new FileInputStream(new File(fileToBeRead));
			HSSFWorkbook workbook = new HSSFWorkbook(inp);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int rowNumber = sheet.getPhysicalNumberOfRows();
			for(int rowIndex = 0; rowIndex < rowNumber; rowIndex++){
				HSSFRow row = sheet.getRow(rowIndex);
				HSSFCell cell = row.getCell(0);
				cell.setCellType(CellType.STRING);
				String name = cell.getStringCellValue();
				cell = row.getCell(1);
				String jobnum = cell.getStringCellValue();
				cell = row.getCell(2);
				cell.setCellType(CellType.STRING);
				String roleType = cell.getStringCellValue();
				cell = row.getCell(3);
				cell.setCellType(CellType.STRING);
				String ksId = cell.getStringCellValue();
				Map<String, Object> userPara = new HashMap<>();
				userPara.put("jobnum", jobnum);
				userPara.put("thirdpartyhisid", jobnum);
				SUserEntity user = userMapper.getUser(userPara);
				if (user == null) {
					user = new SUserEntity();
					user.setName(name);
					user.setJobnum(jobnum);
					user.setPassword("123456");
					user.setThirdpartyhisid(jobnum);
					userMapper.setUser(user);
					// 用户角色
					Map<String, Object> para = new HashMap<>();
					para.put("userid", user.getId());
					para.put("roleid", roleType);
					SUserroleEntity userRole = userRoleMapper.getUserRole(para);
					if (userRole == null) {
						// 插入用户角色管理表
						para = new HashMap<>();
						para.put("orgId", 1);
						para.put("typeId", roleType);
						SRoleEntity nurseRole = roleMapper.getRole(para);
						userRole = new SUserroleEntity();
						userRole.setsUserEntity(user);
						userRole.setsRoleEntity(nurseRole);
						userRoleMapper.setUserRole(userRole);
						// 插入用户机构科室关联表
						SDepartmentEntity inhospitalDepartment = departmentMapper.getDepartmentByHisId(ksId);
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
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 创建默认院后疾病管理师
	 * @throws SQLException 
	 */
	private void setdefaultOutJbgl() throws SQLException {
		// 院后疾病管理师
		Map<String, String> map = new HashMap<String, String>();
		map.put("U0705","梁小凤");
		map.put("U0711","莫小美");
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
		map.put("U0531","白璐华");
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
}