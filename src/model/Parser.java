package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

class Parser implements IParser{

    @Override
    public ArrayList<ArrayList> parse(BufferedReader br) throws IOException{
        //seznam vsech akcii
        ArrayList seznamAkcii = new ArrayList(2000);
        
        //seznam dat pro jednu akcii
        ArrayList seznamDat;

        //jeden radek ze souboru (jedna akcie)
        String radek;
        
        //nastavení regularniho vyrazu pro datum v souboru
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
        
        int i=0;
        while((radek = br.readLine())!=null){
            seznamDat = new ArrayList(7);
            
            //precteni jednoho radku (akcie) a rozparsovani radku na jednotliva data
            String[] radekSplit = radek.split(",");
            
            //vlozeni dat do seznamu
            seznamDat.add(0, radekSplit[0].replaceAll("\"",""));
            seznamDat.add(1, LocalDate.parse(radekSplit[1], formatter));
            seznamDat.add(2, Double.parseDouble(radekSplit[2]));
            seznamDat.add(3, Double.parseDouble(radekSplit[3]));
            seznamDat.add(4, Double.parseDouble(radekSplit[4]));
            seznamDat.add(5, Double.parseDouble(radekSplit[5]));
            seznamDat.add(6, Integer.parseInt(radekSplit[6]));
            
            //vlozeni dat pro tuto akcii do seznamu akcii
            seznamAkcii.add(i, seznamDat);
            i++;
        }
        
        return seznamAkcii;
    }
}