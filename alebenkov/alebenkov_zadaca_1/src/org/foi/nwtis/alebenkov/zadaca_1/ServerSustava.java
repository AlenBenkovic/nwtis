
package org.foi.nwtis.alebenkov.zadaca_1;

/**
 * Server class
 * @author Alen Benkovic
 */
public class ServerSustava {

    private static String datoteka; //konfig datoteka
    private static boolean load=false; //datoteka sa serijaliziranim podacima

    
    /**
     * 
     * @param datoteka - naziv konfiguracijske datoteke
     * @param load - parametar za ucitavanje datoteke sa serijaliziranim podacima
     * 
     */
    
    public ServerSustava(String datoteka, String load) {
        this.datoteka = datoteka;
        if(load != null) this.load=true;
        System.out.println(datoteka);
        System.out.println(this.load);
    }

    public void pokreniServer() {
        //TODO pokreni server
        System.out.println("Pokrecem server!");
    }

    private void ucitajSerijaliziranuEvidenciju(String datEvid) {
        //TODO napravite sami
    }

}
