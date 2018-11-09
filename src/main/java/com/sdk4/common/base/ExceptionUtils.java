package com.sdk4.common.base;

import com.sdk4.common.base.annotation.Nullable;
import com.sdk4.common.base.type.UncheckedException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 */
public class ExceptionUtils {

    public static String toString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        e.printStackTrace(pw);

        return sw.toString();
    }

    public static RuntimeException unchecked(@Nullable Throwable t) {
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        if (t instanceof Error) {
            throw (Error) t;
        }

        throw new UncheckedException(t);
    }
}
