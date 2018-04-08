package mk;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;

import mk.rabbitmq.WorkUnitsSource;

@Configuration
@EnableBinding(WorkUnitsSource.class)
@IntegrationComponentScan
public class IntegrationConfiguration {
}