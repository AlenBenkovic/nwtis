/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.ejb.sb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.foi.nwtis.alebenkov.ejb.eb.Korisnik;
import org.foi.nwtis.alebenkov.ejb.eb.Korisnik_;

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

}
