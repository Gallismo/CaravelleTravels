package ru.almaz.CaravelleTravels.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:/bot.properties")
public class BotConfig {

    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;
}
