package com.time_server;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author
 */
public class HighTimeService implements TimeService {
    private Date date;
    private final int updaterStep;
    private boolean stop;
    private long requestCount;
    private final Runnable threadUpdater;
    private final ScheduledExecutorService executor;
    private final Lock lock;
    private final long numberOfClients;

    //construct a TimeService with a starting date
    public HighTimeService(Date date, long numberOfClients, int updaterStep) {
        this.date = date;
        this.updaterStep = updaterStep;
        this.numberOfClients = numberOfClients;
        
        stop = false;
        lock = new ReentrantLock();
        executor = Executors.newSingleThreadScheduledExecutor();
        threadUpdater = new Thread(() -> {
            if (!stop) {
                long currentTime;
                long newTime;
                currentTime = this.date.getTime();
                newTime = currentTime + this.updaterStep;
                this.date.setTime(newTime);
                System.out.println("clock tick");
            } else {
                executor.shutdown();
            }
        });
        
        //set threadUpdater to run periodically
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(threadUpdater, updaterStep, updaterStep, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public Date getDate() {
        lock.lock();
        try {
            requestCount++;
            System.out.println("request number " + requestCount);
            if (numberOfClients <= requestCount) {
                stop = true;
            }
        } finally {
            lock.unlock();
        }
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean isStopped() {
        return stop;
    }
    
}
