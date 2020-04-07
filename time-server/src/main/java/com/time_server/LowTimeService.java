
package com.time_server;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 
 */

public class LowTimeService implements TimeService {
    
    private Date date;
    private Thread dateUpdater;
    private long requestCount;
    private boolean stop;
    private final int dateUpdaterStep;
    private final long numberOfClients;

    //construct a TimeService with a starting date
    public LowTimeService(Date date, long numberOfClients, int dateUpdaterStep) {
        this.date = date;
        this.dateUpdaterStep = dateUpdaterStep;
        this.numberOfClients = numberOfClients;
        
        stop = false;
        dateUpdater = new Thread(() -> {
            long currentTime;
            long newTime;
            while (requestCount < numberOfClients) {
                currentTime = this.date.getTime();
                newTime = currentTime + this.dateUpdaterStep;
                this.date.setTime(newTime);
                System.out.println("clock tick");
                try {
                    dateUpdater.sleep(this.dateUpdaterStep);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LowTimeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            stop = true;
        });
        dateUpdater.start();
    }

    @Override
    public synchronized Date getDate() {
        requestCount++;
        System.out.println("request number " + requestCount);
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
