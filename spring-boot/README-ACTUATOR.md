# Spring Boot Actuator Details

Spring Boot Actuator is a sub-project of Spring Boot that provides production-ready features to help monitor and manage your application. It offers several endpoints that expose operational information about your running application.

## Actuator Configuration

The actuator has been configured in both microservices:
- loan-service
- credit-risk-service

Configuration in `application.yml`:
```yaml
management:
  endpoints:
    web:
      exposure:
        include: '*'  # Exposes all endpoints
  endpoint:
    health:
      show-details: always  # Shows detailed health information
    shutdown:
      enabled: true  # Enables the shutdown endpoint
```

## Available Endpoints

All endpoints are available at the `/actuator` base path. For example: `http://localhost:9191/actuator/health`

| Endpoint ID | Description |
|-------------|-------------|
| `auditevents` | Exposes audit events information |
| `beans` | Displays a complete list of all Spring beans in your application |
| `caches` | Exposes available caches |
| `conditions` | Shows the conditions that were evaluated on configuration and auto-configuration classes |
| `configprops` | Displays a collated list of all `@ConfigurationProperties` |
| `env` | Exposes properties from Spring's `ConfigurableEnvironment` |
| `flyway` | Shows any Flyway database migrations that have been applied |
| `health` | Shows application health information |
| `httptrace` | Displays HTTP trace information |
| `info` | Displays arbitrary application info |
| `integrationgraph` | Shows the Spring Integration graph |
| `loggers` | Shows and modifies the configuration of loggers in the application |
| `liquibase` | Shows any Liquibase database migrations that have been applied |
| `metrics` | Shows metrics information for the current application |
| `mappings` | Displays a collated list of all `@RequestMapping` paths |
| `scheduledtasks` | Displays the scheduled tasks in your application |
| `sessions` | Allows retrieval and deletion of user sessions from a Spring Session-backed session store |
| `shutdown` | Lets the application be gracefully shutdown (disabled by default) |
| `startup` | Shows startup steps data collected by the ApplicationStartup |
| `threaddump` | Performs a thread dump |

## Usage Examples

### Health Check
```
GET /actuator/health
```
Returns the health status of the application and its dependencies.

### Application Information
```
GET /actuator/info
```
Returns arbitrary application information.

### Metrics
```
GET /actuator/metrics
```
Lists all available metric names.

```
GET /actuator/metrics/{metric.name}
```
Returns the value of a specific metric.

### Environment
```
GET /actuator/env
```
Returns the current environment properties.

### Beans
```
GET /actuator/beans
```
Returns a complete list of all Spring beans in the application.

### Thread Dump
```
GET /actuator/threaddump
```
Returns a thread dump from the application's JVM.

### Shutdown (Enabled)
```
POST /actuator/shutdown
```
Gracefully shuts down the application.

## Security Considerations

In production environments, it's recommended to:
1. Secure the actuator endpoints with Spring Security
2. Expose only necessary endpoints
3. Use HTTPS for all communications
4. Consider using a separate management port

## Custom Metrics

You can add custom metrics to your application using the `MeterRegistry`:

```java
@Component
public class MyMetrics {
    private final MeterRegistry meterRegistry;

    public MyMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        
        // Register a counter
        Counter.builder("my.custom.counter")
               .description("A custom counter")
               .register(meterRegistry);
               
        // Register a gauge
        Gauge.builder("my.custom.gauge", this, MyMetrics::getGaugeValue)
             .description("A custom gauge")
             .register(meterRegistry);
    }
    
    private double getGaugeValue() {
        // Return some value
        return 42.0;
    }
}
```

## Customizing Actuator

You can customize actuator endpoints by:
1. Creating custom endpoints with `@Endpoint`, `@ReadOperation`, `@WriteOperation`, and `@DeleteOperation`
2. Extending existing endpoints
3. Adding custom health indicators

## Monitoring with Prometheus and Grafana

Spring Boot Actuator can be integrated with Prometheus for metrics collection and Grafana for visualization:

1. Add the Prometheus dependency:
```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

2. Access Prometheus metrics at `/actuator/prometheus`
3. Configure Prometheus to scrape this endpoint
4. Set up Grafana dashboards to visualize the metrics