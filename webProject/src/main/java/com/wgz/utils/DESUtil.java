package com.wgz.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.bouncycastle.util.encoders.Base64;



/**
 * @创建人 caiyonggang
 * @创建时间 2016年12月28日上午10:05:13
 * @说明 DES加密 本系统存储敏感数据进行加密存储的加密方法
 * @备注
 */
public class DESUtil {
	/**
	 * @创建人 caiyonggang
	 * @创建时间 2016年12月28日上午10:08:16
	 * @说明 指定加密的key的DES加密重载方法
	 * @备注
	 * @param encryptString
	 * @param encryptKey
	 * @return
	 */
	public static String encryptDES(String encryptString, String encryptKey) {
		try {
			
			DESKeySpec dks = new DESKeySpec((encryptKey).getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey,  new SecureRandom());
			byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));
			return new String(Base64.encode(encryptedData));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	/**
	 * @创建人 caiyonggang
	 * @创建时间 2016年12月28日上午10:08:47
	 * @说明 指定key的DES解密重载方法
	 * @备注
	 * @param decryptString
	 * @param decryptKey
	 * @return
	 */
	public static String decryptDES(String decryptString, String decryptKey) {
		try {
			DESKeySpec dks = new DESKeySpec((decryptKey).getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey,  new SecureRandom());
			byte decryptedData[] = cipher.doFinal(Base64.decode(decryptString.getBytes()));
			return new String(decryptedData,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
