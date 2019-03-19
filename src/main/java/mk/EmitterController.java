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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

//import javax.servlet.http.HttpServletRequest;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import mk.http.RequestResponseLoggingInterceptor;
import mk.kafka.KafkaWorkUnitGateway;
import mk.metrics.SampleMetricBean;
import mk.rabbitmq.WorkUnit;
import mk.rabbitmq.WorkUnitGateway;



@Controller
@EnableBinding(Source.class)
class EmitterController {


    
	 private static Logger log = LoggerFactory.getLogger(EmitterController.class);

	  //  private ExecutorService executor 
	   //   = Executors.newCachedThreadPool();
	 
	    @GetMapping("/rbe")
	    public ResponseEntity<ResponseBodyEmitter> handleRbe() {
	        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

	            nonBlockingService.execute(() -> {
	                try {
	                	
	                    emitter.send("/rbe" + " @ " + new Date(), MediaType.TEXT_PLAIN);
	                	emitter.complete();
	                    log.info("/rbe" + " @ " + new Date());
	                    
	                } catch (Exception ex) {
	                      System.out.println(ex);
	                      emitter.completeWithError(ex);
	                }
	            });

	            return new ResponseEntity(emitter, HttpStatus.OK);
	}
	 
	 
	 private ExecutorService nonBlockingService = Executors
		      .newCachedThreadPool();
		     
		    @GetMapping("/sse")
		    public SseEmitter handleSse() {
		         SseEmitter emitter = new SseEmitter();
		         nonBlockingService.execute(() -> {
		             try {
		            	 
		            	 
		         
		            	 
		                 emitter.send("/sse" + " @ " + new Date());
		                 // we could send more events
		                 emitter.complete();
		                 
		                 
		             } catch (Exception ex) {
		                 emitter.completeWithError(ex);
		             }
		         });
		         return emitter;
		    }   
		    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*		    
		    
    @GetMapping("/srb")
    @Async("threadPoolTaskExecutor")
    public ResponseEntity<StreamingResponseBody> handlesrb(HttpServletRequest r) {
        StreamingResponseBody stream = out -> {
        	

        //	for (int i=0;i<20;i++)
//        	{
                String msg = "/srb" + " @ " + new Date();
                
                try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX: "+msg);
                out.write(msg.getBytes());
                out.flush();
  //      	}
        	
        };
        return new ResponseEntity(stream, HttpStatus.OK);
    }
    
*/  
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    
    

}