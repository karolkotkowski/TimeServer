
package com.time_server.high_level;

import com.time_server.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 *
 * @author
 */
public class HighTimeServiceRequests implements TimeServiceRequests {
    private final int ThreadPoolSize = 10;

    private final long numberOfClients;
    private final Runnable client;
    private final ExecutorService executor;
    private final ThreadFactory factory;

    public HighTimeServiceRequests (TimeService timeService, long numberOfClients) {
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
