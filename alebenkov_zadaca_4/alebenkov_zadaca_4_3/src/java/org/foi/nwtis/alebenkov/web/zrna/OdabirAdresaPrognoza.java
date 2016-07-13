/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.foi.nwtis.alebenkov.ejb.eb.Adrese;
import org.foi.nwtis.alebenkov.ejb.eb.Dnevnik;
import org.foi.nwtis.alebenkov.ejb.sb.AdreseFacade;
import org.foi.nwtis.alebenkov.ejb.sb.DnevnikFacade;
import org.foi.nwtis.alebenkov.ejb.sb.MeteoAdresniKlijent;
import org.foi.nwtis.alebenkov.web.podaci.Lokacija;
import org.foi.nwtis.alebenkov.web.podaci.MeteoPrognoza;

/**
 *
 * @author grupa_1
 */
@Named(value = "odabirAdresaPrognoza")
@SessionScoped
public class OdabirAdresaPrognoza implements Serializable {

    @EJB
    private DnevnikFacade dnevnikFacade;

    @EJB
    private MeteoAdresniKlijent meteoAdresniKlijent;

    @EJB
    private AdreseFacade adreseFacade;

    private String novaAdresa;
    private Map<String, Object> aktivneAdrese;
    private List<String> adreseZaDodavanje;
    private Map<String, Object> kandidiraneAdrese;
    private Map<String, Object> kandidiraneAdresePomocna; //problem sa brisanjem kod iteracije
    private List<String> OdabraneAdrese;
    private String azuriranaAdresa;
    private String idAzuriraneAdrese;
    private boolean prikazAzuriranjaAdrese = false;
    private boolean prikazPrognoze = false;
    private boolean prikazGreske = false;
    private boolean prikazOdabranihAdresa = false;
    private List<MeteoPrognoza> prognozeVremena;
    private String tekstGreske = "";
    private MeteoPrognoza[] mp;

    /**
     * Metoda za spremanje zapisa u dnevnik
     *
     * @param korisnik
     * @param url
     * @param ipadresa
     * @param trajanje
     * @param status
     */
    public void recordLog(String korisnik, String url, String ipadresa, int trajanje, int status) {
        Date datum = new Date();
        Dnevnik d = new Dnevnik();

        d.setKorisnik(korisnik);
        d.setUrl(url);
        d.setIpadresa(ipadresa);
        d.setTrajanje(trajanje);
        d.setStatus(status);
        d.setVrijeme(datum);

        dnevnikFacade.create(d);
    }

    /**
     * Creates a new instance of OdabirAdresaPrognoza
     */
    public OdabirAdresaPrognoza() {
        kandidiraneAdrese = new HashMap<>();
    }

    /**
     *
     * @return
     */
    public String getNovaAdresa() {
        return novaAdresa;
    }

    /**
     *
     * @param novaAdresa
     */
    public void setNovaAdresa(String novaAdresa) {
        this.novaAdresa = novaAdresa;
    }

