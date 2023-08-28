package com.ciscoadiz.todo.projectsservice.flyway;

import io.quarkus.runtime.Startup;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;

@Startup
public class FlywayMigration {
    FlywayMigration(
            Scheduler scheduler,
            FlywayConfig flywayConfig,
            SessionFactory sessionFactory,
            @ConfigProperty(name = "quarkus.datasource.reactive.url") String datasourceUrl,
            @ConfigProperty(name = "quarkus.datasource.username") String datasourceUsername,
            @ConfigProperty(name = "quarkus.datasource.password") String datasourcePassword
    ) {
        Flyway flyway = Flyway
                .configure()
                .dataSource(
                        datasourceUrl.replace("vertx-reactive:", "jdbc:"),
                        datasourceUsername,
                        datasourcePassword
                )
                .cleanDisabled(!flywayConfig.cleanAtStart())
                .load();
        if (flywayConfig.baselineOnMigrate()) {
            flyway.baseline();
        }
        if (flywayConfig.migrateAtStart()) {
            scheduler.pause();
            if (flywayConfig.cleanAtStart()) {
                flyway.clean();
            }
            flyway.migrate();
            sessionFactory.getSchemaManager().validateMappedObjects();
            scheduler.resume();
        }
    }
    @ConfigMapping(prefix = "application.database.flyway")
    public interface FlywayConfig {
        boolean migrateAtStart();
        @WithDefault("false")
        boolean cleanAtStart();
        @WithDefault("false")
        boolean baselineOnMigrate();

    }
}
