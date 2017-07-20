package org.timadair;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController(value = "clientService")
public class HystrixAnnotationTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixAnnotationTemplateApplication.class, args);
	}

	@GetMapping(path = "/")
	public String getSomething() {
		return callSomething();
	}

	@HystrixCommand(commandKey = "commandKeyName", fallbackMethod = "fallback",
    commandProperties = {
      @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"), // Options are THREAD, SEMAPHORE
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
      @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
      @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout", value = "true"),
      @HystrixProperty(name = "execution.isolation.thread.interruptOnCancel", value = "false"),
      @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "10"),
      @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "10"),
      @HystrixProperty(name = "fallback.enabled", value = "true"),
      @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),  // Minimum total requests in window for circuit to break
      @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"), // Circuit breaks for how long?
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
      @HystrixProperty(name = "circuitBreaker.forceOpen", value = "false"),
      @HystrixProperty(name = "circuitBreaker.forceClosed", value = "false"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"), // Total tracking window duration
      @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"), // Must evenly divide metrics.rollingStats.timeInMilliseconds
      @HystrixProperty(name = "metrics.rollingPercentile.enabled", value = "true"),
      @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value = "60000"), // Only set on creation
      @HystrixProperty(name = "metrics.rollingPercentile.numBuckets", value = "6"), // Must evenly divide metrics.rollingPercentile.timeInMilliseconds
      @HystrixProperty(name = "metrics.rollingPercentile.bucketSize", value = "100"), // Num requests to keep per bucket for %ile calculations. Only set on creation
      @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds", value = "500"),
      @HystrixProperty(name = "requestCache.enabled", value = "true"),
      @HystrixProperty(name = "requestLog.enabled", value = "true")
	  },
		threadPoolKey = "threadPoolKeyName", threadPoolProperties = {
      @HystrixProperty(name = "coreSize", value = "10"),
      @HystrixProperty(name = "maximumSize", value = "10"),
      @HystrixProperty(name = "maxQueueSize", value = "-1"), // -1 uses SynchronousQueue, else LinkedBlockingQueue
      @HystrixProperty(name = "queueSizeRejectionThreshold", value = "5"), // Exists because maxQueueSize can't be changed dynamically
      @HystrixProperty(name = "keepAliveTimeMinutes", value = "1"), // used when coreSize < maximumSize
      @HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "false"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
      @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),
	  })
  private String callSomething() {
		return "TODO add restTemplate.getForEntity(\"127.0.0.1:8080\", String.class).getBody()";
	}

	private String fallback() {
	  return "fell back.";
  }
}
