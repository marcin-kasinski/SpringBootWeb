package mk.kafka;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

import mk.rabbitmq.WorkUnit;


@MessagingGateway
public interface KafkaWorkUnitGateway {
	@Gateway(requestChannel = KafkaWorkUnitsSource.CHANNEL_NAME)
	void generate(WorkUnit workUnit);

}