package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Timer;
import model.DataException;
import model.DatumException;
import model.FatalException;
import model.Model;
import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import view.GUI;

/**
 * @author Jan Brzobohatý
 */
public class Controller{
   
   Model model;
   GUI view;
   
   //začátek a konec období, pro které chceme data
   LocalDate zacatekObdobi;
   LocalDate konecObdobi;
   
   //časovač pro kontrolu aktualizace
   Timer timer;
   
   public Controller(Model model, GUI view) {
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
   public void setVisible(boolean zviditelnit) {
       view.setVisible(zviditelnit);
       aktualizovatData();
       
       //spustí se časovač, který bude každých 10 minut kontrolovat jestli je potřeba aktualizace
       timer = new Timer(600000,new CasovacAktualizaceListener());
       timer.start();
   }
   
   /**
    * Vloží data pro zadané období do GUI tabulky.
    * @param zacatek začátek obodobí
    * @param konec konec období
    */
   private void pushDataToTable(LocalDate zacatek, LocalDate konec){
       try {
           view.smazTabulku();
           view.pushDataToTable(model.getDataTabulka(zacatek, konec));
       }catch (DataException ex) {
           view.zobrazChybu(ex.getMessage());
       }catch(DatumException ex){
           view.zobrazChybu(ex.getMessage());
       }catch(FatalException ex){
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
           view.zobrazGraf(model.getDataGraf(zacatek,konec,nazevFirmy),dlouhodobyPrumer, nazevFirmy);
       }catch (DataException ex) {
           view.zobrazChybu(ex.getMessage());
       }catch(DatumException ex){
           view.zobrazChybu(ex.getMessage());
       }catch(FatalException ex){
           view.zobrazChybu(ex.getMessage());
       }
   }
   
   /**
    * Aktualizuje data v programu a zobrazí data v tabulce.
    */
   private void aktualizovatData(){
       view.vypnoutTlacitka();
       view.smazTabulku();
       view.zobrazChybu("Aktualizuji data ...");
       try {
           model.aktualizovat();
           view.zobrazChybu("Aktualizace proběhla v pořádku.");
       } catch (FatalException ex) {
           view.zobrazChybu(ex.getMessage());
       }catch (DataException ex) {
           view.zobrazChybu(ex.getMessage());
       }finally{
           view.zapnoutTlacitka();
           zobrazDatumAktualizace();
           if(!(zacatekObdobi==null||konecObdobi==null)){
               pushDataToTable(zacatekObdobi, konecObdobi);
           }
       }   
   }
   
   /*
    * Metoda zjistí, zda je poslední aktualizace opravdu aktuální k dnešnímu datu
    * a zobrazí poslední datum v souboru.
    */
   private void zobrazDatumAktualizace(){
       //zda je soubor aktualni
       boolean aktualni;
       
       aktualni = isAktualni();
       
       view.zobrazDatumAktualizace(model.getPosledniDatumVSouboru(),aktualni);
   }
   
   /*
    * Ověří, jestli jsou data aktuální. (Pokud ještě nebylo 20:15, tak data 
    * jsou aktuální, pokud byl soubor stažen dnes před 20:15 nebo včera po 20:15. 
    * V případě, že je po 20:15, tak musí být soubor stažen dnes po 20:15.)
   */
   private boolean isAktualni(){
       //zda je soubor aktualni
       boolean aktualni = false;
       
       //datum poslední aktualizace dat
       DateTime datumAktualizace = null;
       
       //současný datum a čas
       DateTime soucasny = new DateTime();
       
       try {
           datumAktualizace = model.getDatumAktualizace();
       } catch (DataException ex) {
           view.zobrazChybu(ex.getMessage());
       }
       
       if(datumAktualizace!=null){
           if(datumAktualizace.getMonthOfYear()==soucasny.getMonthOfYear() && datumAktualizace.getYear()==soucasny.getYear()){
              if(datumAktualizace.getDayOfMonth()==soucasny.getDayOfMonth()){
                  if(soucasny.getHourOfDay()<20||(soucasny.getHourOfDay()==20&&soucasny.getHourOfDay()<=15)){
                      if(datumAktualizace.getHourOfDay()<20||(datumAktualizace.getHourOfDay()==20&&datumAktualizace.getHourOfDay()<=15)){
                          aktualni=true;
                      }
                  }else{
                      if(datumAktualizace.getHourOfDay()>20||(datumAktualizace.getHourOfDay()==20&&datumAktualizace.getHourOfDay()>15)){
                          aktualni=true;
                      }
                  }
              }else if(datumAktualizace.getDayOfMonth()==(soucasny.minusDays(1).getDayOfMonth())){
                  if(soucasny.getHourOfDay()<20||(soucasny.getHourOfDay()==20&&soucasny.getHourOfDay()<=15)){
                      if(datumAktualizace.getHourOfDay()>20||(datumAktualizace.getHourOfDay()==20&&datumAktualizace.getHourOfDay()>15)){
                          aktualni=true;
                      }
                  }
              }  
           }
       }
       return aktualni;
   }
   
   /**
    * Listener pro stisknutí tlačítka pro potvrzení zadaného intervalu.
    * Při stisknutí načte daný interval, zvaliduje, uloží a zobrazí odpovídající data.
    */
   public class TlacitkoIntervalListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent stisk) {
            try{
                view.smazChyby();
                zacatekObdobi = new LocalDate(view.getZacatekRok(), view.getZacatekMesic(), view.getZacatekDen());
                konecObdobi = new LocalDate(view.getKonecRok(), view.getKonecMesic(), view.getKonecDen());
                pushDataToTable(zacatekObdobi, konecObdobi);
            }catch(IllegalFieldValueException ex){
                view.zobrazChybu("Špatně zadaný vstup: " + ex.getMessage());
            }
        }
   }
   
   /**
    * Listener pro stisknutí tláčítka pro zobrazaní grafů firem.
    * Při stisknutí zobrazí pro každou zaškrtnutou firmu jeden graf.
    */
   public class TlacitkoGrafListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent stisk) {
            //list obsahujicí názvy firem, které byly zaškrtnuty
            ArrayList<String> firmy;
            Iterator iteratorFirem;
            
            //název jedné firmy
            String nazevFirmy;
            
            //dlouhodobý průměr firmy
            double dlouhodobyPrumer;
            
            view.smazChyby();
            
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
                } catch (FatalException ex) {
                    view.zobrazChybu(ex.getMessage());
                    continue;
                }catch (DataException ex) {
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
   public class TlacitkoAktualizaceListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent stisk) {
            (new Thread(new AktualizaceThread())).start();
        }
   }
   
   /**
    * Nové vlákno pro aktualizaci.
    */
   public class AktualizaceThread implements Runnable {

        @Override
        public void run() {
            aktualizovatData();
        }
   }
   
   /**
    * Zkontroluje potřebu aktualizace a případně aktualizuje.
    */
   public class CasovacAktualizaceListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent tick) {
            if(!isAktualni()){
                (new Thread(new AktualizaceThread())).start();
                
            }
        }
   }
}
