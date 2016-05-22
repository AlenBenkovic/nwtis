/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.AuthenticationFailedException;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.ReadOnlyFolderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.servlet.ServletContext;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.konfiguracije.bp.BP_konfiguracija;
import org.foi.nwtis.alebenkov.web.zrna.Datoteka;
import org.foi.nwtis.alebenkov.web.zrna.SlanjePoruke;

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
    private int intervalSpavanja;
    private String nazivIspravnogDirektorija;
    private String nazivNeispravnogDirektorija;
    private String nazivOstalogDirektorija;
    private String dirZaSpremanjeStranica;
    private String naslovPoruke;
    private String naslovPorukeStatistike;
    private String adresaPorukeStatistike;
    private String mailPosluzitelj;
    private Konfiguracija mailConfig;
    private BP_konfiguracija bpConfig;
    private List<Datoteka> datotekeWeb = null;
    private String webSerijalizacija;

    public ObradaPoruka(ServletContext kontekst) {
        this.kontekst = kontekst;
        this.mailConfig = (Konfiguracija) kontekst.getAttribute("mailConfig");
        this.bpConfig = (BP_konfiguracija) kontekst.getAttribute("bpConfig");
        this.adresaServera = mailConfig.dajPostavku("adresaServera");
        this.korisnickoIme = mailConfig.dajPostavku("korisnickoIme");
        this.korisnickaLozinka = mailConfig.dajPostavku("korisnickaLozinka");
        this.intervalSpavanja = Integer.parseInt(mailConfig.dajPostavku("intervalDretve")) * 1000;//spremam u sekundama
        this.nazivIspravnogDirektorija = mailConfig.dajPostavku("nazivIspravnogDirektorija");
        this.nazivNeispravnogDirektorija = mailConfig.dajPostavku("nazivNeispravnogDirektorija");
        this.nazivOstalogDirektorija = mailConfig.dajPostavku("nazivOstalogDirektorija");
        this.dirZaSpremanjeStranica = mailConfig.dajPostavku("dirZaSpremanjeStranica");
        this.naslovPoruke = mailConfig.dajPostavku("naslovPoruke");
        this.naslovPorukeStatistike = mailConfig.dajPostavku("naslovPorukeStatistike");
        this.adresaPorukeStatistike = mailConfig.dajPostavku("adresaPorukeStatistike");
        this.mailPosluzitelj = mailConfig.dajPostavku("mailPosluzitelj");
        this.webSerijalizacija = mailConfig.dajPostavku("webSerijalizacija");
        File direktorijZaSpremanje = new File(this.kontekst.getRealPath("/WEB-INF") + java.io.File.separator + dirZaSpremanjeStranica);
        if (!direktorijZaSpremanje.exists()) {//ukoliko direktorij koji je naveden u konfiguraciji ne postoji, kreiram ga
            try {
                direktorijZaSpremanje.mkdir();
                System.out.println("Kreiram direktorij " + dirZaSpremanjeStranica);
            } catch (SecurityException se) {
                System.out.println("Problem sa dozvolama!");
            }

        }
        File dirWebSerijalizacija = new File(this.kontekst.getRealPath("/WEB-INF") + java.io.File.separator + dirZaSpremanjeStranica + java.io.File.separator + webSerijalizacija);
        datotekeWeb = new ArrayList();
        if (!dirWebSerijalizacija.exists()) {
            spremiZapisWebMjesta();
        }
        datotekeWeb = ucitajZapisWebMjesta();

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
        int redniBrojPoruke = 1;

        while (dretvaRadi) {
            int ukupnoPoruka = 0;
            int ispravnoPoruka = 0;
            int neispravnoPoruka = 0;
            int ostaloPoruka = 0;
            int brojDodanihGrad = 0;
            int brojDodanihTvrtka = 0;
            int brojAzuriranihGrad = 0;
            int brojAzuziranihTvrtka = 0;
            String vrsta; //tvrtka ili grad
            String naziv; //naziv tvrtke ili rada
            String operacija; //ADD ili UPDATE

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy H:mm:ss");
                long pocetakRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve
                System.out.println("|||| Obrada zapocela u: " + sdf.format(pocetakRadaDretve));

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

                    System.out.println("|| " + this.getName() + " | Poruka No." + messageNumber + " | Vrsta poruke: " + vrstaPoruke + "\nNaslov: " + naslovPoruke + "\nSadrzaj: " + sadrzajPoruke);

                    if (vrstaPoruke.startsWith("text/plain")) {
                        Matcher mSadrzaj = provjeraSadrzaja(sadrzajPoruke.trim());

                        if ((naslovPoruke.equalsIgnoreCase(this.naslovPoruke)) && (mSadrzaj != null)) {
                            vrsta = mSadrzaj.group(1);
                            naziv = mSadrzaj.group(2);
                            operacija = mSadrzaj.group(3);
                            System.out.println(this.getName() + " | Poruka " + messageNumber + " | ISPRAVNA");
                            ispravnoPoruka++;

                            if (operacijaNadBazom(vrsta, naziv, operacija)) {
                                dohvatiStranicu(naziv.toLowerCase());

                                if (vrsta.equals("GRAD") && operacija.equals("ADD")) {
                                    brojDodanihGrad++;
                                } else if (vrsta.equals("GRAD") && operacija.equals("UPDATE")) {
                                    brojAzuriranihGrad++;
                                } else if (vrsta.equals("TVRTKA") && operacija.equals("ADD")) {
                                    brojDodanihTvrtka++;
                                } else if (vrsta.equals("TVRTKA") && operacija.equals("UPDATE")) {
                                    brojAzuziranihTvrtka++;
                                }
                            }

                            premjestiPoruku(nazivIspravnogDirektorija, store, message, folder);

                        } else {
                            premjestiPoruku(nazivNeispravnogDirektorija, store, message, folder);
                            neispravnoPoruka++;
                            System.out.println(this.getName() + " | Poruka No." + messageNumber + " | NEISPRAVNA");
                        }

                    } else {
                        premjestiPoruku(nazivOstalogDirektorija, store, message, folder);
                        ostaloPoruka++;
                        System.out.println(this.getName() + " | Poruka " + messageNumber + " | OSTALA");

                    }

                }

                // Close the folder
                folder.close(true);//sa ovim true govorim serveru da sve poruke koje su oznacene za brisanje i obrise

                // Close the message store
                store.close();

                if (ukupnoPoruka == 0) {
                    System.out.println("| Nema novih poruka.");
                }

                System.out.println("Stanje obrade:\n| GRAD ADD: " + brojDodanihGrad + "\n| GRAD UPDATE: " + brojAzuriranihGrad + "\n| TVRTKA ADD: " + brojDodanihTvrtka + "\n| TVRTKA UPDATE: " + brojAzuziranihTvrtka);

                spremiZapisWebMjesta();
                izlistajZapise();
                long krajRadaDretve = System.currentTimeMillis(); //biljezim pocetak rada dretve
                long trajanjeRadaDretve = krajRadaDretve - pocetakRadaDretve;
                System.out.println("|||| Obrada zavrsila u: " + sdf.format(krajRadaDretve));

                SimpleDateFormat ms = new SimpleDateFormat("S");

                SlanjePoruke poruka = new SlanjePoruke();
                String tekstPoruke = "Obrada započela u: " + sdf.format(pocetakRadaDretve)
                        + "\nObrada završila u: " + sdf.format(krajRadaDretve)
                        + "\n\nTrajanje obrade u ms: " + ms.format(trajanjeRadaDretve)
                        + "\nBroj poruka: " + ukupnoPoruka
                        + "\nBroj dodanih podataka GRAD: " + brojDodanihGrad
                        + "\nBroj dodanih podataka TVRTKA: " + brojDodanihTvrtka
                        + "\nBroj ažuriranih podataka GRAD: " + brojAzuriranihGrad
                        + "\nBroj ažuriranih podataka TVRTKA: " + brojAzuziranihTvrtka;
                DecimalFormat df = new DecimalFormat("##.00");
                poruka.setPredmetPoruke(this.naslovPorukeStatistike + " " + df.format(redniBrojPoruke));
                poruka.setTekstPoruke(tekstPoruke);
                poruka.setTipPoruke("text/plain");
                poruka.setPosluzitelj(mailPosluzitelj);
                poruka.setTkoPrima(this.adresaPorukeStatistike);
                poruka.setTkoSalje("servis@nwtis.nastava.foi.hr");
                poruka.saljiPoruku();
                redniBrojPoruke++;
                if((intervalSpavanja - trajanjeRadaDretve)<0){//nekad se zna dogoditi da zbog mail servera koji sporo odgovara vrijeme spavanja bude u minusu
                    sleep(60000);
                }else{
                    sleep(intervalSpavanja - trajanjeRadaDretve);//odlazim na spavanje
                }
                
                

            } catch (AuthenticationFailedException e) {
                System.out.println(e.getMessage());
                dretvaRadi = false;
            } catch (FolderClosedException e) {
                System.out.println(e.getMessage());
                dretvaRadi = false;
            } catch (FolderNotFoundException e) {
                System.out.println(e.getMessage());
                dretvaRadi = false;
            } catch (NoSuchProviderException e) {
                System.out.println(e.getMessage());
                dretvaRadi = false;
            } catch (ReadOnlyFolderException e) {
                System.out.println(e.getMessage());
                dretvaRadi = false;
            } catch (StoreClosedException e) {
                System.out.println(e.getMessage());
                dretvaRadi = false;
            } catch (MessagingException | IOException | InterruptedException e) {
                System.out.println(e.getMessage());
                dretvaRadi = false;
            }
        }
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
            System.out.println("| Greska prilikom kreiranja foldera " + e.getMessage() + e.toString());
        }

    }

    private boolean operacijaNadBazom(String vrsta, String naziv, String operacija) {
        String url = bpConfig.getServerDatabase() + bpConfig.getUserDatabase();
        String korisnik = bpConfig.getUserDatabase();
        String lozinka = bpConfig.getUserPassword();
        Connection connection = null;
        Statement statemant = null;
        ResultSet rs = null;
        boolean sqlExe;
        String sql = null;
        int brojRedaka = 0;

        try {
            Class.forName(bpConfig.getDriverDatabase()); //dovoljno pozvati jednom na razini projekta da bi se ucitao sam driver
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR | Greska kod ucitavanja drivera: " + ex.getMessage());
        }

        try {
            connection = DriverManager.getConnection(url, korisnik, lozinka);
            statemant = connection.createStatement();

            sql = "SELECT * FROM elementi where vrsta = '" + vrsta + "' AND  naziv ='" + naziv + "'";
            rs = statemant.executeQuery(sql);
            while (rs.next()) {
                brojRedaka++; //ovo se moze rjesiti na elegantniji nacin ali ovo mi je trenutno najbrzi (getRow, beforeFirst,..) ali je potrebno podici odredjene zastavice na resultsetu
            }

            if (brojRedaka == 0) {
                if (operacija.equals("UPDATE")) {
                    System.out.println("ERROR | Zapis ne postoji u bazi.");
                } else if (operacija.equals("ADD")) {
                    sql = "INSERT INTO elementi VALUES ('" + vrsta + "','" + naziv + "')";
                    sqlExe = statemant.execute(sql);
                    System.out.println("|| Zapis spremljen u bazu");
                    return true;
                }
            } else if (operacija.equals("ADD")) {
                System.out.println("ERROR | Zapis vec postoji u bazi.");
            } else if (operacija.equals("UPDATE")) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("ERROR | Greska u radu s bazom: " + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (statemant != null) {
                try {
                    statemant.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return false;
    }

    private void dohvatiStranicu(String naziv) {

        //Provjeravam postoji li web stranica
        BufferedInputStream ulaz = null;
        FileOutputStream izlaz = null;

        String domena[] = {".hr", ".info", ".com", ".eu"};

        SimpleDateFormat sdf = new SimpleDateFormat("yMdHms");
        long trenutnoVrijeme = System.currentTimeMillis();
        String dirStranice = naziv + "_" + sdf.format(trenutnoVrijeme);

        for (int i = 0; i < domena.length; i++) {
            try {//provjeravam web mjesto za sve domene
                System.out.println("|| Pokusavam dohvatiti stranicu www." + naziv + domena[i]);
                String url = "http://" + naziv + domena[i];
                ulaz = new BufferedInputStream(new URL(url).openStream());
                if (ulaz != null) {
                    //KREIRANJE DIREKTORIJA WEB STRANICE
                    System.out.println("| Direktorij stranice: " + dirStranice);
                    File direktorijStranice = new File(this.kontekst.getRealPath("/WEB-INF") + java.io.File.separator + dirZaSpremanjeStranica + java.io.File.separator + dirStranice);
                    if (!direktorijStranice.exists()) {
                        try {
                            direktorijStranice.mkdir();
                            System.out.println("| Kreiram direktorij " + dirStranice + " za " + url);
                        } catch (SecurityException se) {
                            System.out.println("| Problem sa dozvolama:");
                        }

                    }
                    //END
                    //path je samo pomocna varijabla za kreiranje datoteke web mjesta
                    String path = this.kontekst.getRealPath("/WEB-INF") + java.io.File.separator + this.dirZaSpremanjeStranica + java.io.File.separator + dirStranice + java.io.File.separator + "www." + naziv + domena[i];
                    File datoteka = new File(path);
                    izlaz = new FileOutputStream(datoteka);
                    String puniNaziv = naziv + domena[i];

                    final byte data[] = new byte[1024];
                    int count;
                    while ((count = ulaz.read(data, 0, 1024)) != -1) {
                        izlaz.write(data, 0, count);
                    }
                    this.datotekeWeb.add(new Datoteka(path, puniNaziv, datoteka.length(), sdf.format(trenutnoVrijeme)));
                } else {
                    System.out.println("ERROR | Stranica " + url + " ne postoji.");
                }
            } catch (MalformedURLException ex) {
                System.out.println("ERROR | Greska prilikom pristupa web stranici ." + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("ERROR | Greska prilikom I/O operacija." + ex.getMessage());
            } finally {
                if (ulaz != null) {
                    try {
                        ulaz.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (izlaz != null) {
                    try {
                        izlaz.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

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

    /**
     * Spremanje objekata spremljenih stranica u datoteku
     */
    public void spremiZapisWebMjesta() {

        try {
            String dat = this.kontekst.getRealPath("/WEB-INF") + java.io.File.separator + dirZaSpremanjeStranica + java.io.File.separator + webSerijalizacija;
            FileOutputStream fileOut = new FileOutputStream(dat);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(datotekeWeb);
            out.close();
            fileOut.close();
            System.out.println("SERVER | Serijalizirana evidencija spremljena u " + dat);
        } catch (IOException ex) {
            System.out.println("ERROR | " + ex.getMessage());
        }
    }

    /**
     * Ucitavanje objekata iz datoteke
     *
     * @return objekte iz datoteke (Spremljena web mjesta)
     */
    public synchronized List<Datoteka> ucitajZapisWebMjesta() {
        List<Datoteka> e = null;
        try {
            String dat = this.kontekst.getRealPath("/WEB-INF") + java.io.File.separator + dirZaSpremanjeStranica + java.io.File.separator + webSerijalizacija;
            FileInputStream fileIn = new FileInputStream(dat);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (List<Datoteka>) in.readObject();
            in.close();
            fileIn.close();

        } catch (ClassNotFoundException c) {
            System.out.println("ERROR | " + c.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR | " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("ERROR | " + ex.getMessage());
        }
        return e;
    }

    public void izlistajZapise() {
        for (int i = 0; i < datotekeWeb.size(); i++) {
            System.out.println(datotekeWeb.get(i).getApsolutnaPutanja());
            System.out.println(datotekeWeb.get(i).getNazivDatoteke());
            System.out.println(datotekeWeb.get(i).getVelicina());
            System.out.println(datotekeWeb.get(i).getVrijemeKreiranja());
        }
    }

}
