package org.foi.nwtis.matnovak.kvadrati;

public class NeparniKvadrati_5 {
	
	public static Ispisivac_1 kreirajIspisivac_1(int odBroja, int doBroja)
	{
		int ostatak = (int) (System.currentTimeMillis() % 3);
		switch(ostatak){
			case 0:
				return new NeparniKvadrati_3(odBroja,doBroja);
			case 1:
				return new NeparniKvadrati_4(odBroja,doBroja);
			case 2:
				return new KolikoJeSati();
			default: 
				return null;
		}
	}
}