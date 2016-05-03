/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;

/**
 *
 * @author abenkovic
 */
public class SerijalizatorEvidencije extends Thread {

    private boolean zaustavi = false;
    private Konfiguracija konfig;
    private Evidencija evid = null;

    public SerijalizatorEvidencije(Konfiguracija konfig, Evidencija evid) {
        this.konfig = konfig;
        this.evid = evid;
    }
    
    public SerijalizatorEvidencije(Konfiguracija konfig) {
        this.konfig = konfig;
    }

    @Override
    public void run() {
        super.run();

        int intervalSerijalizacije = Integer.parseInt(konfig.dajPostavku("intervalSerijalizacije"));

        while (!zaustavi) {
            long poc = System.currentTimeMillis();
            if(this.evid != null){
                spremiEvidenciju();
            }else {
                System.out.println("ERROR| Evidencija jos nije kreirana..");
            }
            long trajanje = System.currentTimeMillis() - poc;
            try {
                sleep(intervalSerijalizacije * 1000 - trajanje); // x1000 jer je postavka spremljena u sekundama
            } catch (InterruptedException ex) {
                System.out.println("ERROR | " + ex.getMessage());
            }
        }

    }

    public void spremiEvidenciju() {

        try {
            String dat = konfig.dajPostavku("evidDatoteka");
            FileOutputStream fileOut = new FileOutputStream(dat);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(evid);
            out.close();
            fileOut.close();
            System.out.println("SERVER | Serijalizirana evidencija spremljena u " + dat);

        } catch (IOException ex){
            System.out.println("ERRPR | " + ex.getMessage());
        }
    }

    public Evidencija ucitajEvidenciju() {
        Evidencija e = null;
        try {
            String dat = konfig.dajPostavku("evidDatoteka");
            FileInputStream fileIn = new FileInputStream(dat);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Evidencija) in.readObject();
            in.close();
            fileIn.close();

            System.out.println("SERVER | Deserijalizirana evidencija ucitana.");

        } catch (ClassNotFoundException c ){
            System.out.println("ERROR | " + c.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerijalizatorEvidencije.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SerijalizatorEvidencije.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;

    }

    public void spremiTrenutnoStanje(Evidencija evid) {
        this.evid = evid;
    }

}
