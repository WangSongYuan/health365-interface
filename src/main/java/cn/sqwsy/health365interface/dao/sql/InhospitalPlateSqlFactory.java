package cn.sqwsy.health365interface.dao.sql;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.dao.entity.SInhospitalplateEntity;

public class InhospitalPlateSqlFactory {
	public String setInhospitalPlate(SInhospitalplateEntity inhospitalPlate){
        SQL sql = new SQL();
        sql.INSERT_INTO("s_inhosipitalplate");
        sql.VALUES("inhospitalid", "#{sInhospitalEntity.id}");
        sql.VALUES("plateid", "#{sPlateEntity.id}");
        if(inhospitalPlate.getStatus()!=null){
        	sql.VALUES("status", "#{status}");
        }
        if(inhospitalPlate.getCreatetime()==null){
        	sql.VALUES("createtime", "now()");
        }
        return sql.toString();
	}
}
