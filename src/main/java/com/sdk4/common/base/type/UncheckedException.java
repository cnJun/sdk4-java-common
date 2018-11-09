package com.sdk4.common.base.type;

/**
 * CheckedException
 *
 * @author sh
 */
public class UncheckedException extends RuntimeException {
    public UncheckedException(Throwable wrapped) {
        super(wrapped);
    }

    @Override
    public String getMessage() {
        return super.getCause().getMessage();
    }
}
