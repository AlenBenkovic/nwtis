package org.foi.nwtis.dkermek;

import org.foi.nwtis.dkermek.kvadrati.*;

public class Vjezba_02_1 {

	public static void main(String args[]) {
		if(args.length != 3) {
			System.out.println("Broj argumenta ne odgovara");	
			return;
		}
		
		int odBroja = Integer.parseInt(args[0]);
		int doBroja = Integer.parseInt(args[1]);

		int vrsta = Integer.parseInt(args[2]);
		Kvadrati kvad = null;
		
		switch(vrsta) {
		case 1:
			kvad = new Kvadrati(odBroja, doBroja);
			kvad.ispis();
			break;
		case 2:
			kvad = new NeparniKvadrati_1(odBroja, doBroja);
			kvad.ispis();
			break;
		case 3:
			kvad = new NeparniKvadrati_2(odBroja, doBroja);
			kvad.ispis();
			break;	
		case 4:
			kvad = new NeparniKvadrati_3(odBroja, doBroja);
//			kvad.ispisiPodatke();	ne radi jer nije metoda iz klase Kvadrati
			break;	
		case 5:
			Ispisivac_1 ispisivac = new NeparniKvadrati_3(odBroja, doBroja);
			ispisivac.ispisiPodatke();	
			break;	
		case 6:
			NeparniKvadrati_4 kvad4 = new NeparniKvadrati_4(odBroja, doBroja);
			kvad4.ispisiPodatkeLinijski();	
			break;	
		case 7:
			kvad = new NeparniKvadrati_4(odBroja, doBroja);
			kvad.ispis();	
			break;	
		case 8:
			Ispisivac_1 ispisivac1 = new NeparniKvadrati_4(odBroja, doBroja);
			ispisivac1.ispisiPodatke();	
			break;	
		case 9:
			Ispisivac_2 ispisivac2 = new NeparniKvadrati_4(odBroja, doBroja);
			ispisivac2.ispisiPodatkeLinijski();	
			break;	
		case 10:
			for(int i=0;i<100;i++) {
				Ispisivac_1 ispisivac3 = NeparniKvadrati_5.kreirajIspisivac_1(odBroja, doBroja);
				ispisivac3.ispisiPodatke();	
				System.out.println(ispisivac3.getClass());
				System.out.println();
				try {
					Thread.sleep(1000);
				} catch (Exception e) 
				{}
			}
			break;				
		default:
			System.out.println("Argumenti ne odgovaraju");
		}
			
	}
}
		
		