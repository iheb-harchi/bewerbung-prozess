package org.mycompany.bewerbungssystem.infrastructure.startup;
import org.mycompany.bewerbungssystem.infrastructure.config.FlywayRunner;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterDeploymentValidation;
import jakarta.inject.Inject;

@ApplicationScoped
public class AppStart {

    @Inject
    private FlywayRunner flywayRunner;

    public void onStart(@Observes AfterDeploymentValidation event) {
        flywayRunner.runMigrations();
    }
}
