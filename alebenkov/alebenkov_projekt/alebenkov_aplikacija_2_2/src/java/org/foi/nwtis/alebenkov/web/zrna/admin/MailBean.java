/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import org.foi.nwtis.alebenkov.konfiguracije.Konfiguracija;
import org.foi.nwtis.alebenkov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author alebenkov
 */
@Named(value = "mailBean")
@SessionScoped
public class MailBean implements Serializable {

    private Konfiguracija mailConfig = null;

    private String adresaServera;
    private String korisnickoIme;
    private String korisnickaLozinka;

    private String mailPosluzitelj;

    private Session session = null;
    private Store store = null;
    private Folder folder = null;
    private Folder odabraniFolder = null;
    private Message message = null;
    private String tekstPoruke = "";
    private String posiljateljPoruke = "";
    private Message[] messages = null;
    private String nazivOdabranogFoldera = "INBOX";

    private Folder[] folderi;

    private boolean prikaziPoruke = false;
    private boolean prikaziPoruku = false;

    /**
     * Creates a new instance of MailBean
     */
    public MailBean() {
        mailConfig = SlusacAplikacije.getServerConfig();

        this.adresaServera = mailConfig.dajPostavku("adresaServera");
        this.korisnickoIme = mailConfig.dajPostavku("korisnickoIme");
        this.korisnickaLozinka = mailConfig.dajPostavku("korisnickaLozinka");

        this.mailPosluzitelj = mailConfig.dajPostavku("mailPosluzitelj");
        if (store != null) {
            if (!store.isConnected()) {
                init();
            }
        } else {
            init();
        }

    }

    public void init() {
        try {
            session = Session.getDefaultInstance(System.getProperties(), null); //procesiranje maila pocinje
            store = session.getStore("imap"); //spremam sesiju za pristup mailu
            store.connect(this.adresaServera, this.korisnickoIme, this.korisnickaLozinka);//spajam se na IMAP server
            folder = store.getDefaultFolder(); //uzimam default folder
            folderi = folder.list();
        } catch (MessagingException ex) {
            Logger.getLogger(MailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dohvatiPoruke() {

        try {
            this.odabraniFolder = this.folder.getFolder(nazivOdabranogFoldera);
            this.odabraniFolder.open(Folder.READ_WRITE); //citam postu u index folderu u RW modu
            this.messages = this.odabraniFolder.getMessages();//uzimam poruke iz foldera
            tekstPoruke = "";//mali fix da sakrijem zadnju citanu poruku
        } catch (MessagingException ex) {
            Logger.getLogger(MailBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void dohvatiPoruku(int id) {

        this.message = this.messages[id - 1];
        try {
            this.tekstPoruke = this.message.getContent().toString();
            Address[] froms = message.getFrom();
            this.posiljateljPoruke = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
            if (this.tekstPoruke != "") {
                prikaziPoruku = true;
            }

        } catch (IOException ex) {
            Logger.getLogger(MailBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obrisiPoruku(int id) {

        this.message = this.messages[id - 1];
        try {
            this.message.setFlag(Flags.Flag.DELETED, true);
            odabraniFolder.close(true);
            store.close();
            dohvatiPoruke();
        } catch (MessagingException ex) {
            Logger.getLogger(MailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Folder[] getFolderi() {

        try {
            folderi = folder.list();
        } catch (MessagingException ex) {
            Logger.getLogger(MailBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return folderi;
    }

    public Message[] getMessages() {
        return messages;
    }

    public String getNazivOdabranogFoldera() {
        return nazivOdabranogFoldera;
    }

    public void setNazivOdabranogFoldera(String nazivOdabranogFoldera) {
        this.nazivOdabranogFoldera = nazivOdabranogFoldera;
    }

    public boolean isPrikaziPoruke() {
        if (this.messages != null) {
            if (this.messages.length > 1) {
                prikaziPoruke = true;
            }

        }
        return prikaziPoruke;
    }

    public Message getMessage() {
        return message;
    }

    public String getTekstPoruke() {
        return tekstPoruke;
    }

    public String getPosiljateljPoruke() {
        return posiljateljPoruke;
    }

    public boolean isPrikaziPoruku() {
        if (this.tekstPoruke != "") {
            prikaziPoruku = true;
        } else {
            prikaziPoruku = false;
        }
        return prikaziPoruku;
    }

}
