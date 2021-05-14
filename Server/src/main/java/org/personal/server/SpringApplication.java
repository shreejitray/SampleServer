package org.personal.server;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.spring.SpringResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

public class SpringApplication {
    @Autowired
    private ApplicationContext ctx;

    private String address;

    private List<? extends Object> providers;

    @Autowired
    private SpringBus bus;


    @Bean
    public Server jaxRsServer() {
        try {
            List<ResourceProvider> resourceProviders = new ArrayList<ResourceProvider>();
            String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                if (ctx.findAnnotationOnBean(beanName, Path.class) != null) {
                    SpringResourceFactory factory = new SpringResourceFactory(beanName);
                    factory.setApplicationContext(ctx);
                    resourceProviders.add(factory);
                }
            }
            JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
            factory.setAddress(getAddress());
            factory.setProviders(providers);
            factory.setBus(bus);
            if (resourceProviders.size() > 0) {
                factory.setResourceProviders(resourceProviders);
            }
            return factory.create();
        }catch (Exception e){
            System.out.println("Somthing");
            throw e;
        }
    }
    public List<? extends Object> getProviders() {
        return providers;
    }

    public void setProviders(List<? extends Object> providers) {
        this.providers = providers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
