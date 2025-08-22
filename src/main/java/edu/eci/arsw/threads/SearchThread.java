package edu.eci.arsw.threads;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;

public class SearchThread extends Thread {
    private final int startIndex;
    private final int endIndex;
    private final String ipAddress;
    private final HostBlacklistsDataSourceFacade facade;
    private final List<Integer> blackListOccurrences = new LinkedList<>();

    public SearchThread(int startIndex, int endIndex, String ipAddress, HostBlacklistsDataSourceFacade facade) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.ipAddress = ipAddress;
        this.facade = facade;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            if (facade.isInBlackListServer(i, ipAddress)) { 
                blackListOccurrences.add(i);
            }
        }
    }

    public List<Integer> getOccurrences() {
        return blackListOccurrences;
    }
}
