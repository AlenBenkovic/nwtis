package org.foi.nwtis.dkermek.kvadrati;

public class NeparniKvadrati_4 extends NeparniKvadrati_2 implements Ispisivac_2 {
	
	public NeparniKvadrati_4(int odBroja, int doBroja) {
		super(odBroja, doBroja);
	}
		
	public void ispisiPodatke() {
		ispis();
	}
	
	public void ispisiPodatkeLinijski() {
		int i=this.odBroja % 2 == 0 ? this.odBroja + 1 : this.odBroja;
		for(;i <= this.doBroja;i+=4) {
			System.out.printf("%3d * %3d = %3d\t", i, i, i*i);
		}
	}
}