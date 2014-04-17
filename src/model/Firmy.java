/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import java.util.HashMap;

/**
 * Třída představuje soubor všech firem.
 * 
 * @author Jan Brzobohatý
 */
public class Firmy {
    a
    private Firmy(){}
    
    //seznam všech firem (klíč je zkratka firmy)
    private static HashMap<String,Firma> firmy;
    
    /**
     * Metoda slouží k přidávání akcií do databáze.
     * @param akcie konkrétní akcie ke kokrétnímu datu
     */
    public static void pridatAkcii(Akcie akcie) {
        if(firmy.containsKey(akcie.getZkratka())){
            //pokud firma už existuje, tak ji pouze pridame akcii
            firmy.get(akcie.getZkratka()).pridatAkcii(akcie);
        }else{
            //pokud firma ještě neexistuje, tak ji vytvoříme
            firmy.put(akcie.getZkratka(), new Firma(akcie));
        }
    }
    
    public static void getData(Date zacatek, Date konec){
        
        
        
        //musi tam byt i zda se vyplati koupit
    }
}
