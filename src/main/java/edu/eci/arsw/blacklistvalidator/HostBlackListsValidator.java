package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import edu.eci.arsw.threads.SearchThread;
import java.util.ArrayList;
import java.util.List;

public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT = 5;
    private final HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();

    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipAddress suspicious host's IP address.
     * @param N number of threads to use.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipAddress, int N) {
        int registeredServers = skds.getRegisteredServersCount();
        int partitionSize = registeredServers / N;

        List<SearchThread> threads = new ArrayList<>();

        // Crear y lanzar los hilos
        for (int i = 0; i < N; i++) {
            int start = i * partitionSize;
            int end = (i == N - 1) ? registeredServers : (i + 1) * partitionSize;
            SearchThread t = new SearchThread(start, end, ipAddress, skds);
            threads.add(t);
            t.start();
        }

        // Esperar que todos terminen
        for (SearchThread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Recolectar resultados
        List<Integer> blackListOccurrences = new ArrayList<>();
        for (SearchThread t : threads) {
            blackListOccurrences.addAll(t.getOccurrences());
        }

        // Reportar
        if (blackListOccurrences.size() >= BLACK_LIST_ALARM_COUNT) {
            skds.reportAsNotTrustworthy(ipAddress);
        } else {
            skds.reportAsTrustworthy(ipAddress);
        }

        System.out.printf("Checked %d servers with %d threads.%n", registeredServers, N);
        return blackListOccurrences;
    }
}
