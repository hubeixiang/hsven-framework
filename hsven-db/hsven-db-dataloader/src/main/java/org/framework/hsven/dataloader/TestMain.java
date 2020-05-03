package org.framework.hsven.dataloader;

import org.apache.commons.lang3.time.DateFormatUtils;

public class TestMain {
    public static void main(String[] args){
        long beginMs = System.currentTimeMillis();
        String string = DateFormatUtils.format(beginMs,"yyyy-MM-dd HH:mm:ss.SS");
        System.out.println(string);
    }
}
