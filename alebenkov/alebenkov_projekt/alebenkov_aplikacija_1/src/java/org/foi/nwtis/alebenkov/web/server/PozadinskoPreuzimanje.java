/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.server;

/**
 *
 * @author abenkovic
 */
public class PozadinskoPreuzimanje extends Thread {

    private boolean dretvaRadi = true;
    private boolean dretvaPreuzima = true;

    public PozadinskoPreuzimanje() {

    }

    @Override
    public void interrupt() {
        this.dretvaRadi = false;
        System.out.println("Gasim pozadinsku dretvu za preuzimanje.");
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        while (dretvaRadi) {
            if (dretvaPreuzima) {
                System.out.println("|Preuzimam podatke i spremam ih u bazu...");
                try {
                    sleep(10000);
                } catch (InterruptedException ex) {
                    System.out.println("Pozadinksa dretva se budi...");
                }
            } else {
                try {
                    sleep(10000);
                } catch (InterruptedException ex) {
                    System.out.println("Pozadinksa dretva se budi...");
                }
            }
        }
        System.out.println("Pozadinksa dretva zavrsava s radom.");

    }

    public boolean isDretvaRadi() {
        return dretvaRadi;
    }

    public void setDretvaRadi(boolean dretvaRadi) {
        this.dretvaRadi = dretvaRadi;
    }

    public boolean isDretvaPreuzima() {
        return dretvaPreuzima;
    }

    public void setDretvaPreuzima(boolean dretvaPreuzima) {
        this.dretvaPreuzima = dretvaPreuzima;
    }

}
