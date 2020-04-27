package org.framework.hsven.executor.actuator;

import org.framework.hsven.executor.model.ExecutorServiceInfo;
import org.framework.hsven.executor.support.ThreadPoolExecutorManager;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;

import java.util.HashMap;
import java.util.Map;

@WebEndpoint(id = "threadpool")
public class ThreadPoolEndpoint {

    @ReadOperation
    public Map<String, ExecutorServiceInfo> getThreadPoolInfo() {
        Map map = new HashMap();
        map.putAll(ThreadPoolExecutorManager.getInstance().infoSerializable());
        return map;
    }
}
