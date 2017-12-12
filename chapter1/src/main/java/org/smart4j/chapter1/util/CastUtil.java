package org.smart4j.chapter1.util;

/**
 * 数值类型转换类
 * @author Administrator
 *
 */
public class CastUtil {

	/**
	 * Object ==> String 默认为 "";
	 * 
	 * @param obj
	 * @return
	 */
	public static String castString(Object obj) {
		return CastUtil.castString(obj, "");
	}

	/**
	 * 调用 String.valueOf 的方法变成String
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static String castString(Object obj, String defaultValue) {
		return obj != null ? String.valueOf(obj) : defaultValue;
	}

	public static double castDouble(Object obj) {
		return CastUtil.castDouble(obj, 0d);
	}

	public static double castDouble(Object obj, double defaultValue) {
		double doubleValue = defaultValue;

		if (obj != null) {
			// 先将obj 转换成为 string
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					doubleValue = Double.parseDouble(strValue);
				} catch (Exception e) {
					doubleValue = defaultValue;
				}

			}
		}
		return doubleValue;
	}

	/**
	 * 转换为 long
	 * 
	 * @param obj
	 * @return
	 */
	public static long castLong(Object obj) {
		return castLong(obj, 0);
	}

	/**
	 * 转换为 long
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static long castLong(Object obj, long defaultValue) {
		long longValue = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					longValue = Long.parseLong(strValue);
				} catch (Exception e) {
					longValue = defaultValue;
				}

			}
		}

		return longValue;
	}

	/**
	 * 将obj 转换为 int
	 * 
	 * @param obj
	 * @return
	 */
	public static int castInt(Object obj) {
		return castInt(obj, 0);
	}

	public static int castInt(Object obj, int defaultValue) {
		int intValue = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			try {
				intValue = Integer.parseInt(strValue);
			} catch (Exception e) {
				intValue = defaultValue;
			}
		}

		return intValue;
	}
	
	
	public static boolean castBoolean(Object obj){
		return castBoolean(obj,false);
	}
	
	public static boolean castBoolean(Object obj,boolean defaultValue){
		boolean booleanValue = defaultValue;
		if(obj!=null){
			booleanValue = Boolean.parseBoolean(castString(obj));
		}
		
		return booleanValue;
	}
}
