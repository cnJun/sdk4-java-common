package com.sdk4.common.id;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * id
 *
 * @author sh
 */
public class IdUtils {

    public static UUID fastUUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong());
    }

}
