package cn.sqwsy.health365interface.dao.sql;

import org.apache.ibatis.jdbc.SQL;

public class NurseSqlFactory {
	public String getAllNurse(){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("s_nurse");
        return sql.toString();
	}
}
