
package com.time_server.low_level;

import com.time_server.*;

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

    //construct a TimeService with a starting date
    public LowTimeService(Date date, long numberOfClients, int dateUpdaterStep) {
        this.date = date;
        this.dateUpdaterStep = dateUpdaterStep;

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
                    Thread.sleep(this.dateUpdaterStep);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LowTimeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            stop = true;
        });
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

	@Override
	public void startDateUpdater() {
		dateUpdater.start();

	}


}
