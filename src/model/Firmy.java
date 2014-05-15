/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.joda.time.LocalDate;

/**
 * Třída představuje soubor všech firem.
 * 
 * @author Jan Brzobohatý
 */
class Firmy {

    /**
     * Konstanta určuje, o kolik se může lišit průměrný propad firmy od 
     * největšího propadu firem, aby byl pořád navržen na koupi.
    */
    private final static double TOLERANCE_PROPADU = 10;
    
    private Firmy(){}
    
    //seznam všech firem (klíč je zkratka firmy)
    private static HashMap<String,Firma> firmy;
    
    /**
     * Metoda slouží k přidávání akcií do databáze.
     * @param akcie konkrétní akcie ke kokrétnímu datu
     * 
     * @throws FatalException když data nejsou validní
     */
    public static void pridatAkcii(Akcie akcie) throws FatalException{
        if(akcie==null){
            throw new FatalException("Data nejsou validní (akcie=null). (class Firmy->pridatAkcii)");
        }
        if(firmy==null){
            firmy = new HashMap();
        }
        if(firmy.containsKey(akcie.getZkratka())){
            //pokud firma už existuje, tak ji pouze pridame akcii
            firmy.get(akcie.getZkratka()).pridatAkcii(akcie);
        }else{
            //pokud firma ještě neexistuje, tak ji vytvoříme
            firmy.put(akcie.getZkratka(), new Firma(akcie));
        }
    }
    
    /**
     * @param zacatek začátek období, pro které chceme data
     * @param konec konec období, pro které cheme data
     * @return seznam polí, kde seznam představuje jednotlivé 
     * firmy (neuspořádané) a každá firma je tvořena polem dat (size=7), 
     * který je pevně uspořádaný a obsahuje jednotlivá data pro jednu firmu.
     * Pole dat pro jednu firmu má následující indexování:
     * 
     * <table>
     * <tr><th>index</th><th>hodnota</th><th>typ</th></tr>
     * <tr><th>0</th><th>zkratka firmy</th><th>String</th></tr>
     * <tr><th>1</th><th>průměrná cena</th><th>double</th></tr>
     * <tr><th>2</th><th>průměrný objem</th><th>double</th></tr>
     * <tr><th>3</th><th>odchylka od min</th><th>double</th></tr>
     * <tr><th>4</th><th>odchylka od max</th><th>double</th></tr>
     * <tr><th>5</th><th>odchylka od dlouh. prům.</th><th>double</th></tr>
     * <tr><th>6</th><th>koupit</th><th>String</th></tr>
     * </table>
     * 
     * @throws DatumException pokud datum zacatek je chronologicky az po datumu 
     * konec nebo pokud je jeden z datumů null
     * @throws DataException pokud pro dané období nejsou žádná data.
     * @throws FatalException pokud nejsou k dispozici žádná data.
     */
    public static ArrayList<Object[]> getData(LocalDate zacatek, LocalDate konec) throws DataException, DatumException, FatalException{
        
        if(zacatek==null||konec==null){
            throw new DatumException("Neplatný časový interval (datum=null). (class Firmy->getData)");
        }
        if(zacatek.isAfter(konec)){
            throw new DatumException("Neplatný časový interval (konec<zacatek). (class Firmy->getData)");
        }
        
        //seznam dat pro všechny firmy (seznam firem)
        ArrayList<Object[]> data = new ArrayList();
        
        if(firmy==null){
            throw new FatalException("Nejsou k dispozici žádná data. (class Firmy->getData)");
        }
        
        Iterator iterator = firmy.keySet().iterator();
        
        while(iterator.hasNext()){
            try{
                data.add(firmy.get((String)iterator.next()).getData(zacatek, konec));
            }catch(DataException ex){
                continue;
            }
        }
        
        if(data.isEmpty()){
            throw new DataException("Pro dané období nejsou žádná data. (class Firmy->getData)");
        }
        
        nastavitNejvetsiPropad(data);
        
        return data;
    }
    
