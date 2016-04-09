/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1_15;

/**
 *
 * @author csx
 */
public class glavnaTest {

    public static void main(String args[]) {
        Dretve d1 = new Dretve();
        

        d1.start();
        

        System.out.println("Kraj programa!");
    }
}

class Dretve extends Thread {

    

    Dretve() {
         System.out.println("Kreirao dretvu!" + this.getName());
    }

    

    public void start() {
        System.out.println("Start: " +  this.getName());
        super.start();
    }

    public void interrupt() {
        System.out.println("Stop: " + this.getName());
        super.interrupt();
    }

    

    public void run() {
         System.out.println("Pokrecem dretvu!" + this.getName());
    }
}
