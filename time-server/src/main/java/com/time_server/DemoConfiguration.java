
package com.time_server;

import java.util.Date;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author 
 */

//configuration for constants and creating instances in demo
@Configuration
public class DemoConfiguration {
    
    private final static int TIME_SERVICE_STEP = 10; //in milliseconds
    private final static int TIME_CALIBRATOR_STEP = 100; //in miliseconds
    private final static long NUMBER_OF_CLIENTS = 100 * 1000;
    
    @Bean
    public TimeService timeService() {
        return new HighTimeService(new Date(), NUMBER_OF_CLIENTS, TIME_SERVICE_STEP);
    }
    
    @Bean
    public TimeCalibrator timeCalibrator() {
        return new HighTimeCalibrator(timeService(), TIME_CALIBRATOR_STEP);
    }
    
    @Bean
    public TimeServiceClients timeServiceClients() {
        return new HighTimeServiceClients(timeService(), NUMBER_OF_CLIENTS);
    }
}
