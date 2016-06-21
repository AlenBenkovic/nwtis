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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import org.foi.nwtis.alebenkov.ejb.eb.Adrese;
import org.foi.nwtis.alebenkov.ejb.sb.AdreseFacade;
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
    private List<MeteoPrognoza> prognozeVremena;
    private String tekstGreske = "";
    private MeteoPrognoza[] mp;

    /**
     * Creates a new instance of OdabirAdresaPrognoza
     */
    public OdabirAdresaPrognoza() {
        kandidiraneAdrese = new HashMap<>();
    }

    public String getNovaAdresa() {
        return novaAdresa;
    }

    public void setNovaAdresa(String novaAdresa) {
        this.novaAdresa = novaAdresa;
    }

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

    public void setAktivneAdrese(Map<String, Object> aktivneAdrese) {
        this.aktivneAdrese = aktivneAdrese;
    }

    public List<String> getAdreseZaDodavanje() {
        return adreseZaDodavanje;
    }

    public void setAdreseZaDodavanje(List<String> adreseZaDodavanje) {
        this.adreseZaDodavanje = adreseZaDodavanje;
    }

    public Map<String, Object> getKandidiraneAdrese() {
        return kandidiraneAdrese;
    }

    public void setKandidiraneAdrese(Map<String, Object> kandidiraneAdrese) {
        this.kandidiraneAdrese = kandidiraneAdrese;
    }

    public List<String> getOdabraneAdrese() {
        return OdabraneAdrese;
    }

    public void setOdabraneAdrese(List<String> OdabraneAdrese) {
        this.OdabraneAdrese = OdabraneAdrese;
    }

    public List<String> getAdreseZaBrisanje() {
        return OdabraneAdrese;
    }

    public void setAdreseZaBrisanje(List<String> adreseZaBrisanje) {
        this.OdabraneAdrese = adreseZaBrisanje;
    }

    public String getAzuriranaAdresa() {
        return azuriranaAdresa;
    }

    public void setAzuriranaAdresa(String azuriranaAdresa) {
        this.azuriranaAdresa = azuriranaAdresa;
    }

    public String getIdAzuriraneAdrese() {
        return idAzuriraneAdrese;
    }

    public void setIdAzuriraneAdrese(String idAzuriraneAdrese) {
        this.idAzuriraneAdrese = idAzuriraneAdrese;
    }

    public boolean isPrikazAzuriranjaAdrese() {
        return prikazAzuriranjaAdrese;
    }

    public void setPrikazAzuriranjaAdrese(boolean prikazAzuriranjaAdrese) {
        this.prikazAzuriranjaAdrese = prikazAzuriranjaAdrese;
    }

    public boolean isPrikazPrognoze() {
        return prikazPrognoze;
    }

    public void setPrikazPrognoze(boolean prikazPrognoze) {
        this.prikazPrognoze = prikazPrognoze;
    }

   

    public List<MeteoPrognoza> getPrognozeVremena() {
        return prognozeVremena;
    }

    public void setPrognozeVremena(List<MeteoPrognoza> prognozeVremena) {
        this.prognozeVremena = prognozeVremena;
    }

    public String dodajNovuAdresu() {
        Lokacija l = meteoAdresniKlijent.dajLokaciju(novaAdresa);
        Adrese dodanaAdresa = new Adrese(Integer.BYTES, novaAdresa, l.getLatitude(), l.getLongitude());
        adreseFacade.create(dodanaAdresa);

        return "";
    }

    public String preuzmiAdrese() {
        for (String a : adreseZaDodavanje) {
            Iterator<Map.Entry<String, Object>> iterator = aktivneAdrese.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> adresaEntry = iterator.next();
                if (adresaEntry.getValue().toString().compareTo(a) == 0) {
                    kandidiraneAdrese.put(adresaEntry.getKey(), a);
                }
            }
        }
        return "";
    }

    public String ukloniAdrese() {
        kandidiraneAdresePomocna = new HashMap<String, Object>(kandidiraneAdrese);//radim kopiju jer ne mogu brisati kod iteracije
        for (String a : OdabraneAdrese) {
            for (Map.Entry<String, Object> e : kandidiraneAdresePomocna.entrySet()) {
                if (e.getValue().toString().compareTo(a) == 0) {
                    kandidiraneAdrese.remove(e.getKey());
                    prikazPrognoze = false;
                }
            }
        }

        return "";
    }

    public String upisiAdresu() {
        Lokacija l = meteoAdresniKlijent.dajLokaciju(azuriranaAdresa);
        Adrese ispravljenaAdresa = new Adrese(Integer.parseInt(idAzuriraneAdrese), azuriranaAdresa, l.getLatitude(), l.getLongitude());
        adreseFacade.edit(ispravljenaAdresa);
        prikazAzuriranjaAdrese = false;

        return "";
    }

    public String azurirajAdresu() {
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
                    break;
                }
            }
        }
        return "";
    }

    public boolean isPrikazGreske() {
        return prikazGreske;
    }

    public void setPrikazGreske(boolean prikazGreske) {
        this.prikazGreske = prikazGreske;
    }

    public String getTekstGreske() {
        return tekstGreske;
    }

    public void setTekstGreske(String tekstGreske) {
        this.tekstGreske = tekstGreske;
    }

    public MeteoPrognoza[] getMp() {
        return mp;
    }

    public void setMp(MeteoPrognoza[] mp) {
        this.mp = mp;
    }

    public void dohvatiPrognozu() {
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
                }

            }
        }

    }

}