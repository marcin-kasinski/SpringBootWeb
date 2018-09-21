package mk.metrics;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Component
public class SampleMetricBean {

	private final Counter counter;
	private final Timer timer1;
	private final Timer timer2;

	public SampleMetricBean(MeterRegistry registry) {
		
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Registering");
		this.counter = registry.counter("MKWEB_6_received.messages");
		this.timer1 = registry.timer("MKWEB_exec_time1");
		this.timer2 = registry.timer("MKWEB_exec_time2");
	}

	public void handleMessage(String message) {
		
		System.out.println("handleMessage START");
		this.counter.increment();
		System.out.println("handleMessage END");
	}

	public void handleTimerNanoseconds(long duration) {
		
		System.out.println("handleTimer START / duration: nanoseconds : "+duration+" / miliseconds :"+duration/1000000);
		//this.timer.record(duration, TimeUnit.NANOSECONDS);
		this.timer1.record(Math.abs(duration/1000000), TimeUnit.MILLISECONDS);
		this.timer2.record(Math.abs(duration/1000000000), TimeUnit.SECONDS);
		System.out.println("handleTimer END");
	}
}