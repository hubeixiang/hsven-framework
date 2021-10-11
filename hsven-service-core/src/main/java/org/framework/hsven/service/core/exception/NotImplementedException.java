package org.framework.hsven.service.core.exception;

/**
 * @description:
 * @author: sven
 * @create: 2021-09-25
 **/

public class NotImplementedException extends RuntimeException {
    public NotImplementedException(String message) {
        super(message);
    }

    public static NotImplementedException of(String message) {
        return new NotImplementedException(message);
    }
}

