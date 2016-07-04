/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.ejb.eb;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alebenkov
 */
@Entity
@Table(name = "ALEBENKOV_DNEVNIK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlebenkovDnevnik.findAll", query = "SELECT a FROM AlebenkovDnevnik a"),
    @NamedQuery(name = "AlebenkovDnevnik.findById", query = "SELECT a FROM AlebenkovDnevnik a WHERE a.id = :id"),
    @NamedQuery(name = "AlebenkovDnevnik.findByKorisnik", query = "SELECT a FROM AlebenkovDnevnik a WHERE a.korisnik = :korisnik"),
    @NamedQuery(name = "AlebenkovDnevnik.findByAkcija", query = "SELECT a FROM AlebenkovDnevnik a WHERE a.akcija = :akcija"),
    @NamedQuery(name = "AlebenkovDnevnik.findByTrajanje", query = "SELECT a FROM AlebenkovDnevnik a WHERE a.trajanje = :trajanje"),
    @NamedQuery(name = "AlebenkovDnevnik.findByVrijeme", query = "SELECT a FROM AlebenkovDnevnik a WHERE a.vrijeme = :vrijeme")})
public class AlebenkovDnevnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "KORISNIK")
    private String korisnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "AKCIJA")
    private String akcija;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRAJANJE")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VRIJEME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vrijeme;

    public AlebenkovDnevnik() {
    }

    public AlebenkovDnevnik(Integer id) {
        this.id = id;
    }

    public AlebenkovDnevnik(Integer id, String korisnik, String akcija, int trajanje, Date vrijeme) {
        this.id = id;
        this.korisnik = korisnik;
        this.akcija = akcija;
        this.trajanje = trajanje;
        this.vrijeme = vrijeme;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public String getAkcija() {
        return akcija;
    }

    public void setAkcija(String akcija) {
        this.akcija = akcija;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(Date vrijeme) {
        this.vrijeme = vrijeme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlebenkovDnevnik)) {
            return false;
        }
        AlebenkovDnevnik other = (AlebenkovDnevnik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.alebenkov.ejb.eb.AlebenkovDnevnik[ id=" + id + " ]";
    }
    
}
