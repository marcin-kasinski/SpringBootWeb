package mk;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.sleuth.zipkin2.ZipkinRestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ZipkinConfig {
	@Bean ZipkinRestTemplateCustomizer myCustomizer() {
		return new ZipkinRestTemplateCustomizer() {
			@Override
			public void customize(RestTemplate restTemplate) {
				// customize the RestTemplate
				

				//set interceptors/requestFactory
				ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
				List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
				ris.add(ri);
				restTemplate.setInterceptors(ris);
				restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
				
				
			}
		};
	}
}
