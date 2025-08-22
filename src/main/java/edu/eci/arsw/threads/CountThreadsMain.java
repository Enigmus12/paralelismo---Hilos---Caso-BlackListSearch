/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */

public class CountThreadsMain {

    public static void main(String[] args) {
        
        // Creamos los 3 hilos con los intervalos dados
        CountThread t1 = new CountThread(0, 99);
        CountThread t2 = new CountThread(99, 199);
        CountThread t3 = new CountThread(200, 299);

        System.out.println("=== Ejecutando con start() ===");
        t1.start();
        t2.start();
        t3.start();
        
        // Si aquí cambiamos start() por run(),
        // los hilos se ejecutan secuencialmente, NO en paralelo.
    }
}

