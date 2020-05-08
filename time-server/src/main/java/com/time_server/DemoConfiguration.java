
package com.time_server;

import com.time_server.low_level.*;
import com.time_server.high_level.*;

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
    private final static int TIME_CALIBRATOR_STEP = 100; //in milliseconds
    private final static long NUMBER_OF_CLIENTS = 100 * 1000;

    @Bean
    public TimeService timeService() {
    	//switch between LowTimeService and HighTimeService
        return new HighTimeService(new Date(), NUMBER_OF_CLIENTS, TIME_SERVICE_STEP);
    }

    @Bean
    public TimeCalibrator timeCalibrator() {
    	//switch between LowTimeCalibrator and HighTimeCalibrator
        return new HighTimeCalibrator(timeService(), TIME_CALIBRATOR_STEP);
    }

    @Bean
    public TimeServiceRequests timeServiceRequests() {
    	//switch between LowTimeServiceRequests and HighTimeServiceRequests
        return new HighTimeServiceRequests(timeService(), NUMBER_OF_CLIENTS);
    }
}
