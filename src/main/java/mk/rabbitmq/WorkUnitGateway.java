package mk.rabbitmq;

import java.util.Map;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;


@MessagingGateway
public interface WorkUnitGateway {
	@Gateway(requestChannel = WorkUnitsSource.CHANNEL_NAME)
	void generate(WorkUnit workUnit,@Header("header1") String header1);

}