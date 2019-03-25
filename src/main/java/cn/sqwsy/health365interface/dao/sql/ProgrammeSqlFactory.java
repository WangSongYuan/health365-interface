package cn.sqwsy.health365interface.dao.sql;

import org.apache.ibatis.jdbc.SQL;

import cn.sqwsy.health365interface.dao.entity.SProgrammeEntity;

public class ProgrammeSqlFactory {
	public String setProgramme(SProgrammeEntity programme){
        SQL sql = new SQL();
        sql.INSERT_INTO("s_programme");
        sql.VALUES("inhospitalid", "#{inhospitalid}");
        sql.VALUES("plateid", "#{plateid}");
        if(programme.getCreatetime()!=null){
        	 sql.VALUES("createtime", "now()");
        }
        if(programme.getState()!=null){
       	 sql.VALUES("state", "#{state}");
       }
        return sql.toString();
	}
}
