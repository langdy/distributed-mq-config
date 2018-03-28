package com.yjl.distributed.mq.config.common.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;

/**
 * aes加密解密
 * 
 * @author zhaoyc
 * @version 创建时间：2018年1月22日 上午9:51:16
 */
public class AesUtil {

    public static void main(String[] args) {
        // 密钥串
        String secretKey = "x99D683P7sqwlPrMaAlqsI5ncPclJRSUb4";
        // 密码的明文
        String str = "密码的明文： My world is full of wonders! No body can match my spirit";

        // 密码加密后的密文
        byte[] encryptedBytes = AesUtil.encrypt(secretKey, str);
        String encryptedStr = Base64.encodeBase64String(encryptedBytes);
        encryptedStr = Base64Utils.encodeToString(encryptedBytes);
        System.out.println(encryptedStr);

        // 解密后的字符串
        String decryptedStr = AesUtil.decrypt(secretKey, Base64.decodeBase64(encryptedStr));
        decryptedStr = AesUtil.decrypt(secretKey, Base64Utils.decodeFromString(encryptedStr));
        System.out.println(decryptedStr);

    }

    /**
     * 加密
     * 
     * @param secretKey 密钥
     * @param str 明文字符串
     * @return 密文字节数组
     */
    public static byte[] encrypt(String secretKey, String str) {
        try {
            // 密钥数据
            byte[] byteKey = getByteKey(secretKey.getBytes());

            SecretKeySpec secretKeySpec = new SecretKeySpec(byteKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encypted = cipher.doFinal(str.getBytes());
            return encypted;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解密
     * 
     * @param secretKey 密钥
     * @param encrypted 密文字节数组
     * @return 解密后的字符串
     */
    public static String decrypt(String secretKey, byte[] encrypted) {
        try {
            // 密钥数据
            byte[] byteKey = getByteKey(secretKey.getBytes());

            SecretKeySpec secretKeySpec = new SecretKeySpec(byteKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param seed种子数据
     * @return 密钥数据
     */
    private static byte[] getByteKey(byte[] seed) {
        byte[] byteKey = null;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed);
            // AES加密数据块分组长度必须为128比特，密钥长度可以是128比特、192比特、256比特中的任意一个
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byteKey = secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
        }
        return byteKey;
    }
}
