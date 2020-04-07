
package com.time_server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 *
 * @author
 */
public class HighTimeServiceClients implements TimeServiceClients {
    private final int ThreadPoolSize = 10;
    
    private final long numberOfClients;
    private final TimeService timeService;
    private final Runnable client;
    private final ExecutorService executor;
    private final ThreadFactory factory;
    
    public HighTimeServiceClients (TimeService timeService, long numberOfClients) {
        this.timeService = timeService;
        this.numberOfClients = numberOfClients;
        
        client = new Thread(() -> {
           timeService.getDate();
        });
        
        factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable);
            }
        };
        
        //create a pool
        executor = Executors.newFixedThreadPool(ThreadPoolSize, factory);
    }
    
    @Override
    public void startClients() {
        //create new threads and assign them to the pool
        for (int i = 0; i < numberOfClients; i++) {
            executor.execute(factory.newThread(client));
        }
        
        executor.shutdown();
    }
    
}
