package mk.kafka;

import java.util.Map;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import mk.rabbitmq.WorkUnit;


@MessagingGateway
public interface KafkaWorkUnitGateway {
	@Gateway(requestChannel = KafkaWorkUnitsSource.CHANNEL_NAME)
	void generate(@Payload WorkUnit workUnit, @Headers Map<String, String> headers);

}