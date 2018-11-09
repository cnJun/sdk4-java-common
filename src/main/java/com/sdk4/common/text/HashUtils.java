package com.sdk4.common.text;

import com.sdk4.common.base.annotation.NotNull;
import com.sdk4.common.base.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 封装各种Hash算法的工具类
 */
public class HashUtils {

    private static final ThreadLocal<MessageDigest> MD5_DIGEST = createThreadLocalMessageDigest("MD5");
    private static final ThreadLocal<MessageDigest> SHA_1_DIGEST = createThreadLocalMessageDigest("SHA-1");

    private static SecureRandom random = new SecureRandom();

    // ThreadLocal重用MessageDigest
    private static ThreadLocal<MessageDigest> createThreadLocalMessageDigest(final String digest) {
        return new ThreadLocal<MessageDigest>() {
            @Override
            protected MessageDigest initialValue() {
                try {
                    return MessageDigest.getInstance(digest);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(
                            "unexpected exception creating MessageDigest instance for [" + digest + ']', e);
                }
            }
        };
    }

    public static byte[] sha1(@NotNull byte[] input) {
        return digest(input, get(SHA_1_DIGEST), null, 1);
    }

    public static byte[] sha1(@NotNull String input) {
        return digest(input.getBytes(Charsets.UTF_8), get(SHA_1_DIGEST), null, 1);
    }

    public static byte[] sha1(@NotNull byte[] input, @Nullable byte[] salt) {
        return digest(input, get(SHA_1_DIGEST), salt, 1);
    }

    public static byte[] sha1(@NotNull String input, @Nullable byte[] salt) {
        return digest(input.getBytes(Charsets.UTF_8), get(SHA_1_DIGEST), salt, 1);
    }

    public static byte[] sha1(@NotNull byte[] input, @Nullable byte[] salt, int iterations) {
        return digest(input, get(SHA_1_DIGEST), salt, iterations);
    }

    public static byte[] sha1(@NotNull String input, @Nullable byte[] salt, int iterations) {
        return digest(input.getBytes(Charsets.UTF_8), get(SHA_1_DIGEST), salt, iterations);
    }

    private static MessageDigest get(ThreadLocal<MessageDigest> messageDigest) {
        MessageDigest instance = messageDigest.get();
        instance.reset();
        return instance;
    }

    /**
     * 对字符串进行散列, 支持md5与sha1算法.
     */
    private static byte[] digest(@NotNull byte[] input, MessageDigest digest, byte[] salt, int iterations) {
        // 带盐
        if (salt != null) {
            digest.update(salt);
        }

        // 第一次散列
        byte[] result = digest.digest(input);

        // 如果迭代次数>1，进一步迭代散列
        for (int i = 1; i < iterations; i++) {
            digest.reset();
            result = digest.digest(result);
        }

        return result;
    }
}
