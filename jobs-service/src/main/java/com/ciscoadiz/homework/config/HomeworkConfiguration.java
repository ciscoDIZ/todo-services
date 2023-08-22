package com.ciscoadiz.homework.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;
import io.smallrye.config.WithParentName;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ConfigMapping(prefix = "homework")
public interface HomeworkConfiguration {


    HomeworkSecurityConfiguration security();
    interface HomeworkSecurityConfiguration {

        @WithName("iteration-count")
        Integer iterationCount();
    }
}
