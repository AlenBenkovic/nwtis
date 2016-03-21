class Vjezba_01_2 {
    public static void main(String args[]){
        if(args.length != 1){
            System.out.println("Nema 1 argument");
            return;
        }
        int broj = Integer.parseInt(args[0]);
        Faktorijela f = new Faktorijela(broj);
        System.out.println("Za " + args[0] + " faktorijela - " + f.dajFaktorijelu());
    }
}