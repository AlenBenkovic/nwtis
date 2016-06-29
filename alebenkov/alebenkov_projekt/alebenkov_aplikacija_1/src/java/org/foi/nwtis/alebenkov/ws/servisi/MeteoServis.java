/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.ws.servisi;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.foi.nwtis.alebenkov.web.podaci.Adresa;
import org.foi.nwtis.alebenkov.web.podaci.MeteoPodaci;
import org.foi.nwtis.alebenkov.web.server.DBOps;

/**
 *
 * @author abenkovic
 */
@WebService(serviceName = "MeteoServis")
public class MeteoServis {

    /**
     * Web service operation
     *
     * @param adresa
     * @param user
     * @param pass
     * @return
     */
    @WebMethod(operationName = "zadnjiMeteoPodaci")
    public MeteoPodaci zadnjiMeteoPodaci(@WebParam(name = "adresa") String adresa, @WebParam(name = "user") String user, @WebParam(name = "pass") String pass) {
        DBOps db = new DBOps();
        int[] korisnik = db.provjeraKorisnika(user, pass);
        if (korisnik[0] < 1) {
            MeteoPodaci mp = new MeteoPodaci();
            db.dnevnik(user, "MeteoServis/zadnjiMeteoPodaci", "Korisnicki podaci nisu ispravni");
            mp.setName("Korisnicki podaci nisu ispravni.");
            return mp;
        } else if (!db.provjeraKvote(user)) {
            MeteoPodaci mp = new MeteoPodaci();
            db.dnevnik(user, "MeteoServis/zadnjiMeteoPodaci", "Prekoracen limit");
            mp.setName("Prekoracili ste dozvoljeni limit za upite.");
            return mp;
        } else {
            db.dnevnik(user, "MeteoServis/zadnjiMeteoPodaci", "OK 10");
            MeteoPodaci mp = db.zadnjiMeteoPodaci(adresa);
            return mp;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "adreseKorisnika")
    public List<Adresa> adreseKorisnika(@WebParam(name = "user") String user, @WebParam(name = "pass") String pass) {
        DBOps db = new DBOps();
        int[] korisnik = db.provjeraKorisnika(user, pass);
        if (korisnik[0] < 1) {
            Adresa a = new Adresa();
            db.dnevnik(user, "MeteoServis/adreseKorisnika", "Korisnicki podaci nisu ispravni");
            a.setAdresa("Korisnicki podaci nisu ispravni.");//ovo je katastrofa ali trenutno najbrze jer ne znam drugacije :D
            List<Adresa> adrese = new ArrayList<>();
            adrese.add(a);
            return adrese;
        } else if (!db.provjeraKvote(user)) {
            Adresa a = new Adresa();
            db.dnevnik(user, "MeteoServis/adreseKorisnika", "Prekoracen limit");
            a.setAdresa("Prekoracili ste dozvoljeni limit za upite.");//ovo je katastrofa ali trenutno najbrze jer ne znam drugacije :D
            List<Adresa> adrese = new ArrayList<>();
            adrese.add(a);
            return adrese;
        } else {
            db.dnevnik(user, "MeteoServis/adreseKorisnika", "OK 10");
            List<Adresa> adrese = db.adreseKorisnika(user);
            return adrese;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "rangLista")
    public List<String> rangLista(@WebParam(name = "user") String user, @WebParam(name = "pass") String pass, @WebParam(name = "n") int n) {
        DBOps db = new DBOps();
        int[] korisnik = db.provjeraKorisnika(user, pass);
        if (korisnik[0] < 1) {
            List<String> a = new ArrayList<>();
            db.dnevnik(user, "MeteoServis/rangLista", "Korisnicki podaci nisu ispravni");
            a.add("Korisnicki podaci nisu ispravni");
            return a;

        } else if (!db.provjeraKvote(user)) {
            List<String> a = new ArrayList<>();
            db.dnevnik(user, "MeteoServis/rangLista", "Prekoracen limit");
            a.add("Prekoracili ste dozvoljeni limit za upite.");//ovo je katastrofa ali trenutno najbrze jer ne znam drugacije :D
            return a;

        } else {
            db.dnevnik(user, "MeteoServis/rangLista", "OK 10");
            List<String> adrese = db.rangLista(n);
            return adrese;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "zadnjihNmeteo")
    public List<MeteoPodaci> zadnjihNmeteo(@WebParam(name = "user") String user, @WebParam(name = "pass") String pass, @WebParam(name = "adresa") String adresa, @WebParam(name = "n") int n) {
        DBOps db = new DBOps();
        int[] korisnik = db.provjeraKorisnika(user, pass);
        List<MeteoPodaci> mpl = new ArrayList<>();
        MeteoPodaci mp = new MeteoPodaci();
        if (korisnik[0] < 1) {
            db.dnevnik(user, "MeteoServis/zadnjihNmeteo", "Korisnicki podaci nisu ispravni");
            mp.setName("Korisnicki podaci nisu ispravni");
            mpl.add(mp);
            return mpl;

        } else if (!db.provjeraKvote(user)) {
            db.dnevnik(user, "MeteoServis/zadnjihNmeteo", "Prekoracen limit");
            mp.setName("Prekoracen limit");
            mpl.add(mp);
            return mpl;

        } else {
            db.dnevnik(user, "MeteoServis/zadnjihNmeteo", "OK 10");
            mpl = db.zadnjihN(n, adresa);
            return mpl;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "razdobljeMeteo")
    public List<MeteoPodaci> razdobljeMeteo(@WebParam(name = "user") String user, @WebParam(name = "pass") String pass, @WebParam(name = "adresa") String adresa, @WebParam(name = "odD") String odD, @WebParam(name = "doD") String doD) {
        DBOps db = new DBOps();
        int[] korisnik = db.provjeraKorisnika(user, pass);
        List<MeteoPodaci> mpl = new ArrayList<>();
        MeteoPodaci mp = new MeteoPodaci();
        if (korisnik[0] < 1) {
            db.dnevnik(user, "MeteoServis/razdobljeMeteo", "Korisnicki podaci nisu ispravni");
            mp.setName("Korisnicki podaci nisu ispravni");
            mpl.add(mp);
            return mpl;

        } else if (!db.provjeraKvote(user)) {
            db.dnevnik(user, "MeteoServis/razdobljeMeteo", "Prekoracen limit");
            mp.setName("Prekoracen limit");
            mpl.add(mp);
            return mpl;

        } else {
            db.dnevnik(user, "MeteoServis/razdobljeMeteo", "OK 10");
            mpl = db.razdobljeMeteo(adresa, odD, doD);
            return mpl;
        }
    }

}
