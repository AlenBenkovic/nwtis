
package org.foi.nwtis.alebenkov.web.podaci;

/**
 *
 * @author alebenkov
 */
public class Adresa {
    private int idadresa;
    private String adresa;
    private Lokacija geoloc;

    public Adresa() {
    }

    public Adresa(int idadresa, String adresa, Lokacija geoloc) {
        this.idadresa = idadresa;
        this.adresa = adresa;
        this.geoloc = geoloc;
    }

    public Lokacija getGeoloc() {
        return geoloc;
    }

    public void setGeoloc(Lokacija geoloc) {
        this.geoloc = geoloc;
    }

    public int getIdadresa() {
        return idadresa;
    }

    public void setIdadresa(int idadresa) {
        this.idadresa = idadresa;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
    
    
}
