package cn.sqwsy.health365interface.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
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
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.sqwsy.health365interface.dao.entity.SDepartmentEntity;
import cn.sqwsy.health365interface.dao.entity.SOrgEntity;
import cn.sqwsy.health365interface.dao.entity.SRoleEntity;
import cn.sqwsy.health365interface.dao.entity.SUserEntity;
import cn.sqwsy.health365interface.dao.entity.SUserorgEntity;
import cn.sqwsy.health365interface.dao.entity.SUserroleEntity;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

@Component
@SuppressWarnings("unchecked")
public class SanYaHisHandler extends HisDateService {
	

	//@Scheduled(fixedDelay = 1800000)
	public void fixedRateJob() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startHisTime = sdf.format(new Date());
			System.out.println("开始"+startHisTime);
			//setKs();
			//setJbgls();
			//setdefaultAdmin();
			Map<String, Object> departmentPara = new HashMap<>();
			departmentPara.put("orgId", 1);
			departmentPara.put("state", 1);
			List<SDepartmentEntity> departments = departmentMapper.getDepartmentsList(departmentPara);
			
			//住院记录
			for(SDepartmentEntity department:departments){
				StringBuffer sb = new StringBuffer();
				sb.append("^^1^").append(department.getThirdpartyhisid()).append("^");
				toXml(callinginterface(sb),1);
			}
			
			//出院记录
			for(int i=0;i<3;i++){
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
					StringBuffer sb = new StringBuffer();
					sb.append(startTime + "^" + endTime + "^2^").append(department.getThirdpartyhisid()+"^");
					toXml(callinginterface(sb),2);
				}
			}
			
			//转科室处理
