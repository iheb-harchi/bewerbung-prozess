package org.mycompany.bewerbungssystem.infrastructure.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FlywayRunner {
    private static final Logger logger = LoggerFactory.getLogger(FlywayRunner.class);


	@Resource(lookup = "java:jboss/jdbc/OracleDataSource")
	private DataSource dataSource;

	public void runMigrations() {
		Flyway flyway = Flyway.configure().dataSource(dataSource) // Konfiguration mit der Datenquelle
				.locations("classpath:db/migration") // Verzeichnis, in dem sich die Migrationsskripte befinden
				.loggers("slf4j") // SLF4J-Logger aktivieren
                .load();

		logger.error("Running migrations...");
        flyway.migrate(); // Führe die Migrationen aus
		logger.error("Migration completed.");
    }
}

