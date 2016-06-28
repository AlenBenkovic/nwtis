/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.server;

import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author abenkovic
 */
public class PozadinskoPreuzimanje extends Thread {

    private boolean dretvaRadi = true;
    private boolean dretvaPreuzima = true;
    private Konfiguracija serverConfig = null;
    private int intervalPreuzimanja;

    public PozadinskoPreuzimanje() {
        serverConfig = SlusacAplikacije.getServerConfig();
        intervalPreuzimanja = Integer.parseInt(serverConfig.dajPostavku("intervalPreuzimanja")) * 1000; //podatak je spremeljan u sekundama
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
                long pocetakRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve
                System.out.println("|Preuzimam podatke i spremam ih u bazu...");
                long krajRadaDretve = System.currentTimeMillis(); //biljezim kraj rada dretve
                long trajanjeRadaDretve = krajRadaDretve - pocetakRadaDretve;
                try {
                    if ((intervalPreuzimanja - trajanjeRadaDretve) > 0) {
                        sleep(intervalPreuzimanja - trajanjeRadaDretve);//odlazim na spavanje
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Pozadinska dretva se budi...");
                }
            } else {
                try {
                    sleep(intervalPreuzimanja);
                } catch (InterruptedException ex) {
                    System.out.println("Pozadinska dretva se budi...");
                }
            }
        }
        System.out.println("Pozadinska dretva zavrsava s radom.");

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
