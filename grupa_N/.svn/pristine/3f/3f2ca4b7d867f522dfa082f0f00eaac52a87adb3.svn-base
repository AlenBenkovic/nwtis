package aplikacija;
import podrska.Faktorijela;

public class Vjezba_01_4 {
	public static void main(String args[]){
		
		if(args.length != 1)
		{
			System.out.println("Krivi broj argumenata!");
			return;
		}
		
		int broj = Integer.parseInt(args[0]);
		
		for(int i=0; i<=broj;i++){
			Faktorijela f = new Faktorijela(i);
			long rez = f.dajFaktorijelu();
			System.out.println(i + " ima faktorijelu "+rez);
		}
	}
} 