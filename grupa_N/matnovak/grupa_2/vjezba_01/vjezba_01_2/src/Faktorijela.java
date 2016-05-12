public class Faktorijela {
	private int broj;
	
	Faktorijela (int broj){
		this.broj = broj;
	}
	
	public long dajFaktorijelu(){
		long rezaltat = 1;
		
		if(broj == 0)
		{
			return 1;
		}
		else if(broj < 0) {
			return -1;
		}
		else {
			for(int i = 1; i<=broj; i++){
				rezaltat *= i;
			}
			return rezaltat;
		}
	}
	
}