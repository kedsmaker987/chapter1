package org.smart4j.chapter1.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串的工具类
 * @author Administrator
 *
 */
public class StringUtil {
	
	/**
	 * isEmpty
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str!=null){
			str = str.trim();
		}
		
		return StringUtils.isEmpty(str);
	}
	
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
}
