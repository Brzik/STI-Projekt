package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Třída představuje soubor akcií pro jednu firmu za různé dny.
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
    
    //délka jednoho dne v milisekundách
    private final long JEDEN_DEN = 86400000;
    
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
     * Vrací data pro tuto firmu za dané časové období.
     * 
     * @param zacatek zacatek časového období
     * @param konec konec časového období
     * @return seznam s následujícím obsahem:
     * 
     * <table>
     * <tr><th>index</th><th>hodnota</th><th>typ</th></tr>
     * <tr><th>0</th><th>zkratka firmy</th><th>String</th></tr>
     * <tr><th>1</th><th>průměrná cena</th><th>double</th></tr>
     * <tr><th>2</th><th>průměrný objem</th><th>double</th></tr>
     * <tr><th>3</th><th>odchylka od min</th><th>double</th></tr>
     * <tr><th>4</th><th>odchylka od max</th><th>double</th></tr>
     * <tr><th>5</th><th>odchylka od dlouh. prům.</th><th>double</th></tr>
     * <tr><th>6</th><th>prům. propad za poslední 3 dny</th><th>double</th></tr>
     * </table>
     */
    public ArrayList getData(Date zacatek, Date konec) {
        //vybraná data pro tuto firmu
        ArrayList data = new ArrayList(7);
        
        //akcie, které jsou v zadaném časovém intervalu
        ArrayList<Akcie> vybraneAkcie;
        
        //průměrná cena za dané časové období
        double prumernaCena;
        
        vybraneAkcie = vyberAkcie(zacatek, konec);
        prumernaCena = getPrumernaCena(vybraneAkcie);
        
        data.add(0, zkratka);
        data.add(1, prumernaCena);
        data.add(2, getPrumernyObjem(vybraneAkcie));
        data.add(3, getOdchylkaMin(vybraneAkcie));
        data.add(4, getOdchylkaMax(vybraneAkcie));
        data.add(5, getOdchylkaOdDlouhPrum(prumernaCena));
        data.add(6, getPropad3Dny(konec));
        
        return data;
    }

    /**
     * @return zkratka firmy
     */
    public String getZratka() {
      return zkratka;
    }
    
    /**
     * @param konec konec časového intervalu
     * @return propad této akcie za poslední tři dny daného intervalu
     */
    private double getPropad3Dny(Date konec) {
        
        //3 akcie za poslední 3 dny
        ArrayList<Akcie> vybraneAkcie;
        
        //3 dny od konce časového intervalu
        Date zacatek = new Date();
        
        zacatek.setTime(konec.getTime()-(2*JEDEN_DEN));
        vybraneAkcie = vyberAkcie(zacatek, konec);
        
        return getPropad3Dny(vybraneAkcie);
    }
    
    /**
     * @param vybraneAkcie vsechny akcie v určitém období
     * @return průměrný propad za poslední 3 dny v určitém období
     */
    private double getPropad3Dny(ArrayList<Akcie> vybraneAkcie){
        
        //součet všech propadů cen akcií v časovém intervalu
        double suma;
        
        Iterator iterator;       
        
        iterator = vybraneAkcie.iterator();        
        suma=0;
        
        while(iterator.hasNext()){
            suma += ((Akcie)iterator.next()).getPropad();
        }
        
        return suma/3;
    }
    
    /**
     * Metoda vybere ze seznamu pouze ty akcie, které jsou v určeném časovém rozmezí.
     * 
     * @param zacatek začátek časového rozmezí
     * @param konec konec časového rozmnezí
     * @return seznam akcií v daném časovém rozmezí
     */
    private ArrayList<Akcie> vyberAkcie(Date zacatek, Date konec){
        
        //seznam vybraných akcií
        ArrayList<Akcie> vybraneAkcie = new ArrayList();
        
        for(Date i=zacatek;i.before(konec);i.setTime(i.getTime()+JEDEN_DEN)){
            if(akcie.containsKey(i)){
                vybraneAkcie.add(akcie.get(i)); 
            }
        }
        
        //uložení posledního dne v intervalu
        vybraneAkcie.add(akcie.get(konec));
        
        return vybraneAkcie;
    }
    
    /**
     * @param vybraneAkcie vsechny akcie v určitém období
     * @return průměrnou cenu akcie v určitém období
     */
    private double getPrumernaCena(ArrayList<Akcie> vybraneAkcie){
        
        //pocet dní v časovém intervalu
        int dnu;
        
        //součet všech průměrných cen akcií v časovém intervalu
        double suma;
        
        Iterator iterator;       
        
        iterator = vybraneAkcie.iterator();        
        dnu=vybraneAkcie.size();
        suma=0;
        
        while(iterator.hasNext()){
            suma += ((Akcie)iterator.next()).getPrumernaCena();
        }
        
        return suma/dnu;
    }
    
    /**
     * @param vybraneAkcie vsechny akcie v určitém období
     * @return průměrný objem za časové období
     */
    private double getPrumernyObjem(ArrayList<Akcie> vybraneAkcie){
        
        //pocet dní v časovém intervalu
        int dnu;
        
        //součet všech objemů akcií v časovém intervalu
        double suma;
        
        Iterator iterator;       
        
        iterator = vybraneAkcie.iterator();  
        dnu=vybraneAkcie.size();
        suma=0;
        
        while(iterator.hasNext()){
            suma += ((Akcie)iterator.next()).getObjem();
        }
        
        return suma/dnu;
    }
    
    /**
     * @param vybraneAkcie vsechny akcie v určitém období
     * @return průměrnou odchylku od min v určitém období
     */
    private double getOdchylkaMin(ArrayList<Akcie> vybraneAkcie){
        
        //pocet dní v časovém intervalu
        int dnu;
        
        //součet všech odchylek od min akcií v časovém intervalu
        double suma;
        
        Iterator iterator;       
        
        iterator = vybraneAkcie.iterator();        
        dnu=vybraneAkcie.size();
        suma=0;
        
        while(iterator.hasNext()){
            suma += ((Akcie)iterator.next()).getOdchylkaMin();
        }
        
        return suma/dnu;
    }
    
    /**
     * @param vybraneAkcie vsechny akcie v určitém období
     * @return průměrnou odchylku od max v určitém období
     */
    private double getOdchylkaMax(ArrayList<Akcie> vybraneAkcie){
        
        //pocet dní v časovém intervalu
        int dnu;
        
        //součet všech odchylek od max akcií v časovém intervalu
        double suma;
        
        Iterator iterator;       
        
        iterator = vybraneAkcie.iterator();        
        dnu=vybraneAkcie.size();
        suma=0;
        
        while(iterator.hasNext()){
            suma += ((Akcie)iterator.next()).getOdchylkaMax();
        }
        
        return suma/dnu;
    }
    
    /**
     * @param prumernaCena prumerná cena akcie
     * @return odchylku průměrné ceny akcie od dlouhodobého průměru akcie
     */
    private double getOdchylkaOdDlouhPrum(double prumernaCena){
        return Math.abs(dlouhodobyPrumer-prumernaCena);
    }
    
    
    private void getDatumy(){
                //      Date dNow = new Date();
                //      SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
                //
                //      System.out.println("Current Date: " + ft.format(dNow));

    }
}
