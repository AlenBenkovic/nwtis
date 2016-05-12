package org.foi.nwtis.matnovak;

import org.foi.nwtis.matnovak.kvadrati.*;

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
		Ispisivac_1 ispisivac1 = null;
		
		switch(vrsta) {
		case 0:
			kvad = new Kvadrati(odBroja, doBroja);
			kvad.ispis();
			break;
		case 1:
			NeparniKvadrati_1 npkvad = new NeparniKvadrati_1(odBroja, doBroja);
			npkvad.ispis();
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
			kvad.ispis();
			break;
		case 5:
			ispisivac1 = new NeparniKvadrati_3(odBroja, doBroja);
			//ispisivac1.ispis(); 
			ispisivac1.ispisiPodatke();
			break;
		case 6:
			NeparniKvadrati_4 npkvad4 = new NeparniKvadrati_4(odBroja, doBroja);
			npkvad4.ispisiPodatkeLinijski();
			break;
		case 7:
			kvad = new NeparniKvadrati_4(odBroja, doBroja);
			kvad.ispis();
			break;
		case 8:
			ispisivac1 = new NeparniKvadrati_4(odBroja, doBroja);
			ispisivac1.ispisiPodatke();
			break;
		case 9:
			Ispisivac_2 ispisivac2 = new NeparniKvadrati_4(odBroja, doBroja);
			ispisivac2.ispisiPodatke();
			break;
		case 10:
			for(int i=0;i<10;i++){
				ispisivac1 = NeparniKvadrati_5.kreirajIspisivac_1(odBroja, doBroja);
				ispisivac1.ispisiPodatke();
				System.out.println("Klasa je:"+ispisivac1.getClass());
				System.out.println();
			}
			break;
		default:
			System.out.println("Argumenti ne odgovaraju");
		}
			
	}
}
		
		