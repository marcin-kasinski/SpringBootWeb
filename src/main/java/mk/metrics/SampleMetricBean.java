package mk.metrics;

import org.springframework.stereotype.Component;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class SampleMetricBean {

	private final Counter counter;

	public SampleMetricBean(MeterRegistry registry) {
		
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Registering");
		this.counter = registry.counter("MKWEB_6_received.messages");
	}

	public void handleMessage(String message) {
		
		System.out.println("handleMessage START");
		this.counter.increment();
		System.out.println("handleMessage END");
	}
}