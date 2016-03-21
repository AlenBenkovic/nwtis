class Faktorijela{
    protected int broj;
    Faktorijela(int broj){
        this.broj = broj;
    }
    
    long dajFaktorijelu(){
        long rezultat = 1;
        if (broj<0){
            rezultat= -1;
            System.out.println("Nema faktorijele za " + broj);
        } else {
            for(int i=1;i<=broj; i++){
                rezultat=rezultat * i;
            }
        }
        return rezultat;
    }
}