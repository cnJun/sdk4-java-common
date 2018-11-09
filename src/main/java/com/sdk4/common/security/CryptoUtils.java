package com.sdk4.common.security;

import com.sdk4.common.base.ExceptionUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

/**
 * 签名/加解密工具
 *
 * @author sh
 * @date 2018/10/30
 */
public class CryptoUtils {
    private static final String ALG_HMACSHA1 = "HmacSHA1";

    /**
     * RFC2401
     */
    private static final int DEFAULT_HMACSHA1_KEYSIZE = 160;

    public static byte[] hmacSha1(byte[] input, byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, ALG_HMACSHA1);
            Mac mac = Mac.getInstance(ALG_HMACSHA1);
            mac.init(secretKey);
            return mac.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    public static byte[] generateHmacSha1Key() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALG_HMACSHA1);
            keyGenerator.init(DEFAULT_HMACSHA1_KEYSIZE);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }
}
