package org.framework.hsven.cxfws.server.ws;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.xml.ws.Endpoint;

@Configuration
public class CxfConfig implements WebMvcConfigurer {
    @Autowired
    @Qualifier(Bus.DEFAULT_BUS_ID)
    private SpringBus bus;

    @Autowired
    private IWebServiceEomsManager iWebServiceEomsManager;

    @Autowired
    private IWebServiceNmsManager iWebServiceNmsManager;

//    @Bean(name = Bus.DEFAULT_BUS_ID)
//    public SpringBus springBus() {
//        SpringBus springBus = new SpringBus();
//        LoggingFeature logFeature = new LoggingFeature();
//        logFeature.setPrettyLogging(true);
//        logFeature.initialize(springBus);
//        springBus.getFeatures().add(logFeature);
//        return springBus;
//    }

    @Bean
    @Qualifier("eomsEndpoint")
    public Endpoint eomsEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, iWebServiceEomsManager);
        endpoint.publish("/" + Constants.EOMS_MANAGER_PORT_TYPE_NAME);
        return endpoint;
    }

    @Bean
    @Qualifier("nmsEndpoint")
    public Endpoint nmsEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, iWebServiceNmsManager);
        endpoint.publish("/" + Constants.NMS_MANAGER_PORT_TYPE_NAME);
        return endpoint;
    }

}
