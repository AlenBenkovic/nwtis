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
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import javax.mail.Session;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.foi.nwtis.alebenkov.ejb.eb.Korisnik;
import org.foi.nwtis.alebenkov.ejb.sb.KorisnikFacade;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.web.podaci.JMSporuka;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author abenkovic
 */
public class PozadinskaObrada extends Thread {

    KorisnikFacade korisnikFacade = lookupKorisnikFacadeBean();

    private boolean dretvaRadi = true;
    private Konfiguracija mailConfig = null;
    private int intervalObrade;

    private String adresaServera;
    private String korisnickoIme;
    private String korisnickaLozinka;

    private String nazivUspjesnogDirektorija;
    private String nazivNeuspjesnogDirektorija;
    private String nazivNeispravnogDirektorija;

    private String naslovPoruke;

    private String mailPosluzitelj;

    public PozadinskaObrada() {
        mailConfig = SlusacAplikacije.getServerConfig();
        intervalObrade = Integer.parseInt(mailConfig.dajPostavku("intervalObrade")) * 1000; //podatak je spremeljan u sekundama

        this.adresaServera = mailConfig.dajPostavku("adresaServera");
        this.korisnickoIme = mailConfig.dajPostavku("korisnickoIme");
        this.korisnickaLozinka = mailConfig.dajPostavku("korisnickaLozinka");

        this.nazivUspjesnogDirektorija = mailConfig.dajPostavku("nazivUspjesnogDirektorija");
        this.nazivNeuspjesnogDirektorija = mailConfig.dajPostavku("nazivNeuspjesnogDirektorija");
        this.nazivNeispravnogDirektorija = mailConfig.dajPostavku("nazivNeispravnogDirektorija");

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
                    String user;
                    String pass;
                    String role;
                    ukupnoPoruka++;
                    message = messages[messageNumber]; //uzimam trenutnu poruku kroz koju prolazim
                    String vrstaPoruke = message.getContentType().toLowerCase();//stavljam u mala slova kako god da pise radi kasnije probjere
                    String naslovPoruke = message.getSubject();
                    String sadrzajPoruke = message.getContent().toString();
                    String naredba = "";
                    if (sadrzajPoruke.indexOf("\n") > 0) {
                        naredba = (String) sadrzajPoruke.subSequence(0, sadrzajPoruke.indexOf("\n"));
                    }
                    System.out.println("|| " + this.getName() + " | Poruka No." + messageNumber + " | Vrsta poruke: " + vrstaPoruke + "\nNaslov: " + naslovPoruke + "\nNaredba: " + naredba);
                    Matcher mSadrzaj = provjeraNaredbe(naredba.trim());

                    if (vrstaPoruke.startsWith("text/plain") && naslovPoruke.equalsIgnoreCase(this.naslovPoruke) && mSadrzaj != null) {
                        user = mSadrzaj.group(4);
                        pass = mSadrzaj.group(5);
                        role = mSadrzaj.group(6);
                        if (korisnikCeka(user)) {
                            System.out.println(this.getName() + " | Poruka " + messageNumber + " | USPJESNA");
                            uspjesnePoruke++;
                            premjestiPoruku(nazivUspjesnogDirektorija, store, message, folder);

                        } else {
                            System.out.println(this.getName() + " | Poruka No." + messageNumber + " | NEUSPJESNA");
                            premjestiPoruku(nazivNeuspjesnogDirektorija, store, message, folder);
                            neuspjesnePoruke++;
                        }
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
                
                //SALJEM JMS poruku na kraju
                JMSporuka jms = new JMSporuka(pocetakRadaDretve, pocetakRadaDretve, ukupnoPoruka, uspjesnePoruke, neuspjesnePoruke, neispravnePoruke);
                sendJMSMessageToNWTiS_aplikacija_2(jms);

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
                dretvaRadi=false;
                interrupt();
            } catch (IOException ex) {
                Logger.getLogger(PozadinskaObrada.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JMSException ex) {
                Logger.getLogger(PozadinskaObrada.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NamingException ex) {
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
        String regex = "^USER ([a-zA-Z0-9_]+)\\; PASSWD ([a-zA-Z0-9_]+)\\; (ADD ([a-zA-Z0-9_]+)\\; PASSWD ([a-zA-Z0-9_]+)\\; ROLE (ADMIN|USER))\\; *$"; //4-ime, 5-pass, 6-role
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(p);
        boolean status = m.matches();
        if (status) {
            return m;
        } else {
            return null;
        }
    }

    public boolean korisnikCeka(String user) {
        //provjera ceka li korisnik na odobrenje, ako ceka ide true i odobravanje, ako ne ceka ide false
        //0-nije odobren, 1-odobren
        Korisnik k = korisnikFacade.dohvatiKorisnika(user);
        if (k != null) {
            if (k.getOdobreno() == 0) {
                korisnikFacade.odobriKorisnika(user);
                return true;
            } else return false;

        }
        return false;
    }

    public boolean isDretvaRadi() {
        return dretvaRadi;
    }

    public void setDretvaRadi(boolean dretvaRadi) {
        this.dretvaRadi = dretvaRadi;
    }

    private KorisnikFacade lookupKorisnikFacadeBean() {
        try {
            Context c = new InitialContext();
            return (KorisnikFacade) c.lookup("java:global/alebenkov_aplikacija_2/alebenkov_aplikacija_2_1/KorisnikFacade!org.foi.nwtis.alebenkov.ejb.sb.KorisnikFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private javax.jms.Message createJMSMessageForjmsNWTiS_aplikacija_2(javax.jms.Session session, Object messageData) throws JMSException {
        // TODO create and populate message to send
        TextMessage tm = session.createTextMessage();
        tm.setText(messageData.toString());
        return tm;
    }

    private void sendJMSMessageToNWTiS_aplikacija_2(Object messageData) throws JMSException, NamingException {
        Context c = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) c.lookup("java:comp/env/jms/NWTIS_QF_aplikacija_2");
        Connection conn = null;
        javax.jms.Session s = null;
        try {
            conn = cf.createConnection();
            s = conn.createSession(false, s.AUTO_ACKNOWLEDGE);
            Destination destination = (Destination) c.lookup("java:comp/env/jms/NWTiS_aplikacija_2");
            MessageProducer mp = s.createProducer(destination);
            mp.send(createJMSMessageForjmsNWTiS_aplikacija_2(s, messageData));
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

}
