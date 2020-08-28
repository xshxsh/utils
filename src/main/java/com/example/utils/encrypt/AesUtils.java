package com.example.utils.encrypt;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author xsh
 * @date : 2020-8-27 14:28
 * @description: AES解密、加密处理
 */


public class AesUtils {

    //AES加密算法
    private static final String ARITHMETIC = "AES";
    //ECB加密模式，PKCS5Padding填充
    private static final String CIPHER_MODE = "AES/ECB/PKCS5Padding";

    /**
     * AES加密
     *
     * @param key     密码(固定的32位字符串)
     * @param content 待加密内容
     * @return base64字符串
     */
    public static String encrypt(String key, String content) {
        try {
            //构造密钥生成器，指定为AES算法
            KeyGenerator kgen = KeyGenerator.getInstance(ARITHMETIC);
            //初始化密钥生成器
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), ARITHMETIC));
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            //LogUtils.error("AES加密发生异常，待加密内容：{}，报错：", content, e);
            return null;
        }
    }

    /**
     * AES解密
     *
     * @param key     密码(固定的32位字符串)
     * @param content 待解密内容
     * @return 明文
     */
    public static String decrypt(String key, String content) {
        try {
            //构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator kgen = KeyGenerator.getInstance(ARITHMETIC);
            //初始化密钥生成器
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), ARITHMETIC));
            //采用base64算法进行转码,避免出现中文乱码
            byte[] encrypt = Base64.decodeBase64(content);
            byte[] decryptBytes = cipher.doFinal(encrypt);
            return new String(decryptBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            //LogUtils.error("AES解密发生异常，待解密内容：{}，报错：", content, e);
            return null;
        }
    }

}
