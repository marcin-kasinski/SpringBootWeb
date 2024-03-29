package mk.metrics;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Component
public class SampleMetricBean {

	 private static Logger log = LoggerFactory.getLogger(SampleMetricBean.class);

	private final Counter counter;
	private final Timer timer1;
	private final Timer timer2;
	private final Timer timer3;

	public SampleMetricBean(MeterRegistry registry) {
		
		
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Registering\nline2\nline3");
		this.counter = registry.counter("MKWEB_6_received.messages");
		this.timer1 = registry.timer("MKWEB_exec_time1");
		this.timer2 = registry.timer("MKWEB_exec_time2");
		
		this.timer3= Timer.builder("MKWEB_exec_time")
				.description("Time spent on Loading main web page")
  .tag("host", "MYHOST")
  .tag("region", "us-east-1")
  .register(registry);


		
	}

	public void handleMessage(String message) {
		
		System.out.println("handleMessage START");
		this.counter.increment();
		System.out.println("handleMessage END");
	}

	public void handleTimerNanoseconds(long duration) {
		
		log.info("handleTimer START / duration: nanoseconds : "+duration+" / miliseconds :"+duration/1000000+"/ sent to prometheus :"+Duration.ofMillis(duration/1000000));
		//this.timer.record(duration, TimeUnit.NANOSECONDS);
		this.timer1.record(Math.abs(duration/1000000), TimeUnit.MILLISECONDS);
		this.timer2.record(Math.abs(duration/1000000000), TimeUnit.SECONDS);
		this.timer3.record(Duration.ofMillis(duration/1000000));
		//this.timer3.record(duration/1000000,TimeUnit.MILLISECONDS);
		log.info("handleTimer END");
	}
}