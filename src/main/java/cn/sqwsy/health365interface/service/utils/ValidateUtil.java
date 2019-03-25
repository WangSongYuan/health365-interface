package cn.sqwsy.health365interface.service.utils;

public class ValidateUtil {
	/**
	 * 是否是null
	 * @param term
	 * @return
	 */
	public static boolean isNull(String term){
		return !isNotNull(term);
	}
	/**
	 * 是否是非null
	 * @param term
	 * @return
	 */
	public static boolean isNotNull(String term){
		if(term==null)return false;
		if(term.trim().length()<1)return false;
		return true;
	}
	/**
	 * 是否是数字
	 * @param term
	 * @return
	 */
	public static boolean isDigit(String term){
		if(term == null)
            return false;
        char ac[] = term.toCharArray();
        for(int i = 0; i < ac.length; i++)
            if(!Character.isDigit(ac[i]))
                return false;
        return true;
	}
	
	/**
	 * 判断两个字符串是否相等
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquals(String str1,String str2){
		if(str1 == str2)return true;
		if(str1 == null || str2 == null)return false;
		return str1.equals(str2);
	}
}

