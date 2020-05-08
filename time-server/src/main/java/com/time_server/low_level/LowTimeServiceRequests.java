
package com.time_server.low_level;

import com.time_server.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author
 */
public class LowTimeServiceRequests implements TimeServiceRequests {
    private final List<Thread> clients;
    private final long numberOfClients;

    public LowTimeServiceRequests(TimeService timeService, long numberOfClients) {
        this.numberOfClients = numberOfClients;

        //create threads requesting for current time
        clients = new ArrayList<Thread>((int) this.numberOfClients);
        for (int i = 0; i < numberOfClients; i++) {
            clients.add(new Thread(() -> {
                timeService.getDate();
            }));
        }
    }

    @Override
    public void startClients() {
        Iterator<Thread> iterator = clients.iterator();
        while (iterator.hasNext()) {
            iterator.next().start();
        }
    }


}
