package cn.sqwsy.health365interface.service;

import java.io.StringReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.sqwsy.health365interface.dao.entity.SDepartmentEntity;
import cn.sqwsy.health365interface.dao.entity.SOrgEntity;
import cn.sqwsy.health365interface.dao.entity.SRoleEntity;
import cn.sqwsy.health365interface.dao.entity.SUserEntity;
import cn.sqwsy.health365interface.dao.entity.SUserorgEntity;
import cn.sqwsy.health365interface.dao.entity.SUserroleEntity;
import cn.sqwsy.health365interface.service.utils.ValidateUtil;

@Component
public class SanYaHisHandler extends HisDateService {

	@Scheduled(fixedDelay = 3000)
	public void fixedRateJob() {
		for(int i=1;i<=30;i++){
			setKs();
			setJbgls();
			Map<String, Object> departmentPara = new HashMap<>();
			departmentPara.put("orgId", 1);
			departmentPara.put("state", 1);
			List<SDepartmentEntity> departments = departmentMapper.getDepartmentsList(departmentPara);
			
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
			
			try {
				setdefaultAdmin();
				for (SDepartmentEntity department : departments) {
					StringBuffer sb = new StringBuffer();
					sb.append("^^1^").append(department.getThirdpartyhisid());
					callinginterface(sb, 1);
					sb.setLength(0);
					sb.append(startTime + "^" + endTime + "^2^").append(department.getThirdpartyhisid());
					callinginterface(sb, 2);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void callinginterface(StringBuffer sb, Integer status) {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://172.16.200.140/csp/i-empi/DHC.HealthManager.BS.HealthMangerInfoService.CLS?WSDL=1");
		Object[] objects = new Object[0];
		try {
			objects = client.invoke("GetMessageInfo", "GetIPOutInfo", sb.toString());
			toXml(objects[0], status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void toXml(Object object, Integer status) throws DocumentException, SQLException {
		String data = object.toString().substring(1);
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new StringReader(data.toString()), "ANSI");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		if(root.element("ResultCode").getText().equals("0")){
			Iterator<Element> it = root.elementIterator("IpOutInfos");
			while (it.hasNext()) {
				Element element = it.next();
				Map<String, Object> para = new HashMap<>();
				// 章丘数据唯一标识
				if (ValidateUtil.isNotNull(element.element("visitnum").getText())) {
					para.put("visitnum", element.element("visitnum").getText());
					startGrabDataByElement(element, para, 1, status);
				}
			}
		}else{
			System.out.println("没有数据");
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
			// 插入医生角色关联表
			setUserRole(user, adminRole);
			// 插入医生用户机构科室关联表
			setUserOrg(user, org, department);
		}
	}

	public void setKs() {
		Map<Object, String> map = new HashMap<>();
		map.put(5, "病理科");
		map.put(16, "儿科");
		map.put(19, "放疗科");
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
		map.put(77, "老年医学中医科");
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
		map.put(338, "急诊重症监护室");
		for (Object key : map.keySet()) {
			try {
				setDepartment(key.toString(), map.get(key), 1);
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
					SDepartmentEntity inhospitalDepartment = departmentMapper.getDepartmentByHisId(ksId);
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
						userOrgMapper.setUserOrg(userOrg);
					}
				}
			}
		}
	}
}
