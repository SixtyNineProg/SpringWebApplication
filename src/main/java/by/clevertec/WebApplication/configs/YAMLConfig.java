package by.clevertec.WebApplication.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cache")
public class YAMLConfig {
    private Boolean enabled;
    private Boolean LRU;
    private Boolean LFU;
}
