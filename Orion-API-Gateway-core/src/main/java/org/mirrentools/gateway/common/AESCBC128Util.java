package org.mirrentools.gateway.common;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * AES/CBC/PKCS5Padding加解密工具
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public class AESCBC128Util {
	/**
	 * 默认的key与偏移量
	 */
	public final static String DEFAULT_KEY = "AESCBC128UtilKEY";
	/**
	 * AES加密,模式CBC,填充PKCS5Padding,数据128位,偏移量16位
	 * 
	 * @param str
	 *          内容
	 * @return
	 * @throws Exception
	 */
	public static String encode(String str) throws Exception {
		return encode(str, DEFAULT_KEY, DEFAULT_KEY);
	}
	/**
	 * AES加密,模式CBC,填充PKCS5Padding,数据128位,偏移量16位
	 * 
	 * @param str
	 *          内容
	 * @param key
	 *          密码与偏移量必须大于长度大于16位,超过16位截取前16位
	 * @return
	 * @throws Exception
	 */
	public static String encode(String str, String key) throws Exception {
		return encode(str, key, key);
	}
	/**
	 * AES加密,模式CBC,填充PKCS5Padding,数据128位,偏移量16位
	 * 
	 * @param str
	 *          内容
	 * @param key
	 *          密码必须大于长度大于16位,超过16位截取前16位
	 * @param ivParameter
	 *          偏移量必须大于长度大于16位,超过16位截取前16位
	 * @return
	 * @throws Exception
	 */
	public static String encode(String str, String key, String ivParameter) throws Exception {
		if (key == null || key.length() < 16) {
			throw new RuntimeException("key的size必须大于等于16");
		}
		if (ivParameter == null || ivParameter.length() < 16) {
			throw new RuntimeException("偏移量的size必须大于等于16");
		}
		key = key.substring(0, 16);
		ivParameter = ivParameter.substring(0, 16);
		byte[] kb = key.getBytes("utf-8");
		SecretKeySpec sks = new SecretKeySpec(kb, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 算法/模式/补码方式
		IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, sks, iv);
		byte[] eb = cipher.doFinal(str.getBytes("utf-8"));
		return Base64.getEncoder().encodeToString(eb);
	}
	/**
	 * AES解密,模式CBC,填充PKCS5Padding,数据128位,偏移量16位
	 * 
	 * @param str
	 *          内容
	 * @return
	 * @throws Exception
	 */
	public String decode(String str) throws Exception {
		return decode(str, DEFAULT_KEY, DEFAULT_KEY);
	}

	/**
	 * AES解密,模式CBC,填充PKCS5Padding,数据128位,偏移量16位
	 * 
	 * @param str
	 *          内容
	 * @param key
	 *          密码与偏移量必须大于长度大于16位,超过16位截取前16位
	 * @return
	 * @throws Exception
	 */
	public static String decode(String str, String key) throws Exception {
		return decode(str, key, key);
	}
	/**
	 * AES解密,模式CBC,填充PKCS5Padding,数据128位,偏移量16位
	 * 
	 * @param str
	 *          内容
	 * @param key
	 *          密码必须大于长度大于16位,超过16位截取前16位
	 * @param ivParameter
	 *          偏移量必须大于长度大于16位,超过16位截取前16位
	 * @return
	 * @throws Exception
	 */
	public static String decode(String str, String key, String ivParameter) throws Exception {
		if (key == null || key.length() < 16) {
			throw new RuntimeException("key 的size必须大于等于16");
		}
		if (ivParameter == null || ivParameter.length() < 16) {
			throw new RuntimeException("偏移量的size必须大于等于16");
		}
		key = key.substring(0, 16);
		ivParameter = ivParameter.substring(0, 16);
		byte[] kb = key.getBytes("utf-8");
		SecretKeySpec sks = new SecretKeySpec(kb, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, sks, iv);
		byte[] by = Base64.getDecoder().decode(str);
		byte[] db = cipher.doFinal(by);
		return new String(db);
	}

}
