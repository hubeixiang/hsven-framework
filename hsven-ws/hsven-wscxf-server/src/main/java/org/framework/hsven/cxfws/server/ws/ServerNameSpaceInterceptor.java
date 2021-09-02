package org.framework.hsven.cxfws.server.ws;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.model.ServiceInfo;

/**
 * @description:
 * @author: sven
 * @create: 2021-09-02
 **/

public class ServerNameSpaceInterceptor extends AbstractPhaseInterceptor<Message> {


    public ServerNameSpaceInterceptor() {
        super(Phase.RECEIVE);
    }

    public static ServerNameSpaceInterceptor of() {
        return new ServerNameSpaceInterceptor();
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        for (ServiceInfo si : message.getExchange().getService().getServiceInfos()) {
            // 忽略掉命名空间的关键
            si.setProperty("soap.force.doclit.bare", true);
        }
    }
}
