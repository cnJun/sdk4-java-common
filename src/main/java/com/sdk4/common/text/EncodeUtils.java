package com.sdk4.common.text;

import com.google.common.io.BaseEncoding;

/**
 * 常用编解码工具
 *
 * @author sh
 */
public class EncodeUtils {

    public static String encodeHex(byte[] input) {
        return BaseEncoding.base16().encode(input);
    }

    public static byte[] decodeHex(CharSequence input) {
        return BaseEncoding.base16().decode(input);
    }

    public static String encodeBase64(byte[] input) {
        return BaseEncoding.base64().encode(input);
    }

    public static byte[] decodeBase64(CharSequence input) {
        return BaseEncoding.base64().decode(input);
    }
}
