package org.mirrentools.gateway.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * AES加解密工具
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public class AESUtil {
	private static final Logger LOG = LogManager.getLogger(AESUtil.class);

	// 加解密的钥匙
	private static final String defaultKey = "com.zhunei.qmz-Mirren";

	/**
	 * 使用128位AES加密<br>
	 * 
	 * @param content
	 *          内容
	 * @param aesKey
	 *          加密钥匙
	 * @return
	 */
	public static String encodeAES(String content, String aesKey) {
		try {
			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			// 2.根据ecnodeRules规则初始化密钥生成器
			// 生成一个128位的随机源,根据传入的字节数组
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(aesKey.getBytes());
			keygen.init(128, random);
			// 3.产生原始对称密钥
			SecretKey originalKey = keygen.generateKey();
			// 4.获得原始对称密钥的字节数组
			byte[] raw = originalKey.getEncoded();
			// 5.根据字节数组生成AES密钥
			SecretKey key = new SecretKeySpec(raw, "AES");
			// 6.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance("AES");
			// 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
			byte[] byteEncode = content.getBytes("utf-8");
			// 9.根据密码器的初始化方式--加密：将数据加密
			byte[] byteAES = cipher.doFinal(byteEncode);
			// 10.将加密后的数据转换为字符串
			String encodeAES = Base64.getEncoder().encodeToString(byteAES);
			// 11.将字符串返回
			return encodeAES;
		} catch (InvalidKeyException e) {
			LOG.error("AES加密失败(这是无效的密钥无效编码,长度错误,初始化,等):", e);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("AES加密失败(这个环境下不能用这个加密算法):", e);
		} catch (NoSuchPaddingException e) {
			LOG.error("AES加密失败(当请求特定的填充机制但在环境中不可用):", e);
		} catch (UnsupportedEncodingException e) {
			LOG.error("AES加密失败(不支持的字符编号):", e);
		} catch (IllegalBlockSizeException e) {
			LOG.error("AES加密失败(分组密码的数据长度不正确):", e);
		} catch (BadPaddingException e) {
			LOG.error("AES加密失败(输入数据需要特定的填充机制，但数据未正确填充时):", e);
		}
		// 如果有错就返加 null
		return null;
	}

	/**
	 * 加密 1.构造密钥生成器 2.根据ecnodeRules规则初始化密钥生成器 3.产生密钥 4.创建和初始化密码器 5.内容加密 6.返回字符串
	 */
	public static String encodeAES(String content) {
		return encodeAES(content, defaultKey);
	}

	/**
	 * 解密 解密过程： 1.同加密1-4步 2.将加密后的字符串反纺成byte[]数组 3.将加密内容解密
	 */
	public static String decodeAES(String content) {
		return decodeAES(content, defaultKey);
	}

	public static String decodeAES(String content, String aesKey) {
		try {
			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			// 2.根据ecnodeRules规则初始化密钥生成器
			// 生成一个128位的随机源,根据传入的字节数组
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(aesKey.getBytes());
			keygen.init(128, random);
			// 3.产生原始对称密钥
			SecretKey originalKey = keygen.generateKey();
			// 4.获得原始对称密钥的字节数组
			byte[] raw = originalKey.getEncoded();
			// 5.根据字节数组生成AES密钥
			SecretKey key = new SecretKeySpec(raw, "AES");
			// 6.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance("AES");
			// 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 8.将加密并编码后的内容解码成字节数组
			byte[] byteContent = Base64.getDecoder().decode(content);
			// 解密
			byte[] byteDecode = cipher.doFinal(byteContent);
			String decodeAES = new String(byteDecode, "utf-8");
			return decodeAES;
		} catch (NoSuchAlgorithmException e) {
			LOG.error("AES解密失败(当前环境下不能用这个加密算):", e);
		} catch (NoSuchPaddingException e) {
			LOG.error("AES解密失败(特定的填充机制但在环境中不可用):", e);
		} catch (InvalidKeyException e) {
			LOG.error("AES解密失败(无效的密钥:无效编码,长度错误,初始化,等):", e);
		} catch (IOException e) {
			LOG.error("AES解密失败(IO异常):", e);
		} catch (IllegalBlockSizeException e) {
			LOG.error("AES解密失败(分组密码的数据长度不正确):", e);
		} catch (BadPaddingException e) {
			LOG.error("AES解密失败(数据需要特定的填充机制，但数据未正确填充时):", e);
		}
		// 如果有错就返加null
		return null;
	}
}
