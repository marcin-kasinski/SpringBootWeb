package mk;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.sleuth.zipkin2.ZipkinProperties;
import org.springframework.cloud.sleuth.zipkin2.ZipkinRestTemplateCustomizer;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import brave.sampler.Sampler;
import zipkin2.Span;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;

//@EnableDiscoveryClient
@SpringBootApplication
public class WebApp {
	
	 private static Logger log = LoggerFactory.getLogger(WebApp.class);

	

	public static void main(String[] args) {

		System.out.println("Version 21.02.2019");
		System.out.println("Environment variables");
		
//		UUID uniqueKey = UUID.randomUUID();
//		String span=UUID.randomUUID().toString();
		
//		String spanid= (UUID.randomUUID().toString()).replace("-","").substring(0, 16);
		
//	    System.out.println ("uniqueKey:"+ span);		


		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
			System.out.format("%s=%s%n", envName, env.get(envName));
		}

		SpringApplication.run(WebApp.class, args);
	}
	/*
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
	*/
	/*
	// Use this for debugging (or if there is no Zipkin server running on port 9411)
		@Bean
		@ConditionalOnProperty(value = "sample.zipkin.enabled", havingValue = "false")
		public Reporter<Span> spanReporter() {
			return Reporter.CONSOLE;
	}	
*/
		/*
		   @Bean
		    public CommonsRequestLoggingFilter logFilter() {
		        CommonsRequestLoggingFilter filter
		          = new CommonsRequestLoggingFilter();
		        filter.setIncludeQueryString(true);
		        filter.setIncludePayload(true);
		        filter.setMaxPayloadLength(10000);
		        filter.setIncludeHeaders(false);
		        filter.setAfterMessagePrefix("REQUEST DATA : ");
		        return filter;
		    }
*/
}
