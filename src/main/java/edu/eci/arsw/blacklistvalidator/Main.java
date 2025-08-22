/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;

/**
 *
 * @author hcadavid
 */
public class Main {
    public static void main(String[] args) {
        HostBlackListsValidator validator = new HostBlackListsValidator();

        // Números de hilos a probar
        int[] threadConfigs = {1, 
                               Runtime.getRuntime().availableProcessors(),
                               Runtime.getRuntime().availableProcessors() * 2,
                               50, 
                               100};

        for (int n : threadConfigs) {
            long start = System.currentTimeMillis();

            List<Integer> blackListOcurrences = validator.checkHost("202.24.34.55", n);

            long end = System.currentTimeMillis();
            long duration = end - start;

            System.out.println("Hilos: " + n 
                + " | Ocurrencias: " + blackListOcurrences.size() 
                + " | Tiempo: " + duration + " ms");
        }
    }
}
