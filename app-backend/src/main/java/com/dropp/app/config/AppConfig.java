package com.dropp.app.config;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static com.dropp.app.common.Constant.SRID;

@Configuration
@EnableJpaAuditing
public class AppConfig {

    @Bean
    public GeometryFactory getGeometryFactory() {
        return new GeometryFactory(new PrecisionModel(), SRID);
    }
}
