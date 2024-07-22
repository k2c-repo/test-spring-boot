package com.ryon.config.autoconfig;

import com.ryon.config.MyAutoConfiguration;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Objects;

@MyAutoConfiguration
public class ServerPropertiesConfig {
    @Bean
    public ServerProperties serverProperties (Environment env) {
        return Binder.get(env).bind("", ServerProperties.class).get();
//        ServerProperties properties = new ServerProperties();
//        properties.setContextPath(env.getProperty("contextPath"));
//        properties.setPort(Integer.parseInt(Objects.requireNonNull(env.getProperty("port"))));
//        return properties;
    }
}
