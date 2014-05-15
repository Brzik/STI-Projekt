package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import org.joda.time.LocalDate;


/**
 * Třída představuje soubor akcií pro jednu firmu za různé dny.
 * 
 * @author Jan Brzobohatý
 */
class Firma {
    
    //zkratka firmy
    private String zkratka;
    
    //seznam akcii této firmy k různým datům (klíč je datum ve formátu "yyyy-MM-dd")
    private HashMap<String,Akcie> akcie;
    
    //dlouhodobý průměr firmy
    private double dlouhodobyPrumer;
    
    /**
     * Vytvoří novou instanci firmy a vloží do ní první akcii 
     * @param akcie první akcie (nesmí být null)
     * 
     * @throws FatalException když data nejsou validní
     */
    public Firma(Akcie akcie) throws FatalException{
        dlouhodobyPrumer = 0;
        zkratka = akcie.getZkratka();
        this.akcie = new HashMap();
        pridatAkcii(akcie);
    }
    
    /**
     * Pridá akcii k této firmě.
     * @param akcie akcie, kterou chceme přidat (nesmí být null)
     * 
     * @throws FatalException když data nejsou validní
     */
    public final void pridatAkcii(Akcie akcie) throws FatalException {
        if(akcie==null||this.akcie.containsKey(akcie.getDatum().toString())){
            throw new FatalException("Data nejsou validní. (class Firma->pridatAkcii)");
        }
        
        this.akcie.put(akcie.getDatum().toString(), akcie);
        
        vypocitejDlouhodobyPrumer();
    }

