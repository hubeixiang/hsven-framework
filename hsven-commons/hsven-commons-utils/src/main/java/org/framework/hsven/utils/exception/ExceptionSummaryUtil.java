package org.framework.hsven.utils.exception;

/**
 * @description:
 * @author: sven
 * @create: 2021-11-02
 **/

public class ExceptionSummaryUtil {

    public static String summaryInformation(Throwable t) {
        if (t == null) {
            return "java.lang.NullPointerException";
        }

        return internalSummaryInformation(t);
    }

    private static String internalSummaryInformation(Throwable throwable) {
        if (throwable instanceof NullPointerException) {
            return "java.lang.NullPointerException";
        }
        Throwable internal = throwable.getCause();
        if (internal == null) {
            return throwable.getMessage();
        } else {
            return internalSummaryInformation(internal);
        }
    }
}
