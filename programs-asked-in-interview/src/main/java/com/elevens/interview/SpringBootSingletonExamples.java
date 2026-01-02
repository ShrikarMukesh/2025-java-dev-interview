package com.elevens.interview;

// In a real Spring Boot application, you would import these:
// import org.springframework.stereotype.Service;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Bean;

/**
 * Real World Example 1: Stateless Service Class
 * 
 * Why Singleton?
 * - Service classes typically contain business logic but no state (fields that change per user).
 * - Creating a new instance for every request would be wasteful (memory/GC overhead).
 * - Spring creates ONE instance of this class and injects it wherever needed.
 */
// @Service
class EmailService {
    
    // This method is thread-safe because it relies on local variables (stack memory).
    public void sendWelcomeEmail(String email) {
        System.out.println("Connecting to SMTP server...");
        System.out.println("Sending welcome email to: " + email);
        // Logic to send email
    }
}

/**
 * Real World Example 2: Configuration Class
 * 
 * Why Singleton?
 * - Configuration classes hold global application setup (DB connections, security settings).
 * - You only want ONE source of truth for how the app is configured.
 * - Spring ensures that even if you call a @Bean method multiple times, it returns the SAME instance.
 */
// @Configuration
class AppConfig {
    
    // @Bean
    public Object databaseConnection() {
        // In Spring, this logic runs only once. Subsequent calls return the cached instance.
        System.out.println("Establishing Database Connection Pool...");
        return new Object(); // Represents a DataSource
    }
}

/**
 * Simulation of how Spring Container handles these Singletons
 */
public class SpringBootSingletonExamples {
    
    // Simulating the Spring IOC Container cache
    private static EmailService emailServiceInstance;
    private static AppConfig appConfigInstance;

    public static void main(String[] args) {
        System.out.println("--- Spring Boot Container Startup ---");
        
        // 1. Service Example
        // Spring creates this once at startup
        EmailService service1 = getEmailServiceBean();
        EmailService service2 = getEmailServiceBean();
        
        System.out.println("Service is Singleton: " + (service1 == service2));
        service1.sendWelcomeEmail("user@example.com");

        // 2. Configuration Example
        // Spring creates this once
        AppConfig config = getAppConfigBean();
        config.databaseConnection(); // Returns the singleton DataSource
    }

    // Simulating Spring's Dependency Injection / Bean retrieval
    private static EmailService getEmailServiceBean() {
        if (emailServiceInstance == null) {
            emailServiceInstance = new EmailService();
        }
        return emailServiceInstance;
    }

    private static AppConfig getAppConfigBean() {
        if (appConfigInstance == null) {
            appConfigInstance = new AppConfig();
        }
        return appConfigInstance;
    }
}
