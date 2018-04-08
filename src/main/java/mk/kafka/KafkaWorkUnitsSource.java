package mk.kafka;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface KafkaWorkUnitsSource {

    String CHANNEL_NAME = "worksChannelKafka";

    @Output
    MessageChannel worksChannelKafka();

}