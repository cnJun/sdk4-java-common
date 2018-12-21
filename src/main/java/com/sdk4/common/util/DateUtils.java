package com.sdk4.common.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

/**
 * @author sh
 */
public class DateUtils {
    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Date parseDate(String str) {
        Date result = null;

        try {
            if (str.length() == 19 && str.contains("-") && str.contains(":")) {
                result = DateTime.parse(str, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
            } else if (str.length() == 16 && str.contains("-") && str.contains(":")) {
                result = DateTime.parse(str, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")).toDate();
            } else if (str.length() == 14) {
                result = DateTime.parse(str, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
            } else if (str.length() == 10 && str.contains("-")) {
                result = DateTime.parse(str, DateTimeFormat.forPattern("yyyy-MM-dd")).toDate();
            } else if (str.length() == 8) {
                result = DateTime.parse(str, DateTimeFormat.forPattern("yyyyMMdd")).toDate();
            }
        } catch (Exception e) {
        }

        return result;
    }

    public static String formatDate(Date date, String format) {
        return new DateTime(date).toString(format);
    }
}
