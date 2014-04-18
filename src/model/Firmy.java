/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

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
    
    /**
     * @param zacatek začátek období, pro které chceme data
     * @param konec konec období, pro které cheme data
     * @return seznam seznamů, kde první seznam představuje jednotlivé 
     * firmy (neuspořádané) a tedy každá firma je tvořena dalším seznamem, 
     * který je pevně uspořádaný a obsahuje jednotlivá data pro jednu firmu.
     * Seznam dat pro jednu firmu má následující indexování:
     * 
     * <table>
     * <tr><th>index</th><th>hodnota</th><th>typ</th></tr>
     * <tr><th>0</th><th>zkratka firmy</th><th>String</th></tr>
     * <tr><th>1</th><th>průměrná cena</th><th>double</th></tr>
     * <tr><th>2</th><th>průměrný objem</th><th>double</th></tr>
     * <tr><th>3</th><th>odchylka od min</th><th>double</th></tr>
     * <tr><th>4</th><th>odchylka od max</th><th>double</th></tr>
     * <tr><th>5</th><th>odchylka od dlouh. prům.</th><th>double</th></tr>
     * <tr><th>6</th><th>koupit</th><th>boolean</th></tr>
     * </table>
     */
    public static ArrayList<ArrayList> getData(Date zacatek, Date konec){
        
        //seznam dat pro všechny firmy (dvourozměrný seznam, kde jeden 
        //rozměr je firma a druhý jednotlivá data)
        ArrayList data = new ArrayList();
        
        Iterator iterator = firmy.entrySet().iterator();
        
        while(iterator.hasNext()){
            data.add(((Firma)iterator.next()).getData(zacatek, konec));
        }
        
        nastavitNejvetsiPropad(data);
        
        return data;
    }
    
    /**
     * Metoda nastaví předanému dvourozměrnému seznamu na 6. položku 
     * druhého rozměru, boolean hodnotu. Respektive v případě, že má firma
     * největší propad ze všech firem za poslední 3 dny, tak je jí nastavena
     * položka "koupit" (6. položka) na true, jinak false.
     * 
     * @param seznamFirem seznam seznamů dat pro firmy
     */
    private static void nastavitNejvetsiPropad(ArrayList<ArrayList> seznamFirem){
        //iterator pro seznam firem
        Iterator iteratorSeznaFirem;
        
        //iterator pro seznam firem s nejvetším propadem
        Iterator iteratorMaxList;
        
        //propad iterované firmy
        double propad;
        
        //největší dočasný propad
        double max;
        
        //seznam dat pro iterovanou firmu
        ArrayList firma;
        
        //seznam firem, které mají největší propad
        ArrayList<ArrayList> maxList;
        
        maxList = new ArrayList();
        iteratorSeznaFirem = seznamFirem.iterator();
        
        //nastavení první firmy jako té s největším propadem
        firma = (ArrayList)iteratorSeznaFirem.next();
        maxList.add(0, firma);
        
        //hledání firem s největším propadem
        while(iteratorSeznaFirem.hasNext()){            
            firma = (ArrayList)iteratorSeznaFirem.next();
            propad = (double)firma.get(6);
            
            //pokud se jedná opravdu o propad
            if(propad>0){
                max = (int)maxList.get(0).get(6);

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
        
        //nastavení všech firem na koupit=false
        while(iteratorSeznaFirem.hasNext()){            
            firma = (ArrayList)iteratorSeznaFirem.next();
            firma.set(6, false);
        }
        
        iteratorMaxList = maxList.iterator();
        
        //nastavení těch firem, které mají největší propad na koupit=true
        while(iteratorMaxList.hasNext()){            
            firma = (ArrayList)iteratorMaxList.next();
            firma.set(6, true);
        }
    }
    
    /**
     * @param nazev název firmy, pro kterou chceme dlouhodobý průměr
     * @return dlouhodobý průměr zadané firmy
     * @throws DataException v případě, že firma pro vykreslení nebyla nalazena
     */
    public static double getDlouhodobyPrumer(String nazev) throws DataException{
        if(firmy.containsKey(nazev)){
            return firmy.get(nazev).getDlouhodobyPrumer();
        }else{
            throw new DataException("firma pro vykreslení nenalezena");
        }
    }
    
    /**
     * @param zacatek zacatek období, pro které chceme graf
     * @param konec konec období, pro které chceme graf
     * @param nazev název firmy, pro kterou chceme graf
     * @return graf (množinu uspořádaných dvojic), kde první člen (index=0) 
     * ve dvojici je datum jako textový řetězec ve formátu "dd. mm. yyyy" 
     * a druhý člen (index 1) je průmerná cena k tomuto datu. Množina je 
     * uspořádána chronologicky podle data.
     */
    public static ArrayList[] getGraf(Date zacatek, Date konec, String nazev) throws DataException{ 
        if(firmy.containsKey(nazev)){
            return firmy.get(nazev).getGraf(zacatek, konec);
        }else{
            throw new DataException("firma pro vykreslení nenalezena");
        }
    }
}
