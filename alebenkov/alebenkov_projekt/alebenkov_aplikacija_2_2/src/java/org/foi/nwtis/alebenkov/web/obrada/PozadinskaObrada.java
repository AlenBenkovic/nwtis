/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.obrada;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Session;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author abenkovic
 */
public class PozadinskaObrada extends Thread {

    private boolean dretvaRadi = true;
    private Konfiguracija mailConfig = null;
    private int intervalObrade;

    private String adresaServera;
    private String korisnickoIme;
    private String korisnickaLozinka;

    private String nazivIspravnogDirektorija;
    private String nazivNeispravnogDirektorija;
    private String nazivOstalogDirektorija;

    private String naslovPoruke;

    private String mailPosluzitelj;

    public PozadinskaObrada() {
        mailConfig = SlusacAplikacije.getServerConfig();
        intervalObrade = Integer.parseInt(mailConfig.dajPostavku("intervalObrade")) * 1000; //podatak je spremeljan u sekundama

        this.adresaServera = mailConfig.dajPostavku("adresaServera");
        this.korisnickoIme = mailConfig.dajPostavku("korisnickoIme");
        this.korisnickaLozinka = mailConfig.dajPostavku("korisnickaLozinka");

        this.nazivIspravnogDirektorija = mailConfig.dajPostavku("nazivIspravnogDirektorija");
        this.nazivNeispravnogDirektorija = mailConfig.dajPostavku("nazivNeispravnogDirektorija");
        this.nazivOstalogDirektorija = mailConfig.dajPostavku("nazivOstalogDirektorija");

        this.naslovPoruke = mailConfig.dajPostavku("naslovPoruke");

        this.mailPosluzitelj = mailConfig.dajPostavku("mailPosluzitelj");

    }

    @Override
    public void interrupt() {
        this.dretvaRadi = false;
        System.out.println("2 | Gasim pozadinsku dretvu za preuzimanje.");
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        Session session = null;
        Store store = null;
        Folder folder = null;
        Message message = null;
        Message[] messages = null;
        int redniBrojPoruke = 1;

        while (dretvaRadi) {
            int ukupnoPoruka = 0;
            int uspjesnePoruke = 0; //poruke za koje je korisnik uspjesno odobren
            int neuspjesnePoruke = 0; //poruke za koje korisnik postoji ali je vec odobren ili korisnik ne postoji
            int neispravnePoruke = 0; //sve ostale poruke koje ne odgovaraju kriterijima

            try {
                long pocetakRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve
                System.out.println("2 | Obradjujem nove mail poruke...");
                //POCETAK GLAVNE LOGIKE
                session = Session.getDefaultInstance(System.getProperties(), null); //procesiranje maila pocinje
                store = session.getStore("imap"); //spremam sesiju za pristup mailu
                store.connect(this.adresaServera, this.korisnickoIme, this.korisnickaLozinka);//spajam se na IMAP server
                folder = store.getDefaultFolder(); //uzimam default folder
                folder = folder.getFolder("inbox");
                folder.open(Folder.READ_WRITE); //citam postu u index folderu u RW modu
                messages = folder.getMessages();//uzimam poruke iz inboxa

                //Prolazim kroz sve poruke
                for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {
                    ukupnoPoruka++;
                    message = messages[messageNumber]; //uzimam trenutnu poruku kroz koju prolazim
                    String vrstaPoruke = message.getContentType().toLowerCase();//stavljam u mala slova kako god da pise radi kasnije probjere
                    String naslovPoruke = message.getSubject();
                    String sadrzajPoruke = message.getContent().toString();
                    String naredba = (String) sadrzajPoruke.subSequence(0, sadrzajPoruke.indexOf("\n"));
                    System.out.println("|| " + this.getName() + " | Poruka No." + messageNumber + " | Vrsta poruke: " + vrstaPoruke + "\nNaslov: " + naslovPoruke + "\nNaredba: " + naredba);
                    Matcher mSadrzaj = provjeraNaredbe(naredba.trim());
                    
                    if (vrstaPoruke.startsWith("text/plain") && naslovPoruke.equalsIgnoreCase(this.naslovPoruke) && mSadrzaj != null) {
                        
                        //TODO provjeri postoji li korisnik u bazi
                        
                    } else {
                        premjestiPoruku(nazivNeispravnogDirektorija, store, message, folder);
                        neispravnePoruke++;
                        System.out.println(this.getName() + " | Poruka No." + messageNumber + " | NEISPRAVNA");
                    }
                }

                // Close the folder
                folder.close(true);//sa ovim true govorim serveru da sve poruke koje su oznacene za brisanje i obrise

                // Close the message store
                store.close();

                if (ukupnoPoruka == 0) {
                    System.out.println("2 | Nema novih poruka.");
                }

                //KRAJ GLAVNE LOGIKE
                long krajRadaDretve = System.currentTimeMillis(); //biljezim kraj rada dretve
                long trajanjeRadaDretve = krajRadaDretve - pocetakRadaDretve;

                if ((intervalObrade - trajanjeRadaDretve) > 0) {
                    sleep(intervalObrade - trajanjeRadaDretve);//odlazim na spavanje
                }

            } catch (NoSuchProviderException ex) {
                Logger.getLogger(PozadinskaObrada.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                System.out.println("2 | Pozadinska dretva se budi...");
            } catch (MessagingException ex) {
                Logger.getLogger(PozadinskaObrada.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PozadinskaObrada.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        System.out.println(
                "2 | Pozadinska dretva zavrsava s radom.");

    }

    private void premjestiPoruku(String nazivDirektorija, Store store, Message message, Folder folder) throws MessagingException {
        try {
            Folder noviFolder = store.getFolder(nazivDirektorija);
            if (!noviFolder.exists()) {
                noviFolder.create(Folder.HOLDS_MESSAGES); //kreiram novi folder ako ne postoji
            }
            noviFolder.open(Folder.READ_WRITE);
            Message[] zaKopiranje = new Message[1];//kreiram polje poruka
            zaKopiranje[0] = message; //u polje poruka spremma trenutnu poruku
            folder.copyMessages(zaKopiranje, noviFolder); //kopiram trenutnu poruku u novi folder
            noviFolder.close(false);//zatvaram novi folder
            message.setFlag(Flags.Flag.DELETED, true); //oznacavam trenutnu poruku da je spremna za brisanje

        } catch (Exception e) {
            System.out.println("2 | Greska prilikom kreiranja foldera " + e.getMessage() + e.toString());
        }

    }

    public Matcher provjeraNaredbe(String p) {
        String regex = "(GRAD|TVRTKA) (\\w+); (ADD|UPDATE);"; //group 1 grad ili tvrtka, group 2 naziv, group 3 operacija
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(p);
        boolean status = m.matches();
        if (status) {
            return m;
        } else {
            return null;
        }
    }

    public boolean isDretvaRadi() {
        return dretvaRadi;
    }

    public void setDretvaRadi(boolean dretvaRadi) {
        this.dretvaRadi = dretvaRadi;
    }

}
