/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.ejb.eb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alebenkov
 */
@Entity
@Table(name = "ALEBENKOV_KORISNICI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByKorisnik", query = "SELECT k FROM Korisnik k WHERE k.korisnik = :korisnik"),
    @NamedQuery(name = "Korisnik.findByPrezime", query = "SELECT k FROM Korisnik k WHERE k.prezime = :prezime"),
    @NamedQuery(name = "Korisnik.findByPass", query = "SELECT k FROM Korisnik k WHERE k.pass = :pass"),
    @NamedQuery(name = "Korisnik.findByMail", query = "SELECT k FROM Korisnik k WHERE k.mail = :mail"),
    @NamedQuery(name = "Korisnik.findByRole", query = "SELECT k FROM Korisnik k WHERE k.role = :role"),
    @NamedQuery(name = "Korisnik.findByRang", query = "SELECT k FROM Korisnik k WHERE k.rang = :rang"),
    @NamedQuery(name = "Korisnik.findByOdobreno", query = "SELECT k FROM Korisnik k WHERE k.odobreno = :odobreno")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "KORISNIK")
    private String korisnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "PREZIME")
    private String prezime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "PASS")
    private String pass;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "MAIL")
    private String mail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROLE")
    private int role;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RANG")
    private int rang;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ODOBRENO")
    private int odobreno;

    public Korisnik() {
    }

    public Korisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public Korisnik(String korisnik, String prezime, String pass, String mail, int role, int rang, int odobreno) {
        this.korisnik = korisnik;
        this.prezime = prezime;
        this.pass = pass;
        this.mail = mail;
        this.role = role;
        this.rang = rang;
        this.odobreno = odobreno;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public int getOdobreno() {
        return odobreno;
    }

    public void setOdobreno(int odobreno) {
        this.odobreno = odobreno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (korisnik != null ? korisnik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.korisnik == null && other.korisnik != null) || (this.korisnik != null && !this.korisnik.equals(other.korisnik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.alebenkov.ejb.eb.Korisnik[ korisnik=" + korisnik + " ]";
    }
    
}
