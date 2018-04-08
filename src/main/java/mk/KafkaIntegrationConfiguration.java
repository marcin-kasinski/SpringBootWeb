package mk;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;

import mk.kafka.KafkaWorkUnitsSource;

@Configuration
@EnableBinding(KafkaWorkUnitsSource.class)
@IntegrationComponentScan
public class KafkaIntegrationConfiguration {
}