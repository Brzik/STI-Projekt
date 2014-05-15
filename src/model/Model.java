package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Třída představuje model dat a je hlavním a také jediným 
 * přístupovým bodem k datům. Kromě přístupu k datům také umožňuje 
 * jejich automatickou aktualizaci.
 * 
 * @author Jan Brzobohatý
 */
public class Model{
    
    //internetová adresa souboru s daty z burzy 
    private final String ADRESA = "http://www.euinvest.cz/generate/bcpp_data.csv";
    
    //soubor do kterého ukládáme stažená data a následně z něj čteme
    private File soubor;
    
    //instance této třídy
    protected static Model model;
    
    //poslední datum v souboru
    private LocalDate posledniDatum;
    
    //datum a čas, kdy byla provedena poslední aktualizace
    private DateTime datumAktualizace;

    /**
     * Konstruktor vytvoří instanci pouze instanci.
     */
    protected Model(){}
    
    /**
     * Tovární metoda vrací instanci modelu, který je pro celou 
     * aplikaci pouze jeden.
     * 
     * @return intanci modelu
     */
    public static Model getModel(){
        if(model == null){
            model = new Model();
        }
        return model;
    }

    /**
     * Metoda slouží k aktualizaci veškerých dat akcií.
     * @throws DataException když je vadné připojení k internetu, není 
     * dostupná stránka http://www.euinvest.cz/generate/bcpp_data.csv, došlo 
     * k chybě při stahování dat a nebo při zapisování do souboru, nelze najít 
     * soubor na disku pro čtení dat, nastala chyba při čtení dat ze souboru 
     * na disku
     * @throws FatalException pokud data nejsou validní
     */
    public final void aktualizovat() throws DataException, FatalException{
        try{
            stahnoutSoubor();
        }catch(DataException ex){
            clear();
            nacistSoubor();
            throw ex;
        }
    }
   
    /**
     * Stáhne soubor z adresy http://euinvest.cz/generate/bcpp_data.csv a uloží 
     * ho jako data.txt ve složce, kde byla aplikace spuštěna.
     * 
     * @throws DataException když je vadné připojení k internetu, není
     * dostupná stránka http://www.euinvest.cz/generate/bcpp_data.csv, došlo 
     * k chybě při stahování dat a nebo při zapisování do souboru
     */
    private void stahnoutSoubor() throws DataException{
         
         //Buffer pro čtení dat z netu.
         BufferedInputStream bis = null;
         
         //Buffer pro zapisování dat do souboru
         BufferedOutputStream bos = null;
         
         //URL adresa stránky
         URL url;
         
         //Připojení ke stránce
         URLConnection urlc;

         soubor = new File("data.txt");
         
         try{
            url = new URL(ADRESA);
            urlc = url.openConnection();
            
            //získání streamu z webu
            try{
                bis = new BufferedInputStream(urlc.getInputStream());
            }catch(UnknownHostException ex){
                //otestujeme zda se dá připojit na google a vyloučíme tím vadu připojení
                try{
                    URL url2 = new URL("https://www.google.cz/");
                    URLConnection urlc2 = url2.openConnection();
                    urlc2.getInputStream();
                }catch(UnknownHostException e){
                    throw new DataException("Nelze se připojit k internetu. (Model->stahnoutSoubor)");
                }
                throw new DataException("Stránka " + ADRESA +" není dostupná. (Model->stahnoutSoubor)");
            }
            
            bos = new BufferedOutputStream(new FileOutputStream(soubor));
            
            //zapisování do souboru
            int i;
            while ((i = bis.read()) != -1)
            {
               bos.write(i);
            }
         }catch(IOException ex){
                throw new DataException("Při stahování souboru nastala chyba. (Model->stahnoutSoubor)");
         }finally{
             try{
                if(bis!=null){
                    bis.close();
                }
                if(bos!=null){
                    bos.close();
                }
             }catch(IOException ex){
                throw new DataException("Při stahování souboru nastala chyba. (Model->stahnoutSoubor)");
             }
         }
    }

    /**
     * Meotoda načte data ze souboru na disku a uloží je do příslušných objektů.
     * 
     * @throws DataException když nelze najít soubor na disku pro čtení dat, 
     * nastala chyba při čtení dat ze souboru na disku
     * @throws FatalException pokud data nejsou validní.
     */
    private void nacistSoubor() throws DataException, FatalException{
        
        //parser 
        IParser parser = new Parser();
        
        //seznam akcií
        ArrayList<ArrayList> seznamAkcii;
        
        try{
            BufferedReader br = new BufferedReader(new FileReader(soubor));
            seznamAkcii = parser.parse(br);
        }catch (FileNotFoundException ex) {
            throw new DataException("Nelze najít soubor na disku pro čtení dat. (Model->nacistSoubor)");
        }catch (IOException ex) {
            throw new DataException("Nastala chyba při čtení dat ze souboru na disku. (Model->nacistSoubor)");
        }
        
        transformujNaObjekty(seznamAkcii);
        
        //uložení současného data a času
        ulozDatumAktualizace();
    }
    
