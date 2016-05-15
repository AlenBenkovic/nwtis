/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web;

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
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;

/**
 *
 * @author abenkovic
 */
public class ObradaPoruka extends Thread {

    private ServletContext kontekst;
    //TODO preuzimi podatke iz konfiguracijske datoteke
    private String adresaServera = "nwtis.nastava.foi.hr";
    private String korisnickoIme = "servis@nwtis.nastava.foi.hr"; //ovo bi mozda trebalo iz konfiguracijske datoteke povuci
    private String korisnickaLozinka = "12346";//ovo bi mozda trebalo iz konfiguracijske datoteke povuci
    private long intervalSpavanja = 60000; //1 min
    private String nazivIspravnogDirektorija = "Ispravne_poruke"; //TODO uzmi iz konfig datoteke
    private String nazivNeispravnogDirektorija = "Neispravne_poruke"; //TODO uzmi iz konfig datoteke
    private String nazivOstalogDirektorija = "Ostale_poruke"; //TODO uzmi iz konfig datoteke

    public ObradaPoruka(ServletContext kontekst) {
        this.kontekst = kontekst;
    }

    @Override
    public void interrupt() {
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

        while (true) {
            int ukupnoPoruka = 0;
            int ispravnoPoruka = 0;
            int neispravnoPoruka = 0;
            int ostaloPoruka = 0;

            try {
                //printData("--------------processing mails started-----------------");
                session = Session.getDefaultInstance(System.getProperties(), null);

                //printData("getting the session for accessing email.");
                store = session.getStore("imap");

                store.connect(this.adresaServera, this.korisnickoIme, this.korisnickaLozinka);
                //printData("Connection established with IMAP server.");

                // Get a handle on the default folder
                folder = store.getDefaultFolder();

                //printData("Getting the Inbox folder.");
                // Retrieve the "Inbox"
                folder = folder.getFolder("inbox");

                //Reading the Email Index in Read / Write Mode
                folder.open(Folder.READ_WRITE);

                // Retrieve the messages
                messages = folder.getMessages();

                // Loop over all of the messages
                for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {
                    sveukupnoPoruka++;
                    ukupnoPoruka++;

                    // Retrieve the next message to be read
                    message = messages[messageNumber];

                    // Retrieve the message content
                    messagecontentObject = message.getContent();

                    String vrstaPoruke = message.getContentType();

                    if (vrstaPoruke.equals("text/plain")) {

                        if (ispravnaPoruka(message)) {
                            
                            ispravnoPoruka++;
                            premjestiPoruku(nazivIspravnogDirektorija, store, message, folder);
                                    
                        } else {
                            premjestiPoruku(nazivNeispravnogDirektorija, store, message, folder);
                            neispravnoPoruka++;
                        }

                    } else {
                        premjestiPoruku(nazivOstalogDirektorija, store, message, folder);
                        ostaloPoruka++;

                    }

                    // Close the folder
                    folder.close(true);//sa ovim true govorim serveru da sve poruke koje su oznacene za brisanje i obrise

                    // Close the message store
                    store.close();
        
                    //TODO korigiraj interval spavanja za utroseno vrijeme
                    sleep(intervalSpavanja);//odlazim na spavanje
                }
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
        if(!noviFolder.exists()){
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

    private boolean ispravnaPoruka(Message message) {
        //TODO provjeri ostale uvjete da poruka bude ispravna (predmet, komande)
        return true;
    }

}
