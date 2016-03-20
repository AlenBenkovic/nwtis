/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.alebenkov.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author alen
 */
public class KlijentSustava {

    protected Konfiguracija konfig;
    protected String parametri;
    protected Matcher mParametri;

    public KlijentSustava(String parametri) throws Exception {
        this.parametri = parametri;
        this.mParametri = provjeraParametara(parametri);
        if (this.mParametri == null) {
            throw new Exception("Parametri usera ne odgovaraju!");
        }
    }

    public Matcher provjeraParametara(String p) {
        String sintaksa = "^-user -s ([^\\s]+) -port (\\d{4}) -u ([^\\s]+) -konf +([^\\s]+.(xml|txt))( +-cekaj (\\d{1}))?( +-multi)?( +-ponavljaj (\\d{1}))?$";

        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(p);
        boolean status = m.matches();
        if (status) {
            return m;
        } else {
            System.out.println("Ne odgovara!");
            return null;
        }
    }

    public void pokreniKlijenta() throws Exception {
        System.out.println("Pokrecem klijenta...");
        System.out.println("Broj parametara: " + mParametri.groupCount());
        String datoteka = this.mParametri.group(4);
        int brojDretvi=1;
        int razmakDretvi=0;

        File dat = new File(datoteka);
        if (!dat.exists()) {
            System.out.println("Datoteka konfiguracije ne postoji.");
            return;
        }

        Konfiguracija konfig = null;
        try {
            konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
            brojDretvi = Integer.parseInt(konfig.dajPostavku("brojDretvi"));
            System.out.println("Broj dretvi je: " + brojDretvi);
            razmakDretvi = Integer.parseInt(konfig.dajPostavku("razmakDretvi"));
            System.out.println("Razmak dretvi je: " + razmakDretvi);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
        }

        String server = this.mParametri.group(1);
        String korisnik = this.mParametri.group(3);
        int cekaj = 0;
        int ukupniBrojCiklusa = 1;
        int port = Integer.parseInt(this.mParametri.group(2));
        if (port < 8000 || port > 9999) {
            throw new Exception("Port mora biti u rasponu od 8000 do 9999!");
        }

        if (this.mParametri.group(7) != null) {
            cekaj = Integer.parseInt(this.mParametri.group(7));
            System.out.println("Cekanje je: " + cekaj);
        }
        if (this.mParametri.group(10) != null) {
            ukupniBrojCiklusa = Integer.parseInt(this.mParametri.group(10));
            System.out.println("Ponavljanje je: " + ukupniBrojCiklusa);
        }

        System.out.println("Spajam se na " + server + ":" + port);
        System.out.println("Korisnicka datoteka je: " + datoteka);
        System.out.println("Korisnik je: " + korisnik);

        //TODO traži se više dretvi koje se mogu izvršavati
        System.out.println("Saljem novi zahtjev prema serveru...");
        if (this.mParametri.group(8) != null) {
            System.out.println("Trazio si multi dretve...");
            int nasumicniBroj = (int) ((Math.random() * brojDretvi) + 1);
            int nasumicniRazmakDretvi = (int) ((Math.random() * razmakDretvi) + 1);
            System.out.println("Kreiram " + nasumicniBroj + " dretvi, s razmakom " + nasumicniRazmakDretvi);
            for (int i = 0; i < nasumicniBroj; i++) {
                System.out.println("Kreiram " + i);
                SlanjeZahtjeva sz = new SlanjeZahtjeva();
                sz.setServer(server);
                sz.setPort(port);
                sz.setBrojCiklusa(ukupniBrojCiklusa);
                sz.setCekaj(cekaj);
                sz.setRazmakDretvi(nasumicniRazmakDretvi*(i*1000));
                sz.start();
            }

        } else {
            SlanjeZahtjeva sz = new SlanjeZahtjeva();
            sz.setServer(server);
            sz.setPort(port);
            sz.setBrojCiklusa(ukupniBrojCiklusa);
            sz.setCekaj(cekaj);
            sz.start();
        }

    }
}
