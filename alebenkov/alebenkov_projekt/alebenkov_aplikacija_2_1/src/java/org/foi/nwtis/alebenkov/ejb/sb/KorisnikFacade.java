/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.ejb.sb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.foi.nwtis.alebenkov.ejb.eb.Korisnik;

/**
 *
 * @author alebenkov
 */
@Stateless
public class KorisnikFacade extends AbstractFacade<Korisnik> {

    @PersistenceContext(unitName = "alebenkov_aplikacija_2_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KorisnikFacade() {
        super(Korisnik.class);
    }

    public Korisnik dohvatiKorisnika(String user) {
        Korisnik k = this.find(user);
        return k;

    }

    public void odobriKorisnika(String user) {
        Korisnik k = this.find(user);
        k.setOdobreno(1);
    }
    
    public Korisnik kreirajKorisnika(String user, String surname, String pass, String mail, int role){
        Korisnik k = new Korisnik(user, surname, pass, mail, role, 1, 0);
        em.persist(k);
        return k;
    }
}
