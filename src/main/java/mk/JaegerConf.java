package mk;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
/*
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.reporters.InMemoryReporter;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.jaegertracing.spi.Reporter;
import io.jaegertracing.spi.Sampler;
*/
//@ConditionalOnProperty(value = "opentracing.jaeger.enabled", havingValue = "false", matchIfMissing = false)
@Configuration
public class JaegerConf {

/*
    @Bean
    public io.opentracing.Tracer jaegerTracer() {
         final Reporter reporter = new InMemoryReporter();
         final Sampler sampler = new ConstSampler(false);
         return new JaegerTracer.Builder("untraced-service")
                    .withReporter(reporter)
                    .withSampler(sampler)
                    .build();
        }
  */
    }

