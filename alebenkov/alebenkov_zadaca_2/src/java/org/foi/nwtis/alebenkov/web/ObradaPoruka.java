/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.AuthenticationFailedException;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.ReadOnlyFolderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.servlet.ServletContext;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;

/**
 *
 * @author abenkovic
 */
public class ObradaPoruka extends Thread {

    private boolean dretvaRadi = true;
    private ServletContext kontekst;
    private String adresaServera;
    private String korisnickoIme;
    private String korisnickaLozinka;
    private int intervalSpavanja ;
    private String nazivIspravnogDirektorija;
    private String nazivNeispravnogDirektorija;
    private String nazivOstalogDirektorija;

    public ObradaPoruka(ServletContext kontekst) {
        this.kontekst = kontekst;
        Konfiguracija mailConfig = (Konfiguracija) kontekst.getAttribute("mailConfig");
        this.adresaServera = mailConfig.dajPostavku("adresaServera");
        this.korisnickoIme = mailConfig.dajPostavku("korisnickoIme");
        this.korisnickaLozinka = mailConfig.dajPostavku("korisnickaLozinka");
        this.intervalSpavanja = Integer.parseInt(mailConfig.dajPostavku("intervalDretve"));
        this.nazivIspravnogDirektorija = mailConfig.dajPostavku("nazivIspravnogDirektorija");
        this.nazivNeispravnogDirektorija = mailConfig.dajPostavku(" nazivNeispravnogDirektorija");
        this.nazivOstalogDirektorija = mailConfig.dajPostavku("nazivOstalogDirektorija");

    }

    @Override
    public void interrupt() {
        this.dretvaRadi = false;// ne znam iz kojeg razlika ali nakon interrupta dretva i dalje radi..
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        Session session = null;
        Store store = null;
        Folder folder = null;
        Message message = null;
        Message[] messages = null;
        Object messagecontentObject = null;
        String sender = null;
        String subject = null;
        Multipart multipart = null;
        Part part = null;
        String contentType = null;
        int sveukupnoPoruka = 0;

        while (dretvaRadi) {
            int ukupnoPoruka = 0;
            int ispravnoPoruka = 0;
            int neispravnoPoruka = 0;
            int ostaloPoruka = 0;
            int brojDodanihGrad = 0;
            int brojDodanihTvrtka = 0;
            int brojAzuriranihGrad = 0;
            int brojAzuziranihTvrtka = 0;

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy H:mm:ss");
                long pocetakRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve
                System.out.println("Obrada zapocela u: " + sdf.format(pocetakRadaDretve) );

                session = Session.getDefaultInstance(System.getProperties(), null); //procesiranje maila pocinje

                store = session.getStore("imap"); //spremam sesiju za pristup mailu

                store.connect(this.adresaServera, this.korisnickoIme, this.korisnickaLozinka);//spajam se na IMAP server

                folder = store.getDefaultFolder(); //uzimam default folder

                folder = folder.getFolder("inbox");

                folder.open(Folder.READ_WRITE); //citam postu u index folderu u RW modu

                messages = folder.getMessages();//uzimam poruke iz inboxa

                //Prolazim kroz sve poruke
                for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {
                    sveukupnoPoruka++;
                    ukupnoPoruka++;

                    message = messages[messageNumber]; //uzimam trenutnu poruku kroz koju prolazim

                    messagecontentObject = message.getContent();// Retrieve the message content

                    String vrstaPoruke = message.getContentType().toLowerCase();//stavljam u mala slova kako god da pise radi kasnije probjere
                    String naslovPoruke = message.getSubject();
                    String sadrzajPoruke = message.getContent().toString();

                    System.out.println("Dretva " + this.getId() + " | Poruka " + messageNumber + " | Vrsta poruke: " + vrstaPoruke + "\nNaslov: " + naslovPoruke + "\nSadrzaj: " + sadrzajPoruke);

                    if (vrstaPoruke.startsWith("text/plain")) {
                        Matcher mSadrzaj = provjeraSadrzaja(sadrzajPoruke.trim());

                        if ((naslovPoruke.equalsIgnoreCase("NWTiS poruka")) && (mSadrzaj != null)) {
                            System.out.println(this.getId() + " | Poruka " + messageNumber + " | ISPRAVNA");
                            ispravnoPoruka++;
                            if (mSadrzaj.group(3).equals("ADD")) {
                                //TODO napravi ADD
                                System.out.println("ADD operacija");
                                if (mSadrzaj.group(1).equals("GRAD")) {
                                    brojDodanihGrad++;
                                    System.out.println("GRAD ADD +");
                                } else {
                                    brojDodanihTvrtka++;
                                    System.out.println("TVRTKA ADD +");

                                }

                            } else {//ako nije ADD onda mora biti UPDATE
                                //TODO napravi UPDATE
                                System.out.println("UPDATE operacija");
                                if (mSadrzaj.group(1).equals("GRAD")) {
                                    brojAzuriranihGrad++;
                                    System.out.println("GRAD UPDATE +");
                                } else {
                                    brojAzuziranihTvrtka++;
                                    System.out.println("TVRTKA UPDATE +");
                                }

                            }
                            //premjestiPoruku(nazivIspravnogDirektorija, store, message, folder);

                        } else {
                            //premjestiPoruku(nazivNeispravnogDirektorija, store, message, folder);
                            neispravnoPoruka++;
                            System.out.println(this.getId() + " | Poruka " + messageNumber + " | NEISPRAVNA");
                        }

                    } else {
                        //premjestiPoruku(nazivOstalogDirektorija, store, message, folder);
                        ostaloPoruka++;
                        System.out.println(this.getId() + " | Poruka " + messageNumber + " | OSTALA");

                    }

                }

                // Close the folder
                folder.close(true);//sa ovim true govorim serveru da sve poruke koje su oznacene za brisanje i obrise

                // Close the message store
                store.close();
                
                long krajRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve
                long trajanjeRadaDretve = krajRadaDretve - pocetakRadaDretve;           
                System.out.println("Obrada zavrsila u: " + sdf.format(krajRadaDretve));
                sleep(intervalSpavanja-trajanjeRadaDretve);//odlazim na spavanje

            } catch (AuthenticationFailedException e) {
                //printData("Not able to process the mail reading.");
                e.printStackTrace();
            } catch (FolderClosedException e) {
                //printData("Not able to process the mail reading.");
                e.printStackTrace();
            } catch (FolderNotFoundException e) {
                //printData("Not able to process the mail reading.");
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                //printData("Not able to process the mail reading.");
                e.printStackTrace();
            } catch (ReadOnlyFolderException e) {
                //printData("Not able to process the mail reading.");
                e.printStackTrace();
            } catch (StoreClosedException e) {
                //printData("Not able to process the mail reading.");
                e.printStackTrace();
            } catch (Exception e) {
                //printData("Not able to process the mail reading.");
                e.printStackTrace();
            }
        }
    }

    private void premjestiPoruku(String nazivDirektorija, Store store, Message message, Folder folder) throws MessagingException {

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
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public Matcher provjeraSadrzaja(String p) {
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

}
