package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

class Parser implements IParser{

    @Override
    public ArrayList parse(BufferedReader br) throws IOException, ParseException{
        //seznam vsech akcii
        ArrayList seznamAkcii = new ArrayList(2000);
        
        //seznam dat pro jednu akcii
        ArrayList seznamDat;
        
        //jeden radek ze souboru (jedna akcie)
        String radek;
        
        //nastavení regularniho vyrazu pro datum v souboru
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        
        int i=0;
        while((radek = br.readLine())!=null){
            seznamDat = new ArrayList(7);
            
            //precteni jednoho radku (akcie) a rozparsovani radku na jednotliva data
            String[] radekSplit = new String[7];
            radekSplit = radek.split(",");
            
            //vlozeni dat do seznamu
            seznamDat.add(0, radekSplit[0].replaceAll("\"",""));
            seznamDat.add(1, sdf.parse(radekSplit[1]));
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