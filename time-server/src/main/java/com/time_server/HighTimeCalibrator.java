
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
 * @author tk
 */
public class HighTimeCalibrator implements TimeCalibrator {
    private final TimeService timeService;
    private boolean stop;
    private final int calibrationStep;
    private int calibrationCount;
    private final Runnable calibration;
    private final ScheduledExecutorService executor;
    private final Lock lock;

    public HighTimeCalibrator(TimeService timeService, int calibrationStep) {
        this.timeService = timeService;
        this.calibrationStep = calibrationStep;

        stop = false;
        calibration = new Thread(() -> {
            this.calibrate();
        });
        executor = Executors.newSingleThreadScheduledExecutor();
        lock = new ReentrantLock();
    }

    @Override
    public void calibrate() {
        lock.lock();
        try {
            timeService.setDate(new Date());
            stop = timeService.isStopped();
            calibrationCount++;
            System.out.println("calibration number " + calibrationCount);
            if (stop) {
                executor.shutdown();
            }
        } finally {
            lock.unlock();
        }

    }

    @Override
    public void start() {
        //set calibration to run periodically
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(calibration, 0, calibrationStep, TimeUnit.MILLISECONDS);
    }

}
