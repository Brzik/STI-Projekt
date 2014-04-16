package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

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
    
    //
    private double prumernaCena;

    //udelat singleton
    public Model(){
        aktualizovat();
    }

    public boolean aktualizovat(){
        try {
            stahnoutSoubor();
            nacistSoubor();
        } catch (DataException ex) {
            System.err.println(ex.getMessage());
        }
        return true;
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
                    throw new DataException("Nelze se připojit k internetu.");
                }
                throw new DataException("Stránka " + ADRESA +" není dostupná.");
            }
            
            bos = new BufferedOutputStream(new FileOutputStream(soubor));
            
            //zapisování do souboru
            int i;
            while ((i = bis.read()) != -1)
            {
               bos.write(i);
            }
         }catch(IOException ex){
                throw new DataException("Při stahování souboru nastala chyba.");
         }finally{
             try{
                if(bis!=null){
                    bis.close();
                }
                if(bos!=null){
                    bos.close();
                }
             }catch(IOException ex){
                throw new DataException("Při stahování souboru nastala chyba.");
             }
         }
    }

    /**
     * Meotoda načte data ze souboru na disku a uloží je do příslušných objektů.
     * 
     * @throws DataException když nelze najít soubor na disku pro čtení dat, 
     * nastala chyba při čtení dat ze souboru na disku nebo nastala chyba při 
     * parsování typu String na Date
     */
    private void nacistSoubor() throws DataException{
        
        //parser 
        IParser parser = new Parser();
        
        //seznam akcií
        ArrayList seznamAkcii;
        
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
        
        try{
            BufferedReader br = new BufferedReader(new FileReader(soubor));
            seznamAkcii = parser.parse(br);
        }catch (FileNotFoundException ex) {
            throw new DataException("Nelze najít soubor na disku pro čtení dat.");
        }catch (IOException ex) {
            throw new DataException("Nastala chyba při čtení dat ze souboru na disku.");
        }catch (ParseException ex) {
            throw new DataException("Nastala chyba při parsování typu String na Date");
        }
        
        //transformace arrayListu na objekty + výpočty
        while(seznamAkcii.iterator().hasNext()){    
            //jedna akcie
            seznamDat = (ArrayList)seznamAkcii.iterator().next();

            //rozdíl počáteční a koncové denní ceny
            propad = (double)seznamDat.get(2)-(double)seznamDat.get(3);
            
            //soucet koncove a pocatecni denní hodnoty vyděleno dvěma
            prumer = ((double)seznamDat.get(2)+(double)seznamDat.get(3))/2;
            
            //prumer - max
            odchylkaMax = Math.abs(prumer-(double)seznamDat.get(4));
                    
            //prumer - min
            odchylkaMin = Math.abs(prumer-(double)seznamDat.get(5));
            
            //ulozeni jedne akcie
            Firmy.pridatAkcii(new Akcie(odchylkaMax,
                                        odchylkaMin,
                                        prumer,
                                        propad,
                                        (Date)seznamDat.get(1),
                                        (String)seznamDat.get(0),
                                        (int)seznamDat.get(6)));
        }
    }

    public double getPrumernaCena() {
        return 0.0;
    }

    private void setPrumernaCena() {
        
    }

    public ArrayList getData(Date zacatek, Date konec) {
        return null;
    }

    public ArrayList getFirmu(String nazev) {
        return null;
    }

    public boolean jeSouborAktualni() {
        return false;
    }
}