    /**
     * Vypočítá dlouhodobý průměr firmy.
     */
    private void vypocitejDlouhodobyPrumer(){
        //získání iteratoru pro hash mapu s akciemi
        Iterator iteretor = akcie.keySet().iterator();
        
        //soucet vsech prumernych cen
        double soucet = 0;
        
        //pocet dnů pro které má firma akcie
        int dny = 0;
        
        while(iteretor.hasNext()){
            soucet += akcie.get((String)iteretor.next()).getPrumernaCena();
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
     * 
     * @throws DataException v případě, že pro danou firmu nejsou 
     * v daném obodbí, žádné akcie
     * 
     * @throws DatumException pokud datum <b>zacatek</b> je chronologicky az po datumu <b>konec</b> 
     */
    public Object[] getData(LocalDate zacatek, LocalDate konec) throws DataException, DatumException{
        //vybraná data pro tuto firmu
        Object[] data = new Object[7];
        
        //akcie, které jsou v zadaném časovém intervalu
        ArrayList<Akcie> vybraneAkcie;
        
        //průměrná cena za dané časové období
        double prumernaCena;
        
        if(zacatek.isAfter(konec)){
            throw new DatumException("Neplatný časový interval (konec<zacatek). (class Firma->getData)");
        }
        
        vybraneAkcie = vyberAkcie(zacatek, konec);
        
        if(vybraneAkcie.isEmpty()){
            throw new DataException("V daném období nejsou pro firmu žádné akcie.");
        }
        
        prumernaCena = getPrumernaCena(vybraneAkcie);
        
        data[0] = zkratka;
        data[1] = prumernaCena;
        data[2] = getPrumernyObjem(vybraneAkcie);
        data[3] = getOdchylkaMin(vybraneAkcie);
        data[4] = getOdchylkaMax(vybraneAkcie);
        data[5] = getOdchylkaOdDlouhPrum(prumernaCena);
        data[6] = getPropad3Dny(konec);
        
        return data;
    }
    
    /**
     * @param konec konec časového intervalu
     * @return propad této akcie za poslední tři dny daného intervalu
     */
    private double getPropad3Dny(LocalDate konec){
        
        //3 akcie za poslední 3 dny
        ArrayList<Akcie> vybraneAkcie;
        
        //3 dny od konce časového intervalu
        LocalDate zacatek;
        
        zacatek = konec.minusDays(2);
        
        vybraneAkcie = vyberAkcie(zacatek, konec);
        
        //v případě, že v období nejsou žádné údaje
        if(vybraneAkcie.isEmpty()){
            return 0;
        }
        
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
        
        return suma/vybraneAkcie.size();
    }
    
    /**
     * Metoda vybere ze seznamu pouze ty akcie, které jsou v určeném časovém rozmezí.
     * 
     * @param zacatek začátek časového rozmezí
     * @param konec konec časového rozmnezí
     * @return seznam akcií v daném časovém rozmezí
     */
    private ArrayList<Akcie> vyberAkcie(LocalDate zacatek, LocalDate konec){
        
        //seznam vybraných akcií
        ArrayList<Akcie> vybraneAkcie = new ArrayList();
        
        for(LocalDate i=zacatek;i.isBefore(konec);i=i.plusDays(1)){
            if(akcie.containsKey(i.toString())){
                vybraneAkcie.add(akcie.get(i.toString()));
            }
        }
        
        //uložení posledního dne v intervalu
        if(akcie.containsKey(konec.toString())){
            vybraneAkcie.add(akcie.get(konec.toString()));
        }
        
        
        vybraneAkcie.trimToSize();
        
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
    
    /**
     * @param zacatek zacatek období, pro které chceme graf
     * @param konec konec období, pro které chceme graf
     * @return dvourozměrné pole reprezentující graf (množinu uspořádaných dvojic),
     * kde první souřadnice představuje jednotlivé uspořádané dvojice a druhá 
     * souřadnice představuje indexování v uspořádané dvojici. První člen (index=0) 
     * ve dvojici je datum ve formátu Date 
     * a druhý člen (index 1) je průmerná cena (double) k tomuto datu. Množina je 
     * uspořádána chronologicky podle data.
     * 
     * <h1>Konkrétní příklad:</h1>
     * <table>
     * <tr><th>1.rozměr/2.rozměr</th><th>0</th><th>1</th></tr>
     * <tr><th>0</th><th>24. 5. 2014</th><th>525.00</th></tr>
     * <tr><th>1</th><th>25. 5. 2014</th><th>475.25</th></tr>
     * <tr><th>2</th><th>27. 5. 2014</th><th>255.45</th></tr>
     * </table>
     * 
     * @throws DatumException pokud datum <b>zacatek</b> je chronologicky az po datumu <b>konec</b>
     * @throws DataException pokud v daném období nejsou žádná data pro tuto firmu
     */
    public Object[][] getGraf(LocalDate zacatek, LocalDate konec) throws DataException, DatumException{
        //seznam akcii poze v zadaném intevalu
        ArrayList vybraneAkcie;
        Iterator iteratorAkcii;
        
        //pole představující množinu uspořádaných dvojic
        Object[][] mnozinaDvojic;
        
        //akcie pro jeden den
        Akcie jednaAkcie;
        
        if(zacatek.isAfter(konec)){
            throw new DatumException("Neplatný časový interval (konec<zacatek). (class Firma->getGraf)");
        }
        
        vybraneAkcie = vyberAkcie(zacatek, konec);
        
        if(vybraneAkcie.isEmpty()){
            throw new DataException("V daném období nejsou pro firmu žádné akcie. (class Firma->getGraf)");
        }
        //chronologické uspořádání akcií podle data
        vybraneAkcie = vyberAkcie(zacatek, konec);
        Collections.sort(vybraneAkcie, new Comparator<Akcie>() {
            @Override
            public int compare(Akcie akcie1, Akcie akcie2) {
                if(akcie1.getDatum().isAfter(akcie2.getDatum())){
                    return 1;
                }else if(akcie1.getDatum().isBefore(akcie2.getDatum())){
                    return -1;
                }
                return 0;
            }
        });
        
        //oříznutí velikosti seznamu
        vybraneAkcie.trimToSize();
        
        iteratorAkcii = vybraneAkcie.iterator();
        mnozinaDvojic = new Object[vybraneAkcie.size()][2];
        for(int i=0;i<mnozinaDvojic.length;i++){
            jednaAkcie = (Akcie)iteratorAkcii.next();
            mnozinaDvojic[i][0] = jednaAkcie.getDatumToDate();
            mnozinaDvojic[i][1] = jednaAkcie.getPrumernaCena();
        }
        
        return mnozinaDvojic;
    }
}
