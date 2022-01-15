package org.framework.hsven.utils.exception;

/**
 * @description:
 * @author: sven
 * @create: 2021-08-09
 **/

public class NonStackException extends RuntimeException {
    public NonStackException(String message) {
        super(message);
    }

    public static NonStackException of(String message) {
        return new NonStackException(message);
    }
}

