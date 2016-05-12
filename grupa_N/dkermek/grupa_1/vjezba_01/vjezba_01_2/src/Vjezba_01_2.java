public class Vjezba_01_2  {
	public static void main(String args[]) {
		for(int i=0;i<args.length;i++) {
			Faktorijela f = new Faktorijela(Integer.parseInt(args[i]));
			System.out.println(f.dajFaktorijelu());
		}
	}	
}