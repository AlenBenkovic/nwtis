public class Faktorijela {
	private int broj;
	
	Faktorijela (int broj){
		this.broj = broj;
	}
	
	public long dajFaktorijelu(){
		long faktorijela=1;
		
		for(int i=1;i<=this.broj;i++)
		{
			faktorijela*=i;
		}
		
		return faktorijela;
	}	
}