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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @NamedQuery(name = "AlebenkovKorisnici.findAll", query = "SELECT a FROM AlebenkovKorisnici a"),
    @NamedQuery(name = "AlebenkovKorisnici.findById", query = "SELECT a FROM AlebenkovKorisnici a WHERE a.id = :id"),
    @NamedQuery(name = "AlebenkovKorisnici.findByKorisnik", query = "SELECT a FROM AlebenkovKorisnici a WHERE a.korisnik = :korisnik"),
    @NamedQuery(name = "AlebenkovKorisnici.findByPass", query = "SELECT a FROM AlebenkovKorisnici a WHERE a.pass = :pass"),
    @NamedQuery(name = "AlebenkovKorisnici.findByRole", query = "SELECT a FROM AlebenkovKorisnici a WHERE a.role = :role"),
    @NamedQuery(name = "AlebenkovKorisnici.findByRang", query = "SELECT a FROM AlebenkovKorisnici a WHERE a.rang = :rang"),
    @NamedQuery(name = "AlebenkovKorisnici.findByOdobreno", query = "SELECT a FROM AlebenkovKorisnici a WHERE a.odobreno = :odobreno")})
public class AlebenkovKorisnici implements Serializable {

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
    @Size(min = 1, max = 25)
    @Column(name = "PASS")
    private String pass;
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

    public AlebenkovKorisnici() {
    }

    public AlebenkovKorisnici(Integer id) {
        this.id = id;
    }

    public AlebenkovKorisnici(Integer id, String korisnik, String pass, int role, int rang, int odobreno) {
        this.id = id;
        this.korisnik = korisnik;
        this.pass = pass;
        this.role = role;
        this.rang = rang;
        this.odobreno = odobreno;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlebenkovKorisnici)) {
            return false;
        }
        AlebenkovKorisnici other = (AlebenkovKorisnici) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.alebenkov.ejb.eb.AlebenkovKorisnici[ id=" + id + " ]";
    }
    
}
