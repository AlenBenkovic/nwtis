/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.server;

import java.util.ArrayList;
import java.util.List;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.rest.klijenti.OWMKlijent;
import org.foi.nwtis.alebenkov.web.podaci.Adresa;
import org.foi.nwtis.alebenkov.web.podaci.MeteoPodaci;
import org.foi.nwtis.alebenkov.web.podaci.MeteoPrognoza;
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
    private List<Adresa> adrese;
    private String APPID;

    public PozadinskoPreuzimanje() {
        serverConfig = SlusacAplikacije.getServerConfig();
        intervalPreuzimanja = Integer.parseInt(serverConfig.dajPostavku("intervalPreuzimanja")) * 1000; //podatak je spremeljan u sekundama
        this.adrese = new ArrayList<>();
        this.APPID = serverConfig.dajPostavku("APPID");
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

                DBOps dbOps = new DBOps();
                this.adrese = dbOps.ucitajAdrese();

                for (int i = 0; i < adrese.size(); i++) {
                    OWMKlijent owmk = new OWMKlijent(APPID);

                    MeteoPrognoza[] mpp = owmk.getWeatherForecast(adrese.get(i).getGeoloc().getLatitude(), adrese.get(i).getGeoloc().getLongitude());
                    MeteoPodaci mp = owmk.getRealTimeWeather(adrese.get(i).getGeoloc().getLatitude(), adrese.get(i).getGeoloc().getLongitude());
                    if (dbOps.spremiMeteo(adrese.get(i), mp)) {
                        System.out.println("Meteo podatak za " + adrese.get(i).getAdresa() + " spremljen u bazu.");
                    } else {
                        System.out.println("Huston, we have a problem.");
                    }
                    for (int j = 0; j < mpp.length; j++) {
                        MeteoPodaci mp2 = mpp[j].getPrognoza();
                        if (dbOps.spremiMeteoPrognozu(adrese.get(i), mp2, mpp[j].getAdresa())) {
                            //System.out.println("Meteo prognoza za " + mp2.getName() + " " + adrese.get(i).getAdresa() + " uspjesno spremljena.");
                        } else {
                            System.out.println("Huston, we have one more problem.");
                        }
                        
                    }
                    System.out.println("Meteo prognoza za " + adrese.get(i).getAdresa() + " spremljena u bazu.");

                }

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
