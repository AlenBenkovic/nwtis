/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.obrada;

import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author abenkovic
 */
public class PozadinskaObrada extends Thread {

    private boolean dretvaRadi = true;

    private Konfiguracija serverConfig = null;
    private int intervalPreuzimanja;

    public PozadinskaObrada() {
        serverConfig = SlusacAplikacije.getServerConfig();
        intervalPreuzimanja = Integer.parseInt(serverConfig.dajPostavku("intervalPreuzimanja")) * 1000; //podatak je spremeljan u sekundama

    }

    @Override
    public void interrupt() {
        this.dretvaRadi = false;
        System.out.println("2 | Gasim pozadinsku dretvu za preuzimanje.");
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        while (dretvaRadi) {
            long pocetakRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve
            System.out.println("2 | Obradjujem nove mail poruke...");
            //TODO GLAVNA LOGIKA
            long krajRadaDretve = System.currentTimeMillis(); //biljezim kraj rada dretve
            long trajanjeRadaDretve = krajRadaDretve - pocetakRadaDretve;
            try {
                if ((intervalPreuzimanja - trajanjeRadaDretve) > 0) {
                    sleep(intervalPreuzimanja - trajanjeRadaDretve);//odlazim na spavanje
                }
            } catch (InterruptedException ex) {
                System.out.println("2 | Pozadinska dretva se budi...");
            }
            
        }
        System.out.println("2 | Pozadinska dretva zavrsava s radom.");

    }

    public boolean isDretvaRadi() {
        return dretvaRadi;
    }

    public void setDretvaRadi(boolean dretvaRadi) {
        this.dretvaRadi = dretvaRadi;
    }

}
