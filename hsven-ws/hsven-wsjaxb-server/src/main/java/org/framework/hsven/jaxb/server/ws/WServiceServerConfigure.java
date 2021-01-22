package org.framework.hsven.jaxb.server.ws;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 *
 */
@EnableWs
@Configuration
public class WServiceServerConfigure {

    //定义的不同的xsd服务文件中的命名空间
    //要与xsd文件中的命名空间一致
//    public static final String NAMESPACE_URI = "http://wservice.hios.com/proxy/eoms/service";
//    public static final String PORT_NAME = "EomsPort";
//    public static final String WEB_PATH = "ws";
    public static final String NAMESPACE_URI = Constants.NMS_MANAGER_TARGET_NAMESPACE;
    public static final String PORT_NAME = Constants.NMS_MANAGER_PORT_TYPE_NAME;
    public static final String WEB_PATH = "ws";

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/" + WEB_PATH + "/*");
    }

    /**
     * 依据定义的xsd文件,发布
     * 生成的bean的名称作为发布服务的wsdl文件访问路径
     *
     * @param xsdSchema
     * @return
     */
    @Bean(name = "nms")
    public DefaultWsdl11Definition defaultWsdl11Definition(@Qualifier("nmsSchema") XsdSchema xsdSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        //设置具体的bean名称
        wsdl11Definition.setPortTypeName(PORT_NAME);
        //对外访问的Web Service服务的路径
        //最终组成的路径是以下两种方式都可以对外访问
        //http://127.0.0.1:38620/${本Web服务的名称}/ws(此处设置的setLocationUri地址)/
        //http://127.0.0.1:38620/${本Web服务的名称}/ws(此处设置的setLocationUri地址)/PortTypeName(上面设置的portTypeName值)
        //http://127.0.0.1:38620/proxy-jaxb-server/ws/nms(当前方法生成的bean的名称).wsdl
        wsdl11Definition.setLocationUri("/" + WEB_PATH);
        wsdl11Definition.setTargetNamespace(NAMESPACE_URI);
        wsdl11Definition.setSchema(xsdSchema);
        return wsdl11Definition;
    }

    /**
     * 导入具体定义的xsd文件
     * 如果有多个xsd服务需要发布,就生成多个XsdSchema,取不同的bean名称
     *
     * @return
     */
    @Bean
    @Qualifier("nmsSchema")
    public XsdSchema nmsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/nms.xsd"));
    }
}
