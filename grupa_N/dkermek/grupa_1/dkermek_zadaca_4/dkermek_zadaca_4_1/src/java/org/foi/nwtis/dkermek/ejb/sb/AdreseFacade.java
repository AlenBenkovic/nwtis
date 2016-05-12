/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.ejb.sb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.foi.nwtis.dkermek.ejb.eb.Adrese;

/**
 *
 * @author grupa_1
 */
@Stateless
public class AdreseFacade extends AbstractFacade<Adrese> {

    @PersistenceContext(unitName = "dkermek_zadaca_4_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdreseFacade() {
        super(Adrese.class);
    }
    
}
