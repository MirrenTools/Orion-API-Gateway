package org.mirrentools.gateway.console.common;

import java.security.MessageDigest;
/**
 * MD5工具
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public class MD5Util {
	/** 小写十六进制 */
	private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	/** 大写十六进制 */
	private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	/**
	 * 将字符串编码为小写MD5
	 * 
	 * @param value
	 *          字符串
	 * @return
	 */
	public static String encode(String value) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(value.getBytes());
			return new String(encodeHex(digest, DIGITS_LOWER));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将字符串编码为大写字的MD5
	 * 
	 * @param value
	 * @return
	 */
	public static String encodeUpperCase(String value) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(value.getBytes());
			return new String(encodeHex(digest, DIGITS_UPPER));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将字符串按次数编码为小写MD5
	 * 
	 * @param value
	 *          字符串
	 * @param count
	 *          次数
	 * @return
	 */
	public static String encode(String value, int count) {
		String result = encode(value);
		for (int i = 1; i < count; i++) {
			result = encode(result);
		}
		return result;
	}
	/**
	 * 将字符串按次数编码为大写MD5
	 * 
	 * @param value
	 *          字符串
	 * @param count
	 *          次数
	 * @return
	 */
	public static String encodeUpperCase(String value, int count) {
		String result = encode(value);
		for (int i = 1; i < count; i++) {
			result = encodeUpperCase(result);
		}
		return result;
	}

	/**
	 * 对比小写MD5字符串是否相等
	 * 
	 * @param str
	 *          字符串
	 * @param md5Code
	 *          MD5码
	 * @return
	 */
	public static boolean compare(String str, String md5Code) {
		if (encode(str).equals(md5Code)) {
			return true;
		}
		return false;
	}
	/**
	 * 对比小写MD5字符串是否相等
	 * 
	 * @param str
	 *          字符串
	 * @param encodeCount
	 *          将字符串加密几次
	 * @param md5Code
	 *          MD5码
	 * @return
	 */
	public static boolean compare(String str, int encodeCount, String md5Code) {
		if (encode(str, encodeCount).equals(md5Code)) {
			return true;
		}
		return false;
	}
	/**
	 * 对比大写MD5字符串是否相等
	 * 
	 * @param str
	 *          字符串
	 * @param md5Code
	 *          MD5码
	 * @return
	 */
	public static boolean compareUpperCase(String str, String md5Code) {
		if (encodeUpperCase(str).equals(md5Code)) {
			return true;
		}
		return false;
	}
	/**
	 * 对比大写MD5字符串是否相等
	 * 
	 * @param str
	 *          字符串
	 * @param encodeCount
	 *          将字符串加密几次
	 * @param md5Code
	 *          MD5码
	 * @return
	 */
	public static boolean compareUpperCase(String str, int encodeCount, String md5Code) {
		if (encodeUpperCase(str, encodeCount).equals(md5Code)) {
			return true;
		}
		return false;
	}

	/**
	 * 字节数组转换十六进制
	 * 
	 * @param data
	 * @param toDigits
	 * @return
	 */
	protected static char[] encodeHex(final byte[] data, final char[] toDigits) {
		final int l = data.length;
		final char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}
		return out;
	}

}
