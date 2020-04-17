
package com.time_server;

import java.util.Date;

/**
 *
 * @author
 */
public interface TimeService {
    public Date getDate();
    public void setDate(Date date);
    public boolean isStopped();
    public void startDateUpdater();
}
