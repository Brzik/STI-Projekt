package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Třída představuje soubor akcií pro jednu firmu za různé dny.
 * Zároveň ale třída obsahuje odkazy na všechny firmy.
 * 
 * @author Jan Brzobohatý
 */
class Firma {
    
    //zkratka firmy
    private String zkratka;
    
    //seznam akcii této firmy k různým datům (klíč je datum)
    private HashMap<Date,Akcie> akcie;
    
    //dlouhodobý průměr firmy
    private double dlouhodobyPrumer;
    
    /**
     * Vytvoří novou instanci firmy a vloží do ní první akcii 
     * @param akcie první akcie
     */
    public Firma(Akcie akcie){
        dlouhodobyPrumer = 0;
        pridatAkcii(akcie);
    }
    
    /**
     * Pridá akcii k této firmě.
     * @param akcie akcie, kterou chceme přidat
     */
    public void pridatAkcii(Akcie akcie) {
        this.akcie.put(akcie.getDatum(), akcie);
        vypocitejDlouhodobyPrumer();
    }
    
    public double getPropad3Dny(Date konec) {
      return 0.0;
    }

    /**
     * Vypočítá dlouhodobý průměr firmy.
     */
    private void vypocitejDlouhodobyPrumer(){
        //získání iteratoru pro hash mapu s akciemi
        Iterator iteretor = akcie.entrySet().iterator();
        
        //soucet vsech prumernych cen
        double soucet = 0;
        
        //pocet dnů pro které má firma akcie
        int dny = 0;
        
        while(iteretor.hasNext()){
            soucet += ((Akcie)iteretor.next()).getPrumernaCena();
            dny++;
        }
        
        dlouhodobyPrumer = soucet/dny;
    }
    
    /**
     * @return dlouhodobý průměr firmy
     */
    public double getDlouhodobyPrumer() {
      return dlouhodobyPrumer;
    }

    /**
     * Vrací data ve formě pole.
     * @param zacatek
     * @param konec
     * @return 
     */
    public ArrayList getData(Date zacatek, Date konec) {
//      Date dNow = new Date();
//      SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
//
//      System.out.println("Current Date: " + ft.format(dNow));

        return null;
    }

    /**
     * @return zkratka firmy
     */
    public String getZratka() {
      return zkratka;
    }
}
