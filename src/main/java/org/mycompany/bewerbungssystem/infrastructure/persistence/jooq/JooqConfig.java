package org.mycompany.bewerbungssystem.infrastructure.persistence.jooq;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class JooqConfig {

	@Resource(lookup = "java:jboss/jdbc/OracleDataSource")
	private DataSource dataSource;

	@Produces
	@ApplicationScoped
	public DSLContext dslContext() {
		org.jooq.Configuration configuration = new DefaultConfiguration().set(dataSource).set(SQLDialect.DEFAULT);
		return DSL.using(configuration);
    }
}
