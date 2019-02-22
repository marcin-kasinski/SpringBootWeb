package mk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

//----------------------------------- 2.0.1 -----------------------------------	
import brave.Span;
import brave.Tracer;
//----------------------------------- 2.0.1 -----------------------------------	

//----------------------------------- 1.5.10 -----------------------------------
//import org.springframework.cloud.sleuth.Span;
//import org.springframework.cloud.sleuth.Tracer;
//----------------------------------- 1.5.10 -----------------------------------
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import mk.http.RequestResponseLoggingInterceptor;
import mk.kafka.KafkaWorkUnitGateway;
import mk.metrics.SampleMetricBean;
import mk.rabbitmq.WorkUnit;
import mk.rabbitmq.WorkUnitGateway;



@Controller
@EnableBinding(Source.class)
class WebController {

	@Value("${app.ajax_url}")
	String ajax_url;

	@Value("${app.event_url}")	
	String event_url;

	@Value("${app.event_kafka_url}")	
	String event_kafka_url;

	@Value("${app.rest_url}")
	String rest_url;
	
	@Autowired
	Environment env;
	
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
	private SampleMetricBean sampleBean;

//@Autowired
//private SampleSink gateway;

  
//	@Autowired	
//	private Source source;

    @Autowired
    private WorkUnitGateway workUnitGateway;

    @Autowired
    private KafkaWorkUnitGateway kafkaWorkUnitGateway;
    


	
//    @Autowired	
//    private RabbitTemplate rabbitTemplate;
	

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
	private Tracer tracer;

    
	 private static Logger log = LoggerFactory.getLogger(WebController.class);

    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingVIEW(@ModelAttribute Greeting greeting) {
    	
    	greeting.setContent("Po procesowaniu "+greeting.getContent());
        return "result";
    }

//    @Profile("prd")
    public void sendKafkaRequests(String spanTraceId)
    {
		//wywo�anie rabbitmq i kaka
    	
    	log.info("sendKafkaRequests");

    	Map<String, String> headers = new HashMap<String, String>();
    	
    	headers.put("header1", "mkheader1value");
    	headers.put("header2", "mkheader2value");

    	
   	 WorkUnit sampleWorkUnit = new WorkUnit(spanTraceId, spanTraceId,new Date().toGMTString(), "definition");

 	log.info("sending sampleWorkUnit "+sampleWorkUnit );

   	 //   	 workUnitGateway.generate(sampleWorkUnit, "mkheader1value");
//   	 kafkaWorkUnitGateway.generate(sampleWorkUnit, "mkheader1value");

   	 kafkaWorkUnitGateway.generate(sampleWorkUnit, headers);
		//wywo�anie rabbitmq i kaka

 	log.info("sendKafkaRequests executed");

    }

    public void sendRabbitRequests(String spanTraceId)
    {
		//wywo�anie rabbitmq i kaka
    	

    	Map<String, String> headers = new HashMap<String, String>();
    	
    	headers.put("header1", "mkheader1value");
    	headers.put("header2", "mkheader2value");

    	
   	 WorkUnit sampleWorkUnit = new WorkUnit(spanTraceId, spanTraceId,new Date().toGMTString(), "definition");
//   	 workUnitGateway.generate(sampleWorkUnit, "mkheader1value");
//   	 kafkaWorkUnitGateway.generate(sampleWorkUnit, "mkheader1value");
   	 workUnitGateway.generate(sampleWorkUnit, headers);

    }


    public String getDataFallBack(Model model) {
        return "failed";
    }


    public User addUserRest() {
    	log.info("executing rest_url "+rest_url);
    	
    	User ret=null;
		//restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));

//    	User  user  = restTemplate.getForObject(rest_url, User.class);

//    	log.info("user "+user.getEmail());
    	
    	try {
    	ResponseEntity<User> response =restTemplate.postForEntity(rest_url, new User("jakisemail@mail.com","Marcin"), User.class);

    	ret=response.getBody();
    	}
    	catch(Exception e)
    	{
    		log.info("add user failed"+e);
    	}

    	return ret;

    }

    
    private final WebControllerTaskExecutor webControllerTaskExecutor;
    

    
    @Autowired
    public WebController(WebControllerTaskExecutor webControllerTaskExecutor) {
        this.webControllerTaskExecutor = webControllerTaskExecutor;
    }
  
    @GetMapping("/")
    public Callable<String> welcomeVIEW(@RequestParam(value = "id", defaultValue = ".") String id,Model model,@RequestHeader HttpHeaders headers) {

    	Callable<String> callable = () -> webControllerTaskExecutor.welcomeVIEWExec(id,model,headers);
    	return callable;
    }
 