    /**
     * Metoda uloží seznam akcií do příslušných objektů.
     * 
     * @param seznamAkcii seznam, který obsahuje jednotlivé akcie, 
     * které jsou reprezentovány dalším seznamem dat příslušné akcie 
     * 
     * @throws FatalException pokud data nejsou validní.
     */
    private void transformujNaObjekty(ArrayList<ArrayList> seznamAkcii) throws FatalException{
        //seznam dat pro jednu akcii v konkrétním datumu
        ArrayList seznamDat;
        
        //rozdíl počáteční a koncové denní ceny
        double propad;
        
        //prumerna cena za den
        double prumer;
        
        //odchylka průměrné ceny za den od maxima za den
        double odchylkaMax;
        
        //odchylka průměrné ceny za den od minima za den
        double odchylkaMin;
        
        Iterator iterator = seznamAkcii.iterator();
        
        //transformace arrayListu na objekty + výpočty
        while(iterator.hasNext()){  
            //jedna akcie
            seznamDat = (ArrayList)iterator.next();

            //rozdíl počáteční a koncové denní ceny
            propad = (double)seznamDat.get(2)-(double)seznamDat.get(3);
            
            //soucet koncove a pocatecni denní hodnoty vyděleno dvěma
            prumer = ((double)seznamDat.get(2)+(double)seznamDat.get(3))/2;
            
            //prumer - max
            odchylkaMax = Math.abs(prumer-(double)seznamDat.get(4));
                    
            //prumer - min
            odchylkaMin = Math.abs(prumer-(double)seznamDat.get(5));
                      
            //pokud se bude jednat o poslední akcii, tak v proměnné
            //posledniDatum zůstane uloženo poslední datum v souboru
            posledniDatum = (LocalDate)seznamDat.get(1);
            
            //ulozeni jedne akcie
            Firmy.pridatAkcii(new Akcie(odchylkaMax,
                                        odchylkaMin,
                                        prumer,
                                        propad,
                                        posledniDatum,
                                        (String)seznamDat.get(0),
                                        (int)seznamDat.get(6)));
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
    public ArrayList<Object[]> getDataTabulka(LocalDate zacatek, LocalDate konec) throws DataException, DatumException, FatalException {
        return Firmy.getData(zacatek, konec);
    }

    /**
     * @param zacatek zacatek období, pro které chceme graf
     * @param konec konec období, pro které chceme graf
     * @param nazev název firmy, pro kterou chceme graf
     * @return dvourozměrné pole reprezentující graf (množinu uspořádaných dvojic),
     * kde první souřadnice představuje jednotlivé uspořádané dvojice a druhá 
     * souřadnice představuje indexování v uspořádané dvojici. První člen (index=0) 
     * ve dvojici je datum veformátu Date 
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
    public Object[][] getDataGraf(LocalDate zacatek, LocalDate konec, String nazev) throws DataException, DatumException, FatalException{
        return Firmy.getGraf(zacatek, konec, nazev);
    }
    
    /**
     * @param nazev název firmy, pro kterou chceme dlouhodobý průměr
     * @return dlouhodobý průměr zadané firmy
     * @throws DataException v případě, že firma pro vykreslení nebyla 
     * nalazena nebo nazev=null nebo nazev=""
     * @throws FatalException v případě, že nejsou k dispozici žádná data.
     */
    public double getDlouhodobyPrumerFirmy(String nazev) throws DataException, FatalException{
        return Firmy.getDlouhodobyPrumer(nazev);
    }
    
    /**
     * Metoda vymaže veškerá data v programu (ne ze souboru).
     */
    private void clear(){
        Firmy.clear();
    }
    
    /**
     * @return poslední datum v souboru jako řetězec ve formátu "dd. mm. yyyy" 
     * (Poslední datum v souboru představuje datum, ke kterému 
     * byl soubor naposledy aktualizován.) 
     */
    public String getPosledniDatumVSouboru(){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd. MM. yyyy");
        return posledniDatum.toString(formatter);
    }
    
    /**
     * @return datum poslední aktualizace
     * @throws DataException pokud nastala chyba při zapisování do souboru
     */
    public DateTime getDatumAktualizace() throws DataException{
        if(datumAktualizace==null){
            File file = new File("aktualizace.txt");
            if (file.exists()){
                FileReader fr = null;
                try {
                    fr = new FileReader(file);
                    LineNumberReader ln = new LineNumberReader(fr);
                    while (ln.getLineNumber() == 0){
                        datumAktualizace = DateTime.parse(ln.readLine());
                    }
                }catch (IOException ex) {
                    throw new DataException("Nastala chyba při zapisování data aktualizace do souboru. (Model->getDatumAktualizace)");
                }finally{
                    try{
                        if(fr!=null){
                            fr.close();
                        }
                    }catch (IOException ex) {
                        throw new DataException("Nastala chyba při zapisování data aktualizace do souboru. (Model->getDatumAktualizace)");
                    }
                }
            }
        }
        return datumAktualizace;
    }
    
    /**
     * Uloží datum a čas poslední aktualizace do souboru.
     * @throws DataException pokud nastala chyba při zapisování dat do souboru.
     */
    private void ulozDatumAktualizace() throws DataException{
        //načte současný datum a čas
        datumAktualizace = new DateTime();
        
        //zapíše do souboru
        try {
            try (PrintWriter out = new PrintWriter("aktualizace.txt")) {
                out.println(datumAktualizace.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new DataException("Nastala chyba při zapisování dat do souboru. (Model->ulozDatumAktualizace)");
        }
    }
}