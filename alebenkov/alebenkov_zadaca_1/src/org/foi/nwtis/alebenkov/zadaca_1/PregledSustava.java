/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.alebenkov.konfiguracije.NemaKonfiguracije;

/**
 * Klasa za prikaz serijaliziranih podataka evidencije
 *
 * @author Alen Benkovic
 */
public class PregledSustava {

    private final String datoteka;
    private Evidencija evid;
    private Konfiguracija konfig;
    protected String parametri;
    protected Matcher mParametri;

    public PregledSustava(String parametri) throws Exception {
        this.parametri = parametri;
        mParametri = provjeraParametara(parametri);
        if (mParametri == null) {
            throw new Exception("Parametri servera ne odgovaraju");
        }
        this.datoteka = mParametri.group(1);
    }

    public void pokreniPregledSustava() {
        this.evid = ucitajEvidenciju(datoteka);
        System.out.println("--------------------------------------------------\n|\t PRIKAZ EVIDENCIJE RADA SERVERA \t | \n--------------------------------------------------");
        this.prikazServerZapisa();
        System.out.println("------------------------------------------\n|\t PRIKAZ EVIDENCIJE IGRE \t |\n------------------------------------------");
        this.prikazZapisa();

    }

    /**
     * Ucitavanje evidencije iz datoteke
     *
     * @return evidenciju iz datoteke (Evidencija)
     */
    public Evidencija ucitajEvidenciju(String datoteka) {
        Evidencija e = null;
        try {
            String dat = datoteka;
            FileInputStream fileIn = new FileInputStream(dat);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Evidencija) in.readObject();
            in.close();
            fileIn.close();

            System.out.println("SERVER | Deserijalizirana evidencija ucitana.");

        } catch (ClassNotFoundException c) {
            System.out.println("ERROR | " + c.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerijalizatorEvidencije.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SerijalizatorEvidencije.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;

    }

    public void prikazServerZapisa() {
        ArrayList<Evidencija.EvidencijaServeraZapis> evidencija = evid.getEvidencijaServera();
        for (int j = 0; j < evidencija.size(); j++) {
            Evidencija.EvidencijaServeraZapis ev = evidencija.get(j);
            System.out.println("| Datum: " + ev.getVrijeme() + " | " + ev.getZapis());
        }
    }

    public void prikazZapisa() {
        ArrayList<Evidencija.EvidencijaZapis> evidencija = evid.dohvatiZapise();
        for (int j = 0; j < evidencija.size(); j++) {
            Evidencija.EvidencijaZapis ev = evidencija.get(j);
            System.out.print("-------------------------------------\n"
                    + "| Vrijeme: " + ev.getVrijeme() + "| Ime igraca: " + ev.getImeIgraca()
                    + "\n| Gadjana lokacija: " + ev.getX() + "," + ev.getY() + " | Status: " + ev.getBiljeska() + ""
                    + "\n -------------------------------------\n");
            int[][] poljeBrodova = ev.getPoljeBrodova();
            for (int i = 0; i < poljeBrodova.length; i++) {
                for (int k = 0; k < poljeBrodova[0].length; k++) {
                    if (i == ev.getX() - 1 && k == ev.getY() - 1) {
                        System.out.print("X\t");
                    } else {
                        System.out.print(poljeBrodova[i][k] + "\t");
                    }

                }
                System.out.print("\n");
            }
        }
    }

    public Matcher provjeraParametara(String p) {

        String sintaksa = "^-show -s +([^\\s]+)?$";
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(p);
        boolean status = m.matches();
        if (status) {
            return m;
        } else {
            System.out.println("Parametri ne odgovaraju!");
            return null;
        }
    }

}
