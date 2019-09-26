package by.clevertec.WebApplication.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cache")
public class CacheConfig {
    private Boolean enabled;
    private String cacheType;
    private int sizeCache;
}
