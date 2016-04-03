/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1_15;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author csx
 */
public class NewMain {

    public static void main(String args[]) {
        Dretva d1 = new Dretva();
        Dretva d2 = new Dretva();
        Dretva d3 = new Dretva();

        d1.start();
        d2.start();
        d3.start();

        System.out.println("Kraj programa!");
    }
}

class Dretva extends Thread {

    public static int uk_broj = 0;
    public int r_broj;
    long spavanje;

    Dretva() {
        redniBroj();
    }

    private void redniBroj() {
        r_broj = uk_broj++;
    }

    public void start() {
        System.out.println("Start: " + new Integer(r_broj).toString() + this.getName());
        super.start();
    }

    public void interrupt() {
        System.out.println("Stop: " + new Integer(r_broj).toString());
        super.interrupt();
    }

    private void gen_spavanje() {
        spavanje = (long) (Math.random() * 10000);
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            //gen_spavanje();

            long poc = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "HH:mm:ss.SSS dd.MM.yyyy");
            System.out.println("Sada je " + sdf.format(new Date()));
            long trajanje = System.currentTimeMillis() - poc;
            try {
                sleep(5000 - trajanje);
            } catch (InterruptedException ex) {
                Logger.getLogger(Dretva.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Saljem na spavanje dretvu " + i + " dodatne informacije: " + this.getState() + this.getName());

        }

    }
}
