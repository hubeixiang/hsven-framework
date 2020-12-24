package org.framework.hsven.ws.client.service.client;

import com.hios.sichuan4a.wsservice.FindUser;
import com.hios.sichuan4a.wsservice.FindUserResponse;
import com.hios.sichuan4a.wsservice.GetUserAmount;
import com.hios.sichuan4a.wsservice.GetUserAmountResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 * 第一个ws客户端访问处理
 */
public class WsClientBussiness4A extends WebServiceGatewaySupport {

    public String getUrl() {
        return getWebServiceTemplate().getDefaultUri();
    }

    /**
     * 两种请求方式
     * 带SoapActionCallback,与不带SoapActionCallback都可以正常访问
     *
     * @return
     */

    public Object getUserAmount() {
        GetUserAmount getUserAmount = new GetUserAmount();
        GetUserAmountResponse getUserAmountResponse = (GetUserAmountResponse) getWebServiceTemplate().marshalSendAndReceive(getUserAmount);
        return getUserAmountResponse.getReturn();
    }

    public Object findUser() {
        FindUser findUser = new FindUser();
        findUser.setUserId("admin");
        FindUserResponse findUserResponse = (FindUserResponse) getWebServiceTemplate().marshalSendAndReceive(findUser);
        return findUserResponse.getReturn();
    }
}
