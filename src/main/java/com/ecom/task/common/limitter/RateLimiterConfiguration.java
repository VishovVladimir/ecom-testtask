package com.ecom.task.common.limitter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rate-limiter")
public class RateLimiterConfiguration {
    private long requestLimit;
    private long timePeriod;

}
