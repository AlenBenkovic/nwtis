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
    private CriteriaQuery<Korisnik> cq;
    private CriteriaBuilder cb;
    private Root<Korisnik> r;
    private List<Predicate> p;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KorisnikFacade() {
        super(Korisnik.class);
    }

    public void init() {
        cb = em.getCriteriaBuilder();
        cq = cb.createQuery(Korisnik.class);
        r = cq.from(Korisnik.class);
        p = new ArrayList<>();
    }

    public Korisnik dohvatiKorisnika(String user) {
        Korisnik k = this.find(user);
        return k;

    }

    public void odobriKorisnika(String user) {
        Korisnik k = this.find(user);
        k.setOdobreno(1);
    }

    public boolean provjeriUsername(String user) {
        Korisnik k = this.find(user);
        if (k == null) {
            return false;
        } else {
            return true;
        }
    }

    public void odbaciKorisnika(String user) {
        Korisnik k = this.find(user);
        k.setOdobreno(2);
    }

    public Korisnik kreirajKorisnika(String user, String surname, String pass, String mail, int role) {
        Korisnik k = new Korisnik(user, surname, pass, mail, role, 1, 0);
        em.persist(k);
        return k;
    }

    public List<Korisnik> korisniciNaCekanju() {
        //nasao upute na http://stackoverflow.com/questions/12618489/jpa-criteria-api-select-only-specific-columns
        init();
        List<Korisnik> naCekanju = null;
        p.add(cb.equal(r.get(Korisnik_.odobreno), 0));
        cq.select(r).where(p.toArray(new Predicate[]{}));
        naCekanju = em.createQuery(cq).getResultList();
        return naCekanju;

    }
    
    public boolean provjeraMail(String mail) {
        init();
        List adrese = null;
        p.add(cb.equal(r.get(Korisnik_.mail), mail));
        cq.select(r).where(p.toArray(new Predicate[]{}));
        adrese = em.createQuery(cq).getResultList();
        if(adrese.size()<1){
            return false;
        }
        return true;

    }

}
