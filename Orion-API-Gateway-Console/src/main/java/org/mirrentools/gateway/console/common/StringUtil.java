package org.mirrentools.gateway.console.common;

import java.sql.Date;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 字符串工具
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public class StringUtil {

	/**
	 * 检查字符串里面是否包含指定字符,包含返回true
	 * 
	 * @param regex
	 *          指定字符
	 * @param str
	 *          字符串
	 * @return
	 */
	public static boolean indexOf(String regex, String... str) {
		if (str == null) {
			return false;
		}
		for (String temp : str) {
			if (temp.indexOf(regex) == -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将字符串str中带有集合中rep[0]的字符串,代替为rep[1]的中的字符串
	 * 
	 * @param str
	 * @param rep
	 * @return
	 */
	public static String replace(String str, List<String[]> rep) {
		for (String[] item : rep) {
			if (item[1] == null) {
				item[1] = "";
			}
			str = str.replace(item[0], item[1]);
		}
		return str;
	}

	/**
	 * 创建字符串数组
	 * 
	 * @param str
	 * @return
	 */
	public static String[] asStrArray(String... str) {
		return str;
	}

	/**
	 * 判断字符串是否为null或者空,如果是返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为null或者空,如果是返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(Object str) {
		if (str == null || "".equals(str.toString().trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为null或者空,如果是返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String... str) {
		if (str == null || str.length == 0) {
			return true;
		}
		for (int i = 0; i < str.length; i++) {
			if (str[i] == null || "".equals(str[i].trim())) {
				return true;
			}
		}
		return false;
	}

	/** 字符串数组大小写字母与数字,该数组的规则为0-9,a-z,A-Z */
	public final static char[] ALPHANUMERIC = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	/**
	 * 生成指定程度随机字符串
	 * 
	 * @param len
	 *          字符串长度
	 * @return
	 */
	public static String randomString(int len) {
		StringBuilder result = new StringBuilder(len);
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			result.append(ALPHANUMERIC[random.nextInt(62)]);
		}
		return result.toString();
	}

	/**
	 * 生成指定程度随机字符串
	 * 
	 * @param len
	 *          字符串长度
	 * @param mode
	 *          生成的类型:<br>
	 *          0=0-9,<br>
	 *          1=a-Z,<br>
	 *          2=a-z,<br>
	 *          3=A-Z,<br>
	 *          其他返回0-Z
	 * @return
	 */
	public static String randomString(int len, int mode) {
		StringBuilder result = new StringBuilder(len);
		Random random = new Random();
		if (mode == 0) {
			for (int i = 0; i < len; i++) {
				result.append(ALPHANUMERIC[random.nextInt(9)]);
			}
		} else if (mode == 1) {
			for (int i = 0; i < len; i++) {
				result.append(ALPHANUMERIC[random.nextInt(52) + 10]);
			}
		} else if (mode == 2) {
			for (int i = 0; i < len; i++) {
				result.append(ALPHANUMERIC[random.nextInt(26) + 10]);
			}
		} else if (mode == 3) {
			for (int i = 0; i < len; i++) {
				result.append(ALPHANUMERIC[random.nextInt(26) + 36]);
			}
		} else {
			for (int i = 0; i < len; i++) {
				result.append(ALPHANUMERIC[random.nextInt(62)]);
			}
		}
		return result.toString();
	}

	/**
	 * 获得随机UUID,true=带下划线,false不带下划线
	 * 
	 * @param mode
	 * @return
	 */
	public static String randomUUID(boolean mode) {
		if (mode) {
			return UUID.randomUUID().toString();
		} else {
			return UUID.randomUUID().toString().replace("-", "");
		}
	}

	/**
	 * 判断一个字符串是否为指定对象
	 * 
	 * @param str
	 * @param type
	 * @return 是返回true,反则返回false
	 */
	public static boolean isType(String str, StringUtil.JavaType type) {
		try {
			if (type == StringUtil.JavaType.Boolean) {
				return str.equals("true") || str.equals("false");
			} else if (type == StringUtil.JavaType.Integer) {
				Integer.parseInt(str);
			} else if (type == StringUtil.JavaType.Long) {
				Long.parseLong(str);
			} else if (type == StringUtil.JavaType.Float) {
				Float.parseFloat(str);
			} else if (type == StringUtil.JavaType.Double) {
				Double.parseDouble(str);
			} else if (type == StringUtil.JavaType.JsonObject) {
				new JsonObject(str);
			} else if (type == StringUtil.JavaType.JsonArray) {
				new JsonArray(str);
			} else if (type == StringUtil.JavaType.Date) {
				Date.valueOf(str);
			} else if (type == StringUtil.JavaType.Instant) {
				Instant.parse(str);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * java的数据类型枚举类
	 * 
	 * @author <a href="http://szmirren.com">Mirren</a>
	 *
	 */
	public static enum JavaType {
		String, Integer, Date, Instant, Long, Float, Double, Boolean, JsonObject, JsonArray;
	}

	/**
	 * 将一个字符串转换为int,如果字符串为null或者""返回0
	 * 
	 * @param str
	 * @return
	 */
	public static int getint(String str) {
		if (isNullOrEmpty(str)) {
			return 0;
		}
		try {
			return new Integer(str.trim());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 将一个字符串转换为Integer,如果字符串为null或者""返回null
	 * 
	 * @param str
	 * @return
	 */
	public static Integer getInteger(String str) {
		if (isNullOrEmpty(str)) {
			return null;
		}
		try {
			return new Integer(str.trim());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将一个字符串转换为Integer,如果字符串为null或者""返回null
	 * 
	 * @param str
	 *          字符串
	 * @param def
	 *          默认值
	 * @return
	 */
	public static Integer getInteger(String str, Integer def) {
		if (isNullOrEmpty(str)) {
			return def;
		}
		try {
			return new Integer(str.trim());
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 将一个字符串转换为long,如果字符串为null或者""返回0
	 * 
	 * @param str
	 * @return
	 */
	public static long getlong(String str) {
		if (isNullOrEmpty(str)) {
			return 0l;
		}
		try {
			return new Long(str.trim());
		} catch (Exception e) {
			return 0L;
		}
	}

	/**
	 * 将一个字符串转换为Long,如果字符串为null或者""返回null
	 * 
	 * @param str
	 * @return
	 */
	public static Long getLong(String str) {
		if (isNullOrEmpty(str)) {
			return null;
		}
		try {
			return new Long(str.trim());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将一个字符串转换为Long
	 * 
	 * @param str
	 * @param def
	 *          默认值
	 * @return
	 */
	public static Long getLong(String str, Long def) {
		if (isNullOrEmpty(str)) {
			return def;
		}
		try {
			return new Long(str.trim());
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 将一个字符串转换为float,如果字符串为null或者""返回0.0f
	 * 
	 * @param str
	 * @return
	 */
	public static float getfloat(String str) {
		if (isNullOrEmpty(str)) {
			return 0.0f;
		}
		try {
			return new Float(str.trim());
		} catch (NumberFormatException e) {
			return 0.0f;
		}
	}

	/**
	 * 将一个字符串转换为float,如果字符串为null或者""返回null
	 * 
	 * @param str
	 * @return
	 */
	public static Float getFloat(String str) {
		if (isNullOrEmpty(str)) {
			return null;
		}
		try {
			return new Float(str.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * 将一个字符串转换为double,如果字符串为null或者""返回0.0
	 * 
	 * @param str
	 * @return
	 */
	public static double getdouble(String str) {
		if (isNullOrEmpty(str)) {
			return 0.0;
		}
		try {
			return new Double(str.trim());
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}

	/**
	 * 将一个字符串转换为Double,如果字符串为null或者""返回null
	 * 
	 * @param str
	 * @return
	 */
	public static Double getDouble(String str) {
		if (isNullOrEmpty(str)) {
			return null;
		}
		try {
			return new Double(str.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * 将一个字符串转换为Date,如果字符串为null或者""返回null
	 * 
	 * @param str
	 * @return
	 */
	public static Date getDate(String str) {
		if (isNullOrEmpty(str)) {
			return null;
		}
		try {
			return Date.valueOf(str.trim());
		} catch (Exception e) {
			return new Date(new Long(str));
		}
	}

	/**
	 * 将一个字符串转换为Instant,如果字符串为null或者""返回null
	 * 
	 * @param str
	 * @return
	 * @throws NumberFormatException
	 * @throws DateTimeParseException
	 * @throws RuntimeException
	 */
	public static Instant getInstant(String str) throws NumberFormatException, DateTimeParseException, RuntimeException {
		if (isNullOrEmpty(str)) {
			return null;
		}
		try {
			return Instant.parse(str);
		} catch (Exception e) {
			return Instant.ofEpochMilli(new Long(str));
		}
	}

	/**
	 * 将一个字符串转换为JsonObject,如果字符串为null或者""返回null
	 * 
	 * @param str
	 * @return
	 */
	public static JsonObject getJsonObject(String str) {
		if (isNullOrEmpty(str)) {
			return null;
		}
		try {
			return new JsonObject(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将一个字符串转换为JsonArray,如果字符串为null或者""返回null
	 * 
	 * @param str
	 * @return
	 */
	public static JsonArray getJsonArray(String str) {
		if (isNullOrEmpty(str)) {
			return null;
		}
		try {
			return new JsonArray(str);
		} catch (Exception e) {
			return null;
		}
	}

}
