
package com.time_server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author
 */
public class TimeServerDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //retrieve context from DemoConfiguration
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfiguration.class);

        TimeService timeService = context.getBean("timeService", TimeService.class);
        timeService.startDateUpdater();

        TimeCalibrator timeCalibrator = context.getBean("timeCalibrator", TimeCalibrator.class);
        timeCalibrator.start();

        TimeServiceClients clients = context.getBean("timeServiceClients", TimeServiceClients.class);
        clients.startClients();


    }


}
