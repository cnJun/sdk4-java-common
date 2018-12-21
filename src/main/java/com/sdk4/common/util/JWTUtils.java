package com.sdk4.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sh on 2018/6/20.
 */
public class JWTUtils {
    private JWTUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final String SECRET = "JWT-secret-forsdk4.2018.key";
    private static final String DEFAULT_ISS = "SDK4.COM";
    private static final int DEFAULT_EXPIRE_DAYS = 100;

    public static String createToken(Map<String, String> data, Date expireTime) throws UnsupportedEncodingException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        if (data == null) {
            data = new HashMap<>(0);
        }

        // 过期时间
        Date now = new Date();
        if (expireTime == null) {
            Calendar time = Calendar.getInstance();
            time.add(Calendar.DATE, DEFAULT_EXPIRE_DAYS);
            expireTime = time.getTime();
        }

        JWTCreator.Builder builder = JWT.create().withHeader(map).withClaim("iss", data.containsKey("iss") ? data.remove("iss") : DEFAULT_ISS);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            builder = builder.withClaim(entry.getKey(), entry.getValue());
        }

        String token = builder.withIssuedAt(now)
                .withExpiresAt(expireTime)
                .sign(Algorithm.HMAC256(SECRET));

        return token;
    }

    public static Map<String, String> verifyToken(String token) throws TokenExpiredException, UnsupportedEncodingException {
        Map<String, String> result = Maps.newHashMap();
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> data = jwt.getClaims();
            for (Map.Entry<String, Claim> entry : data.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue().asString();
                if (val != null) {
                    result.put(key, val);
                }
            }
        } catch (TokenExpiredException e) {
            throw e;
        } catch (UnsupportedEncodingException e) {
            throw e;
        }

        return result;
    }

    public static String createJsonToken(String jsonString) throws UnsupportedEncodingException {
        Map<String, String> params = Maps.newHashMap();
        params.put("JSON", jsonString);

        return createToken(params, null);
    }

    public static String verifyJsonToken(String token) throws UnsupportedEncodingException {
        String jsonString;

        Map<String, String> ret = verifyToken(token);

        jsonString = ret.get("JSON");

        return jsonString;
    }
}