//			List<Map<String, String>> list = inhospitalMapper.getInhospitalAndDepartmentId();
//			Object[] objects = new Object[0];
//	        for(Map<String, String> s:list){
//	        	String sb = "^^1^^"+s.get("visitnum");
//    			objects = client.invoke("SendMessageInfo","MES0014",sb);
//    			SAXReader reader = new SAXReader();
//    			Document document = null;
//				document = reader.read(new StringReader(objects[0].toString()), "ANSI");
//    			Element root = document.getRootElement();
//    			if(root.element("ResultCode").getText().equals("0")){
//    				Iterator<Element> it = root.elementIterator("IpOutInfos");
//    				if(it.hasNext()) {
//    					Element element = it.next();
//    					//判断入院科室是否一致
//    					if(!element.element("inhospitaldepartmentid").getText().equals(s.get("THIRDPARTYHISID"))){
//    						Map<String,Object> para = new HashMap<>();
//    						//2三亚数据唯一标识TODO
//    						para.put("visitnum", element.element("visitnum").getText());
//    						//3插数据TODO
//    						startGrabDataByElement(element, para, 1,1,true);
//    					}
//    				}
//    			}
//	        }
	        String endHisTime = sdf.format(new Date());
			System.out.println("结束"+endHisTime);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private Object callinginterface(StringBuffer sb) {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://172.16.200.48/csp/hsb/DHC.Published.PUB0003.BS.PUB0003.CLS?WSDL=1");
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		HTTPClientPolicy policy = new HTTPClientPolicy();
		long timeout = 10 * 60 * 1000;
		policy.setConnectionTimeout(timeout);
		policy.setReceiveTimeout(timeout);
		conduit.setClient(policy);
		Object[] objects = new Object[0];;
		try {
			objects = client.invoke("SendMessageInfo", "MES0014", sb.toString());
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try{
			//接口日志start
			OutputStreamWriter   pw = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//设置日期格式
			String date = df.format(new Date());// new Date()为获取当前系统时间
			String time = df2.format(new Date());
			String fileName = "D:/Dao/logs/"+date +"/"+time+"_"+sb.toString()+".xml";
			File file = new File(fileName);  
			File fileParent = file.getParentFile();  
			if(!fileParent.exists()){  
			    fileParent.mkdirs();  
			}  
			pw = new OutputStreamWriter(
					new FileOutputStream(fileName), "UTF-8");
			pw.write(objects[0].toString());
			pw.close();//关闭流
			//接口日志end
		}catch(Exception e){
			e.printStackTrace();
		}
		return objects[0];
	}

	private void toXml(Object object, Integer status) throws DocumentException, SQLException {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new StringReader(object.toString()), "ANSI");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		if(root.element("ResultCode").getText().equals("0")){
			Iterator<Element> it = root.elementIterator("IpOutInfos");
			while (it.hasNext()) {
				Element element = it.next();
				Map<String, Object> para = new HashMap<>();
				// 三亚数据唯一标识
				if (ValidateUtil.isNotNull(element.element("visitnum").getText())) {
					para.put("visitnum", element.element("visitnum").getText());
					startGrabDataByElement(element, para, 1, status,false);
				}
			}
		}else{
			//System.out.println("没有数据");
		}
	}

	/**
	 * 创建默认医院管理员账号
	 * 
	 * @throws SQLException
	 */
	private void setdefaultAdmin() throws SQLException {
		//
		Map<String, Object> userPara = new HashMap<>();
		userPara.put("jobnum", "2");
		SUserEntity user = userMapper.getUser(userPara);
		if (user == null) {
			user = new SUserEntity();
			user.setJobnum("2");
			user.setName("医院管理员");
			user.setPassword("123456");
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
	
	/*private void setKsByExcel() {
		//默认管理科室第三方科室ID
		List<String> departmentDefaultStateList = new ArrayList<>();
		departmentDefaultStateList.add("94");
		departmentDefaultStateList.add("114");
		departmentDefaultStateList.add("49");
		departmentDefaultStateList.add("185");
		departmentDefaultStateList.add("139");
		departmentDefaultStateList.add("147");
		departmentDefaultStateList.add("363");
		departmentDefaultStateList.add("364");
		departmentDefaultStateList.add("365");
		String fileToBeRead = "D:\\sy.xls";
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
	}*/

	public void setKs() {
		Map<Object, String> map = new HashMap<>();
		map.put(5, "病理科");
		map.put(16, "儿科");
		map.put(19, "耳鼻喉科");
		map.put(26, "放射科");
		map.put(27, "妇产科");
		map.put(32, "感染科");
		map.put(37, "功能检查科");
		map.put(40, "骨关节外科");
		map.put(42, "骨科");
		map.put(49, "呼吸内科");
		map.put(57, "急诊病房");
		map.put(65, "检验科");
		map.put(67, "介入科");
		map.put(69, "康复医学科");
		map.put(73, "口腔颌面外科");
		map.put(77, "老年医学科");
		map.put(84, "麻醉手术科");
		map.put(89, "泌尿外科");
		map.put(94, "内分泌科");
		map.put(101, "普通外科二区");
		map.put(103, "普通外科一区");
		map.put(108, "烧伤整形科皮肤科");
		map.put(114, "神经内科");
		map.put(117, "神经外科");
		map.put(120, "肾内科");
		map.put(126, "输血科");
		map.put(129, "疼痛科");
		map.put(131, "体检中心");
		map.put(139, "消化内科");
		map.put(144, "心胸外科");
		map.put(147, "心血管内科");
		map.put(150, "新生儿科");
		map.put(160, "血液内科");
		map.put(162, "眼科");
		map.put(185, "肿瘤内科");
		map.put(188, "重症医学科(ICU)");
		map.put(174, "营养科");
		map.put(306, "疼痛脊柱微创中心");
		map.put(307, "医疗保健科");
		map.put(331, "全科医学科");
		map.put(338, "急诊重症监护室");
		map.put(363, "肿瘤治疗中心");
		map.put(364, "肿瘤治疗中心一");
		map.put(365, "肿瘤治疗中心二");
		
		//默认管理科室第三方科室ID
		List<String> departmentDefaultStateList = new ArrayList<>();
		departmentDefaultStateList.add("94");
		departmentDefaultStateList.add("114");
		departmentDefaultStateList.add("49");
		departmentDefaultStateList.add("185");
		departmentDefaultStateList.add("139");
		departmentDefaultStateList.add("147");
		departmentDefaultStateList.add("363");
		departmentDefaultStateList.add("364");
		departmentDefaultStateList.add("365");
		
		for (Object key : map.keySet()) {
			try {
				setDepartment(key.toString(), map.get(key), 1,departmentDefaultStateList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void setJbgls() {
		// 院中疾病管理师
		Map<String, String> map = new HashMap<String, String>();
		map.put("wxl", "王晓莉 ,94");// k:工号 v:姓名空格科室第三方id
		map.put("fby", "方宝莹 ,94");
		map.put("fxy", "傅小燕 ,114");
		map.put("wsp", "吴少平 ,114");
		map.put("wxm", "王晓敏 ,49");
		map.put("lt", "林婷 ,49");
		map.put("cyw", "陈月莞,185");
		map.put("fyn", "符宇宁,139");
		map.put("zc", "赵嫦 ,147");
		map.put("fyp", "符燕萍,147");
		for (String key : map.keySet()) {
			String[] data = map.get(key).split(",");
			String name = data[0];
			String ksId = data[1];
			Map<String, Object> userPara = new HashMap<>();
			userPara.put("jobnum", key.toString());
			SUserEntity user = userMapper.getUser(userPara);
			if (user == null) {
				user = new SUserEntity();
				user.setName(name);
				user.setJobnum(key);
				user.setPassword("123456");
				userMapper.setUser(user);
				// 用户角色
				Map<String, Object> userRolePara = new HashMap<>();
				userRolePara.put("userid", user.getId());
				userRolePara.put("roleid", 3);// 院中疾病管理师
				SUserroleEntity userRole = userRoleMapper.getUserRole(userRolePara);
				if (userRole == null) {
					// 插入用户角色管理表
					Map<String, Object> nurseRolePara = new HashMap<>();
					nurseRolePara.put("orgId", 1);
					nurseRolePara.put("typeId", 3);
					SRoleEntity nurseRole = roleMapper.getRole(nurseRolePara);
					userRole = new SUserroleEntity();
					userRole.setsUserEntity(user);
					userRole.setsRoleEntity(nurseRole);
					userRoleMapper.setUserRole(userRole);
					// 插入护士用户机构科室关联表
					Map<String,Object> params = new HashMap<String, Object>();
					params.put("thirdpartyhisid", ksId);
					params.put("orgId", 1);
					SDepartmentEntity inhospitalDepartment = departmentMapper.getDepartment(params);
					Map<String, Object> userOrgPara = new HashMap<>();
					userOrgPara.put("userid", user.getId());
					userOrgPara.put("departmentid", ksId);
					userOrgPara.put("orgid", 1);
					SUserorgEntity userOrg = userOrgMapper.getUserOrg(userOrgPara);
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
}
