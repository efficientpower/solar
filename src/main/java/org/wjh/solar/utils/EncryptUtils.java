package org.wjh.solar.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;

import org.apache.commons.codec.digest.DigestUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加密解密编码工具
 * 
 * @author wangjihui
 *
 */
public class EncryptUtils {
	
	public static class Base64Md5ShaUtils{
		public static final String KEY_SHA = "SHA";
		public static final String KEY_MD5 = "MD5";

		/**
		 * BASE64编码
		 * 
		 * @param key
		 * @return
		 * @throws Exception
		 */
		public static String encryptBASE64(byte[] key) throws Exception {
			return (new BASE64Encoder()).encodeBuffer(key);
		}

		/**
		 * BASE64解码
		 * 
		 * @param key
		 * @return
		 * @throws Exception
		 */
		public static byte[] decryptBASE64(String key) throws Exception {
			return (new BASE64Decoder()).decodeBuffer(key);
		}

		/**
		 * MD5加密
		 * 
		 * @param data
		 * @return
		 * @throws Exception
		 */
		public static byte[] encryptMD5(byte[] data) throws Exception {
			MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
			md5.update(data);
			return md5.digest();
		}

		/**
		 * SHA加密
		 * 
		 * @param data
		 * @return
		 * @throws Exception
		 */
		public static byte[] encryptSHA(byte[] data) throws Exception {
			MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
			sha.update(data);
			return sha.digest();
		}
		
		/**
		 * 获取文件的MD5值
		 * 
		 * @param filePath
		 * @return
		 * @throws IOException 
		 * @throws FileNotFoundException 
		 */
		public static String getMd5OfFile(String filePath) throws Exception{
			return getMd5OfFile(new File(filePath));
		}
		
		/**
		 * 获取文件的MD5值
		 * 
		 * @param file
		 * @return
		 * @throws Exception
		 */
		public static String getMd5OfFile(File file) throws Exception{
			FileInputStream fis = null;
			try{
				fis = new FileInputStream(file);
				return DigestUtils.md5Hex(fis);
			}catch(Exception e){
				throw e;
			}finally{
				if(fis != null){
					fis.close();
				}
			}
		}
		
		/**
		 * 获取文件流的MD5
		 * 
		 * @param is
		 * @return
		 * @throws Exception
		 */
		public static String getMd5OfFileInputStream(InputStream is) throws Exception{
			return DigestUtils.md5Hex(is);
		}
		
		/**
		 * 获取字符串的MD5
		 * 
		 * @param str
		 * @return
		 */
		public static String getMd5OfString(String str){
			return DigestUtils.md5Hex(str);
		}
	}
	
	public static class RsaUtils{
		
		public static final String KEY_ALGORTHM = "RSA";//RSA非对称加密算法
		public static final String PUBLIC_KEY = "RSAPublicKey";//公钥
		public static final String PRIVATE_KEY = "RSAPrivateKey";//私钥
		public static final String UPDATE_TIME = "update_time";//密钥对更新时间key
		public static final long EXPIRE_TIME = 10*24*3600*1000;//密钥对过期时间,设置为10天(ms)
		public static final int MAX_ENCRYPT_BLOCK = 117;//RSA最大加密明文大小
		public static final int MAX_DECRYPT_BLOCK = 128;//RSA最大解密密文大小
		public static Map<String, Object> KEY_PAIR_MAP = new ConcurrentHashMap<String, Object>();
		
		/**
		 * 生成密钥对
		 * 
		 * @return
		 * @throws Exception
		 */
		public static Map<String, Object> genKeyPair() throws Exception {
			if(KEY_PAIR_MAP.size()>0 && (System.currentTimeMillis() - (Long)KEY_PAIR_MAP.get(UPDATE_TIME)) < EXPIRE_TIME){
				return KEY_PAIR_MAP;
			}
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
			keyPairGenerator.initialize(1024);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();// 公钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();// 私钥
			KEY_PAIR_MAP.put(PUBLIC_KEY, publicKey);
			KEY_PAIR_MAP.put(PRIVATE_KEY, privateKey);
			KEY_PAIR_MAP.put(UPDATE_TIME, System.currentTimeMillis());
			return KEY_PAIR_MAP;
		}

		/**
		 * 取得公钥，并转化为base64编码的String类型
		 * 
		 * @param keyMap
		 * @return
		 * @throws Exception
		 */
		public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
			Key key = (Key) keyMap.get(PUBLIC_KEY);
			return Base64Md5ShaUtils.encryptBASE64(key.getEncoded());
		}

		/**
		 * 取得私钥，并转化为base64编码的String类型
		 * 
		 * @param keyMap
		 * @return
		 * @throws Exception
		 */
		public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
			Key key = (Key) keyMap.get(PRIVATE_KEY);
			return Base64Md5ShaUtils.encryptBASE64(key.getEncoded());
		}

		/**
		 * 用私钥加密
		 * 
		 * @param data 待加密数据
		 * @param privateKey 私钥
		 * @return
		 * @throws Exception
		 */
		public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
			// 解密密钥
			byte[] keyBytes = Base64Md5ShaUtils.decryptBASE64(privateKey);
			// 取私钥
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
			Key privateKeyObj = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateKeyObj);
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int inputLen = data.length;
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
				    cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return encryptedData;
		}

		/**
		 * 用公钥解密
		 * 
		 * @param data 已经被加密的数据
		 * @param publicKey 公钥
		 * @return
		 * @throws Exception
		 */
		public static byte[] decryptByPublicKey(byte[] data, String publicKey) throws Exception {
			// 对私钥解密
			byte[] keyBytes = Base64Md5ShaUtils.decryptBASE64(publicKey);
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
			Key publicKeyObj = keyFactory.generatePublic(x509EncodedKeySpec);
			// 对数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicKeyObj);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return decryptedData;
		}
	}
	
	public static void main(String args[]) throws Exception{
		while(true){
			Map<String, Object> keyMap = RsaUtils.genKeyPair();
			String data=Base64Md5ShaUtils.getMd5OfFile("E:\\dev.properties");
			String privateKey = RsaUtils.getPrivateKey(keyMap);
			String MD5encry = Base64Md5ShaUtils.encryptBASE64(RsaUtils.encryptByPrivateKey(data.getBytes(), privateKey));
			String publicKey = RsaUtils.getPublicKey(keyMap);
			String MD5decry = new String(RsaUtils.decryptByPublicKey(Base64Md5ShaUtils.decryptBASE64(MD5encry), publicKey));
			
			System.out.println(privateKey);
			System.out.println(publicKey);
			System.out.println(MD5encry);
			System.out.println(MD5decry);
			System.out.println(data);
			System.out.println(UUID.randomUUID().toString());
			
			System.out.println(Base64Md5ShaUtils.getMd5OfString(""));
		}
	}
	
}