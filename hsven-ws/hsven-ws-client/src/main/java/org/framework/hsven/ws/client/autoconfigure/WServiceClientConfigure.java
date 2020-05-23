package org.framework.hsven.ws.client.autoconfigure;

import org.framework.hsven.ws.client.service.client.WsClientBussinessOne;
import org.framework.hsven.ws.client.service.client.WsClientBussinessSecond;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 *
 */
@Configuration
public class WServiceClientConfigure {
    private static String[] marshallerContextPaths = new String[]{
            "com.boco.eoms.sheet.base.util",
            "com.boco.webservice"
    };

    /**
     * 利用maven的编译,可以从给定的客户端的wsdl文件,生成对应的class代码
     *
     * @return
     */
    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPaths(marshallerContextPaths);
        return marshaller;
    }


    /**
     * 生成对应的soap客户端访问地址,发布对应的客户端的业务服务
     * 如果有多个地址需要访问可以发布多个对应客户端的业务服务
     *
     * @param jaxb2Marshaller
     * @return
     */
    @Bean
    public WsClientBussinessOne wsClientBussinessOne(Jaxb2Marshaller jaxb2Marshaller, SoapClientUriProperties soapClientUriProperties) {
        WsClientBussinessOne client = new WsClientBussinessOne();
        client.setDefaultUri(soapClientUriProperties.getWsUri1());
        client.setMarshaller(jaxb2Marshaller);
        client.setUnmarshaller(jaxb2Marshaller);
        return client;
    }

    @Bean
    public WsClientBussinessSecond wsClientBussinessSecond(Jaxb2Marshaller jaxb2Marshaller, SoapClientUriProperties soapClientUriProperties) {
        WsClientBussinessSecond client = new WsClientBussinessSecond();
        client.setDefaultUri(soapClientUriProperties.getWsUri2());
        client.setMarshaller(jaxb2Marshaller);
        client.setUnmarshaller(jaxb2Marshaller);
        return client;
    }
}
