package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DataException;
import model.DatumException;
import model.FatalException;
import model.Model;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
//import view.View;

/**
 * @author Jan Brzobohatý
 */
public class Controller{
    
   Model model;
   View view;
   
   //začátek a konec období, pro které chceme data
   LocalDate zacatekObdobi;
   LocalDate konecObdobi;
    
   public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        
        //přidání listenerů pro tlačítka
        view.addIntervalListener(new TlacitkoIntervalListener());
        view.addAktualizaceListener(new TlacitkoAktualizaceListener());
        view.addGrafListener(new TlacitkoGrafListener());
   }

   /**
    * Metoda zobrazí GUI a aktualizuje data. 
    */
   public void setVisible() {
       view.setVisible();
       aktualizovatData();
   }
   
   /**
    * Vloží data pro zadané období do GUI tabulky.
    * @param zacatek začátek obodobí
    * @param konec konec období
    */
   private void pushDataToTable(LocalDate zacatek, LocalDate konec){
       try {
           view.pushDataToTable(model.getDataTabulka(zacatek, konec));
       } catch (DataException | DatumException | FatalException ex) {
           view.zobrazChybu(ex.getMessage());
       }
   }
   
   /**
    * Zobrazí graf pro dané obodobí a danou firmu.
    * @param zacatek začátek období
    * @param konec konec období
    * @param nazevFirmy název firmy
    * @param dlouhodobyPrumer dlouhodobý průměr firmy
    */
   private void zobrazGraf(LocalDate zacatek, LocalDate konec, String nazevFirmy, double dlouhodobyPrumer){
       try {
           view.zobrazGraf(model.getDataGraf(zacatek,konec,nazevFirmy));
       } catch (DataException | DatumException | FatalException ex) {
           view.zobrazChybu(ex.getMessage());
       }
   }
   
   /**
    * Aktualizuje data v programu a zobrazí data v tabulce.
    */
   private void aktualizovatData(){
       view.vypnoutTlacitka();
       view.zobrazChybu("Aktualizuji data ...");
       try {
           model.aktualizovat();
           if(!(zacatekObdobi==null||konecObdobi==null)){
               pushDataToTable(zacatekObdobi, konecObdobi);
               zobrazDatumAktualizace();
           }
           view.zobrazChybu("Aktualizace proběhla v pořádku.");
       } catch (DataException ex) {
           view.zobrazChybu(ex.getMessage());
       } catch (FatalException ex) {
           view.smazTabulku();
           view.zobrazChybu(ex.getMessage());
       }
       view.zapnoutTlacitka();
   }
   
   
   private void zobrazDatumAktualizace(){
       //zda je soubor aktualni
       boolean aktualni = true;
       
       //datum poslední aktualizace dat
       DateTime datumAktualizace = null;
       
       //současný datum a čas
       DateTime soucasny = new DateTime();
       
       try {
           datumAktualizace = model.getDatumAktualizace();
       } catch (DataException ex) {
           view.zobrazChybu(ex.getMessage());
       }
       
       if(datumAktualizace==null){
           aktualni=false;
       }else{
           if(datumAktualizace.getMonthOfYear()==soucasny.getMonthOfYear() && 
              datumAktualizace.getYear()==soucasny.getYear()){
              if(datumAktualizace.getDayOfMonth()==soucasny.getDayOfMonth()){
                  
              }  
           }else{
              aktualni=false;
           }
       }
       
       view.zobrazDatumAktualizace(model.getPosledniDatumVSouboru(),aktualni);
   }
   
   /**
    * Listener pro stisknutí tlačítka pro potvrzení zadaného intervalu.
    * Při stisknutí načte daný interval, zvaliduje, uloží a zobrazí odpovídající data.
    */
   private class TlacitkoIntervalListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent stisk) {
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //dodelat validaci
            zacatekObdobi = new LocalDate(view.getZacatekRok(), view.getZacatekMesic(), view.getZacatekDen());
            konecObdobi = new LocalDate(view.getKonecRok(), view.getKonecMesic(), view.getKonecDen());
            pushDataToTable(zacatekObdobi, konecObdobi);
        }
   }
   
   /**
    * Listener pro stisknutí tláčítka pro zobrazaní grafů firem.
    * Při stisknutí zobrazí pro každou zaškrtnutou firmu jeden graf.
    */
   private class TlacitkoGrafListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent stisk) {
            //list obsahujicí názvy firem, které byly zaškrtnuty
            ArrayList<String> firmy;
            Iterator iteratorFirem;
            
            //název jedné firmy
            String nazevFirmy;
            
            //dlouhodobý průměr firmy
            double dlouhodobyPrumer;
            
            firmy = view.getZaskrtnuteFirmy();
            
            if(firmy.isEmpty()){
                view.zobrazChybu("Nejsou vybrány žádné firmy pro vykreslení.");
                return;
            }
            
            iteratorFirem = firmy.iterator();
            
            //zobrazení jednoho grafu pro jednu firmu
            while(iteratorFirem.hasNext()){
                nazevFirmy = (String)iteratorFirem.next();
                try {
                    dlouhodobyPrumer = model.getDlouhodobyPrumerFirmy(nazevFirmy);
                } catch (DataException | FatalException ex) {
                    view.zobrazChybu(ex.getMessage());
                    continue;
                }
                zobrazGraf(zacatekObdobi, konecObdobi, nazevFirmy, dlouhodobyPrumer);
            }
        }
   }
   
   /**
    * Listener pro stisknutí tlačítka pro aktualizaci dat.
    * Při stisknutí se aktualizují a zobrazí data.
    */
   private class TlacitkoAktualizaceListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent stisk) {
            aktualizovatData();
        }
   }
}
