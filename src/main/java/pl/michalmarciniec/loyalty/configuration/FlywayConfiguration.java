package pl.michalmarciniec.loyalty.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class FlywayConfiguration {
    @Value("${loyalty-program.db.samplesPath}")
    private String dbSamplesPath;

    @Bean
    FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            if (dbSamplesPath != null && !dbSamplesPath.isEmpty()) {
                List<String> locations = new ArrayList<>(Arrays.asList(flyway.getLocations()));
                locations.add("classpath:flyway/sample");
                flyway.setLocations(locations.toArray(new String[0]));
            }
            flyway.migrate();
        };
    }

}
