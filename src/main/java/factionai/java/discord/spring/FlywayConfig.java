package factionai.java.discord.spring;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

// https://stackoverflow.com/a/61412233
public class FlywayConfig {
  private final Environment env;

  public FlywayConfig(final Environment env) {
    this.env = env;
  }

  @ConditionalOnProperty(value="spring.flyway.enabled")
  @Bean(initMethod = "migrate")
  public Flyway flyway() {
    return new Flyway(Flyway.configure()
        .baselineVersion("0")
        .baselineOnMigrate(true)
        .table(env.getProperty("spring.flyway.table"))
        .locations(env.getRequiredProperty("spring.flyway.locations").split(","))
        .dataSource(
            env.getRequiredProperty("spring.flyway.url"),
            env.getRequiredProperty("spring.flyway.user"),
            env.getRequiredProperty("spring.flyway.password")));
  }
}
