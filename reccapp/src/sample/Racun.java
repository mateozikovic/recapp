package sample;

public class Racun extends Plovilo{
    //varijable
    private double iznos = 30;
    private int duljina = Plovilo.duljinaPlovila;
    private double poMetru = 10;

    /* metoda obracunaj vraca iznos racuna. Za plovila do 5 metara vraca iznos od 30,
     *  a iznad toga dodaje 10 eura po svakom metru iznad 5
     */
    public double obracunaj(){
        if(this.duljina<=5){
            return iznos;
        }else {
            double iznosIznadPet = 0;
            for (int i = 0; i < this.duljina - 2; i++){
                iznosIznadPet = iznosIznadPet + poMetru;
            }
            return iznosIznadPet;
        }
    }
}
