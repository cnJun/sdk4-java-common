package com.sdk4.common.util;

import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author sh
 */
public class WebUtils {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String[] IP_HEADERS = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "X-Real-IP", "NS-Client-IP" };

    public static final String getRemoteAddress(HttpServletRequest request) {
        String ip = null;
        boolean valid = false;

        for (String header : IP_HEADERS) {
            ip = request.getHeader(header);
            valid = checkIp(ip);
            if (valid) {
                break;
            }
        }

        if (!valid) {
            ip = request.getRemoteAddr();
        }

        int index = ip.indexOf(',');
        if (index != -1) {
            String firstIp = ip.substring(0, index).trim();
            if (checkIp(ip)) {
                ip = firstIp;
            }
        }

        return ip;
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = Maps.newHashMap();

        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            String val = request.getHeader(key);
            headers.put(key.toLowerCase(), val);
        }

        return headers;
    }

    public static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[4096];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }

            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static final boolean checkIp(String ip) {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            return false;
        }

        return true;
    }
}
