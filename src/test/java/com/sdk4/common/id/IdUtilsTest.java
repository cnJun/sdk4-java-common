package com.sdk4.common.id;

import org.junit.Test;

import java.util.UUID;

/**
 * id test
 *
 * @author sh
 * @date 2018/10/30
 */
public class IdUtilsTest {
    @Test
    public void normal() {
        UUID id1 = IdUtils.fastUUID();
        UUID id2 = IdUtils.fastUUID();

        System.out.println("UUID1:" + id1);
        System.out.println("UUID2:" + id2);
    }
}
