package org.framework.hsven.dataloader.exception;

/**
 * 内部的异常,如果捕获到此异常,可以不用再次打印异常信息,因为在抛出此异常时,正常应该已经打印过
 */
public class LoaderException extends RuntimeException {
    public LoaderException(String message) {
        super(message);
    }

    public LoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
