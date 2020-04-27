package org.framework.hsven.executor.support;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolMaintainThread extends Thread {
    private static Logger logger = LoggerFactory.getLogger(ThreadPoolMaintainThread.class);
    //1分钟检查一次
    private final long loopTimeMS = 1000 * 60 * 1;

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    Thread.sleep(loopTimeMS);
                    check();
                } catch (Exception e) {
                    logger.error("ThreadPoolMaintainThread run Exception.", e);
                } catch (Throwable e) {
                    logger.error("ThreadPoolMaintainThread run Throwable.", e);
                }
            }

        } catch (Exception e) {
            logger.error("ThreadPoolMaintainThread run Exception.", e);
        } catch (Throwable e) {
            logger.error("ThreadPoolMaintainThread run Throwable.", e);
        }
    }

    private void check() {
        ThreadPoolExecutorManager.getInstance().clearExpire();
    }
}