    /**
     *
     * @return
     */
    public Map<String, Object> getAktivneAdrese() {
        List<Adrese> adrese = adreseFacade.findAll();
        aktivneAdrese = new HashMap<>();
        for (Adrese a : adrese) {
            boolean postoji = false;
            Iterator<Map.Entry<String, Object>> iterator = kandidiraneAdrese.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> adresaEntry = iterator.next();
                if (adresaEntry.getValue().toString().compareTo(a.getIdadresa().toString()) == 0) {
                    postoji = true;
                    break;
                }
            }
            if (!postoji) {
                aktivneAdrese.put(a.getAdresa(), a.getIdadresa().toString());
            }
        }
        return aktivneAdrese;
    }

    /**
     *
     * @param aktivneAdrese
     */
    public void setAktivneAdrese(Map<String, Object> aktivneAdrese) {
        this.aktivneAdrese = aktivneAdrese;
    }

    /**
     *
     * @return
     */
    public List<String> getAdreseZaDodavanje() {
        return adreseZaDodavanje;
    }

    /**
     *
     * @param adreseZaDodavanje
     */
    public void setAdreseZaDodavanje(List<String> adreseZaDodavanje) {
        this.adreseZaDodavanje = adreseZaDodavanje;
    }

    /**
     *
     * @return
     */
    public Map<String, Object> getKandidiraneAdrese() {
        return kandidiraneAdrese;
    }

    /**
     *
     * @param kandidiraneAdrese
     */
    public void setKandidiraneAdrese(Map<String, Object> kandidiraneAdrese) {
        this.kandidiraneAdrese = kandidiraneAdrese;
    }

    /**
     *
     * @return
     */
    public boolean isPrikazOdabranihAdresa() {
        return prikazOdabranihAdresa;
    }

    /**
     *
     * @param prikazOdabranihAdresa
     */
    public void setPrikazOdabranihAdresa(boolean prikazOdabranihAdresa) {
        this.prikazOdabranihAdresa = prikazOdabranihAdresa;
    }

    /**
     *
     * @return
     */
    public List<String> getOdabraneAdrese() {
        return OdabraneAdrese;
    }

    /**
     *
     * @param OdabraneAdrese
     */
    public void setOdabraneAdrese(List<String> OdabraneAdrese) {
        this.OdabraneAdrese = OdabraneAdrese;
    }

    /**
     *
     * @return
     */
    public List<String> getAdreseZaBrisanje() {
        return OdabraneAdrese;
    }

    /**
     *
     * @param adreseZaBrisanje
     */
    public void setAdreseZaBrisanje(List<String> adreseZaBrisanje) {
        this.OdabraneAdrese = adreseZaBrisanje;
    }

    /**
     *
     * @return
     */
    public String getAzuriranaAdresa() {
        return azuriranaAdresa;
    }

    /**
     *
     * @param azuriranaAdresa
     */
    public void setAzuriranaAdresa(String azuriranaAdresa) {
        this.azuriranaAdresa = azuriranaAdresa;
    }

    /**
     *
     * @return
     */
    public String getIdAzuriraneAdrese() {
        return idAzuriraneAdrese;
    }

    /**
     *
     * @param idAzuriraneAdrese
     */
    public void setIdAzuriraneAdrese(String idAzuriraneAdrese) {
        this.idAzuriraneAdrese = idAzuriraneAdrese;
    }

    /**
     *
     * @return
     */
    public boolean isPrikazAzuriranjaAdrese() {
        return prikazAzuriranjaAdrese;
    }

    /**
     *
     * @param prikazAzuriranjaAdrese
     */
    public void setPrikazAzuriranjaAdrese(boolean prikazAzuriranjaAdrese) {
        this.prikazAzuriranjaAdrese = prikazAzuriranjaAdrese;
    }

    /**
     *
     * @return
     */
    public boolean isPrikazPrognoze() {
        return prikazPrognoze;
    }

    /**
     *
     * @param prikazPrognoze
     */
    public void setPrikazPrognoze(boolean prikazPrognoze) {
        this.prikazPrognoze = prikazPrognoze;
    }

    /**
     *
     * @return
     */
    public List<MeteoPrognoza> getPrognozeVremena() {
        return prognozeVremena;
    }

    /**
     *
     * @param prognozeVremena
     */
    public void setPrognozeVremena(List<MeteoPrognoza> prognozeVremena) {
        this.prognozeVremena = prognozeVremena;
    }

    /**
     *
     * @return
     */
    public String dodajNovuAdresu() {
        int statusAkcije = 999;
        long pocetakObrade = System.currentTimeMillis();
        Lokacija l = meteoAdresniKlijent.dajLokaciju(novaAdresa);
        if (l.getLatitude() == null || l.getLongitude() == null) {
            tekstGreske = "Ne postoji geolokacija za unesenu adresu. Pokusajte ponovno.";
            prikazGreske = true;
            long krajObrade = System.currentTimeMillis();
            int trajanjeObrade = (int) (krajObrade - pocetakObrade);
            recordLog("alen", getURL(), getIP(), trajanjeObrade, statusAkcije);
        } else {
            prikazGreske = false;
            Adrese dodanaAdresa = new Adrese();
            dodanaAdresa.setAdresa(novaAdresa);
            dodanaAdresa.setLatitude(l.getLatitude());
            dodanaAdresa.setLongitude(l.getLongitude());
            adreseFacade.create(dodanaAdresa);
            statusAkcije = 1;
            long krajObrade = System.currentTimeMillis();
            int trajanjeObrade = (int) (krajObrade - pocetakObrade);
            recordLog("alen", getURL(), getIP(), trajanjeObrade, statusAkcije);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String preuzmiAdrese() {

        int statusAkcije = 999;
        long pocetakObrade = System.currentTimeMillis();
        for (String a : adreseZaDodavanje) {
            Iterator<Map.Entry<String, Object>> iterator = aktivneAdrese.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> adresaEntry = iterator.next();
                if (adresaEntry.getValue().toString().compareTo(a) == 0) {
                    kandidiraneAdrese.put(adresaEntry.getKey(), a);
                    statusAkcije = 1;
                    prikazOdabranihAdresa = true;
                }
            }
        }
        long krajObrade = System.currentTimeMillis(); //biljezim kraj rada dretve
        int trajanjeObrade = (int) (krajObrade - pocetakObrade);
        recordLog("alen", getURL(), getIP(), trajanjeObrade, statusAkcije);
        return "";
    }

    /**
     * Uklanjam adrese iz liste odabranih adresa
     *
     * @return
     */
    public String ukloniAdrese() {
        int statusAkcije = 999;
        long pocetakObrade = System.currentTimeMillis();
        kandidiraneAdresePomocna = new HashMap<String, Object>(kandidiraneAdrese);//radim kopiju jer ne mogu brisati kod iteracije
        for (String a : OdabraneAdrese) {
            for (Map.Entry<String, Object> e : kandidiraneAdresePomocna.entrySet()) {
                if (e.getValue().toString().compareTo(a) == 0) {
                    kandidiraneAdrese.remove(e.getKey());
                    prikazPrognoze = false;
                    statusAkcije = 1;
                }
            }
        }

        long krajObrade = System.currentTimeMillis(); //biljezim kraj rada dretve
        int trajanjeObrade = (int) (krajObrade - pocetakObrade);
        recordLog("alen", getURL(), getIP(), trajanjeObrade, statusAkcije);
        return "";
    }

    /**
     * Azuriranje adrese u bazi
     *
     * @return
     */
    public String upisiAdresu() {
        int statusAkcije = 999;
        long pocetakObrade = System.currentTimeMillis();
        Lokacija l = meteoAdresniKlijent.dajLokaciju(azuriranaAdresa);
        if (l.getLatitude() == null || l.getLongitude() == null) {
            tekstGreske = "Ne postoji geolokacija za novu adresu. Pokusajte ponovno.";
            prikazGreske = true;
            long krajObrade = System.currentTimeMillis();
            int trajanjeObrade = (int) (krajObrade - pocetakObrade);
            recordLog("alen", getURL(), getIP(), trajanjeObrade, statusAkcije);
        } else {
            Adrese ispravljenaAdresa = new Adrese(Integer.parseInt(idAzuriraneAdrese), azuriranaAdresa, l.getLatitude(), l.getLongitude());
            adreseFacade.edit(ispravljenaAdresa);
            prikazAzuriranjaAdrese = false;
            prikazGreske = false;
            statusAkcije = 1;
            long krajObrade = System.currentTimeMillis(); //biljezim kraj rada dretve
            int trajanjeObrade = (int) (krajObrade - pocetakObrade);
            recordLog("alen", getURL(), getIP(), trajanjeObrade, statusAkcije);
        }
        return "";
    }

    /**
     * Azuriranje adrese u obrascu
     *
     * @return
     */
    public String azurirajAdresu() {
        int statusAkcije = 999;
        long pocetakObrade = System.currentTimeMillis();
        prikazAzuriranjaAdrese = true;
        if (adreseZaDodavanje.size() != 1) {
            tekstGreske = "Smijete dodati samo jednu adresu.";
            prikazGreske = true;
        } else {
            idAzuriraneAdrese = adreseZaDodavanje.get(0);
            Iterator<Map.Entry<String, Object>> iterator = aktivneAdrese.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> adresaEntry = iterator.next();
                if (adresaEntry.getValue().toString().compareTo(idAzuriraneAdrese) == 0) {
                    azuriranaAdresa = adresaEntry.getKey();
                    prikazAzuriranjaAdrese = true;
                    statusAkcije = 1;
                    break;
                }
            }
        }
        long krajObrade = System.currentTimeMillis(); //biljezim kraj rada dretve
        int trajanjeObrade = (int) (krajObrade - pocetakObrade);
        recordLog("alen", getURL(), getIP(), trajanjeObrade, statusAkcije);
        return "";
    }

    /**
     *
     * @return
     */
    public boolean isPrikazGreske() {
        return prikazGreske;
    }

    /**
     *
     * @param prikazGreske
     */
    public void setPrikazGreske(boolean prikazGreske) {
        this.prikazGreske = prikazGreske;
    }

    /**
     *
     * @return
     */
    public String getTekstGreske() {
        return tekstGreske;
    }

    /**
     *
     * @param tekstGreske
     */
    public void setTekstGreske(String tekstGreske) {
        this.tekstGreske = tekstGreske;
    }

    /**
     *
     * @return
     */
    public MeteoPrognoza[] getMp() {
        return mp;
    }

    /**
     *
     * @param mp
     */
    public void setMp(MeteoPrognoza[] mp) {
        this.mp = mp;
    }

    /**
     * Metoda za dohvat prognoze odabranog grada
     */
    public void dohvatiPrognozu() {
        int statusAkcije = 999;
        long pocetakObrade = System.currentTimeMillis();
        prognozeVremena = new ArrayList<>();
        if (OdabraneAdrese.size() != 1) {
            tekstGreske = "SMIJETE ODABRATI SAMO JEDNU ADRESU ZA PROGNOZU!";
            prikazGreske = true;
        } else {
            prikazGreske = false;
            for (Map.Entry<String, Object> e : kandidiraneAdrese.entrySet()) {

                if (OdabraneAdrese.get(0).compareTo(e.getValue().toString()) == 0) {
                    System.out.println("Trazim meteo prognozu za " + e.getKey());
                    mp = meteoAdresniKlijent.dajMeteoPrognoze(e.getKey());

                    System.out.println("Dohvatio sam meteo podatke, prolazim kroz petlju.." + mp.length);

                    prikazPrognoze = true;
                    for (int i = 0; i < mp.length; i++) {
                        prognozeVremena.add(mp[i]);
                    }
                    statusAkcije = 1;
                }

            }
        }
        long krajObrade = System.currentTimeMillis(); //biljezim kraj rada dretve
        int trajanjeObrade = (int) (krajObrade - pocetakObrade);
        recordLog("alen", getURL(), getIP(), trajanjeObrade, statusAkcije);
    }

    /**
     * Pomocna metoda za dohvati IP-a (IPv6)
     *
     * @return
     */
    public static String getIP() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ip = request.getRemoteAddr();
        return ip;
    }

    /**
     * Pomocna metoda za dohvat trazenog url-a
     *
     * @return
     */
    public static String getURL() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ip = request.getRequestURI();
        return ip;
    }

}
