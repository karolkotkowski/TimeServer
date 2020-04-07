
package com.time_server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author 
 */
public class LowTimeServiceClients implements TimeServiceClients {
    private final List<Thread> clients;
    private final TimeService timeService;
    private final long numberOfClients;

    public LowTimeServiceClients(TimeService timeService, long numberOfClients) {
        this.timeService = timeService;
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
