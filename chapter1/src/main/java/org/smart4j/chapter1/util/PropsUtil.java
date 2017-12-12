package org.smart4j.chapter1.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropsUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);
	
	/**
	 * 加载属性文件
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("finally")
	public static Properties loadProps(String fileName) {
		Properties props = null;
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			if (is == null) {
				throw new FileNotFoundException(fileName + "file is not found");
			}

			props = new Properties();
			props.load(is);
		} catch (Exception e) {
			LOGGER.error("load properties file failure", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					LOGGER.error("close input stream failure ", e);
				}
			}

		}
		return props;
	}
	
	/**
	 * 获取字符类型属性(默认值为空字符串)
	 * @param props
	 * @param key
	 * @return
	 */
	public static String getString(Properties props,String key){
		return getString(props,key,"");
	}
	
	/**
	 * 获取字符串类型 没有可以指定值 defaultValue;
	 * @param props
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Properties props,String key,String defaultValue){
		String value = defaultValue;
		if(props.containsKey(key)){
			value = props.getProperty(key);
		}
		
		return value;
	}
	
	/**
	 * 获取数值型属性(默认为0)
	 * @param props
	 * @param key
	 * @return
	 */
	public static int getInt(Properties props,String key){
		return getInt(props,key,0);
	}
	
	/**
	 * 获取属性值类型()
	 * @param props
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(Properties props,String key,int defaultValue){
		int value = defaultValue;
		if(props.containsKey(key)){
			value = CastUtil.castInt(props.getProperty(key));
		}
		return value;
	}
	
	/**
	 * 各種換進666
	 * @param props
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Properties props,String key){
		return getBoolean(props,key,false);
	}
	
	
	public static boolean getBoolean(Properties props ,String key,boolean defalutValue){
		boolean value = defalutValue;
		if(props.containsKey(key)){
			value = CastUtil.castBoolean(props.getProperty(key));
		}
		return value;
	}
}