    @GetMapping("/blocked")
    public String welcomeVIEWblocked(@RequestParam(value = "id", defaultValue = ".") String id,Model model,@RequestHeader HttpHeaders headers) {

    	return webControllerTaskExecutor.welcomeVIEWExec(id,model,headers);
    }
 
    public String welcomeVIEWExec(String id,Model model,HttpHeaders headers) {
    	/*
    	   Span span=tracer.getCurrentSpan();
       	
	       log.info("Sending message...");
	       log.info("span..."+span.getTraceId());
     
			String spanTraceId= Span.idToHex(span.getTraceId());
			String spanId= Span.idToHex(span.getSpanId());

        MessageProperties properties = new MessageProperties();
        properties.setHeader("spanTraceId", spanTraceId);
      	properties.setHeader("spanId", spanId);
        properties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        Message message = new Message("1234567;Branch A;SALES;3000.50;Pending approval".getBytes(), properties);

        rabbitTemplate.convertAndSend("spring-boot", message);

    	log.info("rabbitTemplate sent");

        kafkaTemplate.send("my-topic-mk", "1234567;KAFKA Branch A;SALES;3000.50;Pending approval");

    	log.info("kafkaTemplate sent");
*/
        
    	try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	log.info("IN welcomeVIEW executed");
	    log.info("id="+id);

		sampleBean.handleMessage("XXX");
		long startTime = System.nanoTime();

		System.out.println("Headers start");
    	Set<String> keys = headers.keySet();
		for (String key : keys) {

			List<String> value = headers.get(key);

			int size = value.size();
			
			for (int i=0;i<size;i++) log.info(key + " " +value.get(i));



		}

		System.out.println("Headers end");
    	
//   	gateway.send("12345678901qaz2wsx3edc4rfv");
    	

    	//----------------------------------- 1.5.10 -----------------------------------
//    	Span span=tracer.getCurrentSpan();
//		String spanTraceId= Span.idToHex(span.getTraceId());
		//----------------------------------- 1.5.10 -----------------------------------


//		String spanId= Span.idToHex(span.getSpanId());
//		String parentId= Span.idToHex(span.getParents().get(0).longValue());


    	//----------------------------------- 2.0.1 -----------------------------------		
		Span span=tracer.currentSpan();
		String spanTraceId= span.context().traceIdString();
		//----------------------------------- 2.0.1 -----------------------------------		


//		String spanId= String.valueOf(span.context().spanId());

		
    	model.addAttribute("traceid",spanTraceId);
    	//model.addAttribute("traceid",id);
    	//model.addAttribute("spanid",UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
    	model.addAttribute("spanid",(UUID.randomUUID().toString()).replace("-","").substring(0, 16));
    	
    	model.addAttribute("app_ajax_url",ajax_url);
    	model.addAttribute("id",id);
    	

    	log.info("event_url "+event_url);
    	log.info("event_kafka_url "+event_kafka_url);

    	
    	model.addAttribute("app_event_url",event_url);
    	model.addAttribute("app_event_kafka_url",event_kafka_url);
    	
    	
		
    	log.info("Log data: "+spanTraceId+" "+spanTraceId);

		
    	
    	if (env.acceptsProfiles("prd")) sendRabbitRequests(id);
    	if (env.acceptsProfiles("prd")) sendKafkaRequests(id);

    	 
    	 
    	 //    	 workUnitGateway.generate(MessageBuilder.withPayload(sampleWorkUnit).build());
//    	source.output().send(MessageBuilder.withPayload(sampleWorkUnit ).build());  	 
//    	 source.output().send(sampleWorkUnit);
    	 
    	
    	log.info("Message sent");
    	
    	List<Greeting> allGreetings = new ArrayList();
    	
    	Greeting greeting0 = new Greeting();
    	greeting0.setContent("Content0");
    	allGreetings.add(greeting0);

    	Greeting greeting1 = new Greeting();
    	greeting1.setContent("Content1");

    	allGreetings.add(greeting1);
    	
    	model.addAttribute("map",allGreetings);


//    	User  user  = restTemplate.getForObject("http://localhost:9191/api/get-by-email?email=x@x.com", User.class);
    	
    	
    	User user=addUserRest();
    	
    	System.out.println("user added "+user);

    	System.out.println("END");
    	long duration = System.nanoTime() - startTime;
    			
    	sampleBean.handleTimerNanoseconds(duration);
    	
        return "welcome";
    }



    
/*
	@RequestMapping("/")
	public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}
*/
/*	
	// inject via application.properties
		@Value("${welcome.message:test}")
		private String message = "Hello World";


	@RequestMapping("/x")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "greeting";
	}
*/
}