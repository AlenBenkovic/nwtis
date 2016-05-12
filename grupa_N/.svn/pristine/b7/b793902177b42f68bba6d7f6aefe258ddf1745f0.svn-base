/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.matnovak.web.zrna;

import static java.lang.ProcessBuilder.Redirect.from;
import static java.lang.ProcessBuilder.Redirect.to;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import static javax.ws.rs.client.Entity.text;

/**
 *
 * @author grupa_3
 */
@Named(value = "slanjePoruke")
@RequestScoped
public class SlanjePoruke {

    private String salje;
    private String prima;
    private String predmet;
    private String sadrzaj;
    private String poruka;
    private String posluzitelj = "localhost"; //TODO čitaj iz konfiguracije

    /**
     * Creates a new instance of SlanjePoruke
     */
    public SlanjePoruke() {
    }

    public String getSalje() {
        return salje;
    }

    public void setSalje(String salje) {
        this.salje = salje;
    }

    public String getPrima() {
        return prima;
    }

    public void setPrima(String prima) {
        this.prima = prima;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public String posaljiPoruku() {

        try {
            // Create the JavaMail session
            java.util.Properties properties = System.getProperties();
            properties.put("mail.smtp.host", posluzitelj);

            Session session = Session.getInstance(properties, null);

            // Construct the message
            MimeMessage message = new MimeMessage(session);

            // Set the from address
            Address fromAddress = new InternetAddress(salje);
            message.setFrom(fromAddress);

            // Parse and set the recipient addresses
            Address[] toAddresses = InternetAddress.parse(prima);
            message.setRecipients(Message.RecipientType.TO, toAddresses);
          
            // Set the subject and text
            message.setSubject(predmet);
            message.setText(sadrzaj);

            Transport.send(message);

            poruka = "Poruka je uspješno poslana!";
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            poruka = "ERROR: "+e.getMessage();
            return "ERROR";
        } 

        
    }
}
