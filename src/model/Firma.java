package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

class Firma {
    
    //seznam všech firem (klíč je zkratka firmy)
    private static HashMap<String,Firma> firmy;
    
    //nazev firmy
    private String nazev;
    
    //seznam akcii této firmy k různým datům (klíč je datum)
    private HashMap<Date,Akcie> akcie;

    /**
     * Tovární metoda slouží k přidávání akcií do databáze.
     * @param akcie konkrétní akcie ke kokrétnímu datu
     */
    public static void pridatAkcie(Akcie akcie) {
        if(firmy.containsKey(akcie.getZkratka())){
            firmy.get(akcie.getZkratka()).pridatAkcii(akcie);
        }else{
            firmy.put(akcie.getZkratka(), new Firma(akcie));
        }
    }
    
    /**
     * Vytvoří novou instanci firmy a vloží do ní první akcii 
     * @param akcie první akcie
     */
    private Firma(Akcie akcie){
        pridatAkcii(akcie);
    }
    
    /**
     * Pridá akcii k této firmě.
     * @param akcie akcie, kterou chceme přidat
     */
    private void pridatAkcii(Akcie akcie) {
        this.akcie.put(akcie.getDatum(), akcie);
    }
    
    public double getPropad3Dny(Date konec) {
      return 0.0;
    }

    //private double getPrumernaCena(Date zacatek, Date konec) {
//      Date dNow = new Date();
//      SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
//
//      System.out.println("Current Date: " + ft.format(dNow));
    //}

    /**
     * Vrací data ve formě pole.
     * @param zacatek
     * @param konec
     * @return 
     */
    public ArrayList getData(Date zacatek, Date konec) {
      return null;
      zkouska githabu
              
      druha zkouska githabu
    }

    /**
     * @return nazev firmy
     */
    public String getNazev() {
      return nazev;
    }
}