    /**
     * Metoda nastaví předanému seznamu firem pro každou firmu v poli dat na 
     * indexu 6 hodnotu "koupit" v případě, že je výhodná ke koupi. 
     * Respektive v případě, že má firma
     * největší propad ze všech firem za poslední 3 dny, tak je jí nastavena
     * položka "koupit" (6. položka) na "koupit", jinak "".
     * 
     * @param seznamFirem seznam polí dat pro firmy
     */
    private static void nastavitNejvetsiPropad(ArrayList<Object[]> seznamFirem){
        //iterator pro seznam firem
        Iterator iteratorSeznaFirem;
        
        //iterator pro seznam firem s nejvetším propadem
        Iterator iteratorMaxList;
        
        //propad iterované firmy
        double propad;
        
        //největší dočasný propad
        double max;
        
        //seznam dat pro iterovanou firmu
        Object[] firma;
        
        //seznam firem, které mají největší propad
        ArrayList<Object[]> maxList;
        
        maxList = new ArrayList();
        iteratorSeznaFirem = seznamFirem.iterator();
        
        //nastavení první firmy jako té s největším propadem
        firma = (Object[])iteratorSeznaFirem.next();
        propad = (double)firma[6];
        while(propad<0&&iteratorSeznaFirem.hasNext()){
            firma = (Object[])iteratorSeznaFirem.next();
            propad = (double)firma[6];
        }
        if(firma!=null&&propad>0){
            maxList.add(0, firma);
        }
        
        //hledání firem s největším propadem
        while(iteratorSeznaFirem.hasNext()){            
            firma = (Object[])iteratorSeznaFirem.next();
            propad = (double)firma[6];
            
            //pokud se jedná opravdu o propad
            if(propad>0){
                max = (double)maxList.get(0)[6];

                //pokud se propad firmy lisi pouze malo od nejvetsiho propadu, 
                //tak zaradime firmu mezi ty s nejvetsim propadem
                if(Math.abs(propad-max)<TOLERANCE_PROPADU){
                    maxList.add(firma);
                }
                //pokud je propad firmy vetsi nez aktuálně největší propad, 
                //tak nahradíme
                else if(propad > max){
                    maxList.clear();
                    maxList.add(0, firma);
                }
            }
        }
        
        //nastavení všech firem na koupit=""
        iteratorSeznaFirem = seznamFirem.iterator();
        while(iteratorSeznaFirem.hasNext()){            
            firma = (Object[])iteratorSeznaFirem.next();
            firma[6] = "";
        }
        
        iteratorMaxList = maxList.iterator();
        
        //nastavení těch firem, které mají největší propad na koupit="koupit"
        while(iteratorMaxList.hasNext()){            
            firma = (Object[])iteratorMaxList.next();
            firma[6] = "koupit";
        }
    }
    
    /**
     * @param nazev název firmy, pro kterou chceme dlouhodobý průměr
     * @return dlouhodobý průměr zadané firmy
     * @throws DataException v případě, že firma pro vykreslení nebyla nalazena 
     * nebo nazev=null nebo nazev=""
     * @throws FatalException v případě, že nejsou k dispozici žádná data.
     */
    public static double getDlouhodobyPrumer(String nazev) throws DataException, FatalException{
        if(firmy==null){
            throw new FatalException("Nejsou k dispozici ždáná data. (class Firmy->getDlouhodobyPrumer)");
        }
        
        if(nazev==null||nazev.isEmpty()){
            throw new DataException("Zadán null nebo prázdný řetězec. (class Firmy->getDlouhodobyPrumer)");
        }
        
        if(firmy.containsKey(nazev)){
            return firmy.get(nazev).getDlouhodobyPrumer();
        }else{
            throw new DataException("Firma pro vykreslení nenalezena. (class Firmy->getDlouhodobyPrumer)");
        }
    }
    
    /**
     * @param zacatek zacatek období, pro které chceme graf
     * @param konec konec období, pro které chceme graf
     * @param nazev název firmy, pro kterou chceme graf
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
     * @throws DataException pokud v daném období nejsou žádná data pro tuto 
     * firmu nebo nebyl nalezen žádný záznam s takovýmto názvem
     * @throws FatalException v případě, že nejsou k dispozici žádná data.
     */
    public static Object[][] getGraf(LocalDate zacatek, LocalDate konec, String nazev) throws DataException, DatumException, FatalException{ 
        if(firmy==null){
            throw new FatalException("Nejsou k dispozici žádná data. (class Firmy->getGraf)");
        }
        
        if(firmy.containsKey(nazev)){
            return firmy.get(nazev).getGraf(zacatek, konec);
        }else{
            throw new DataException("Firma pro vykreslení nenalezena. (class Firmy->getGraf)");
        }
    }
    
    /**
     * Metoda vymaže veškerá data v programu (ne ze souboru).
     */
    public static void clear(){
        firmy=null;
    }
}
