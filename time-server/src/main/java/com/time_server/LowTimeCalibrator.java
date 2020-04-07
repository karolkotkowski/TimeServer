
package com.time_server;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 */
public class LowTimeCalibrator extends Thread implements TimeCalibrator {
    private final TimeService timeService;
    private boolean stop;
    private int calibrationCount;
    private final int calibrationStep;

    public LowTimeCalibrator(TimeService timeService, int calibrationStep) {
        this.timeService = timeService;
        this.calibrationStep = calibrationStep;
        stop = false;
    }
    
    @Override
    public synchronized void calibrate() {
        timeService.setDate(new Date());
        stop = timeService.isStopped();
        calibrationCount++;
        System.out.println("calibration number " + calibrationCount);
    }
    

    @Override
    public void run() {
        while (!stop) {
            this.calibrate();
            try {
                this.sleep(calibrationStep);
            } catch (InterruptedException ex) {
                Logger.getLogger(LowTimeCalibrator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
