package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import model.DataException;
import model.DatumException;
import model.FatalException;
import model.Model;
import org.joda.time.LocalDate;
//import view.View;

/**
 *
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
        
        //načtení dat
        try{
            model.aktualizovat();
        }catch (DataException | FatalException ex) {
            view.zobrazChybu(ex.getMessage());
        }
        
        //přidání listenerů pro tlačítka
        view.addIntervalListener(new TlacitkoIntervalListener());
        view.addAktualizaceListener(new AktualizaceListener());
        view.addGrafListener(new GrafListener());
   }

   /**
    * Metoda zobrazí GUI. 
    */
   public void setVisible() {
       view.setVisible();
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
    */
   private void zobrazGraf(LocalDate zacatek, LocalDate konec, String nazevFirmy){
       try {
           view.zobrazGraf(model.getDataGraf(zacatek,konec,nazevFirmy));
       } catch (DataException | DatumException | FatalException ex) {
           view.zobrazChybu(ex.getMessage());
       }
   }
   
   //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   private void zobrazDatumAktualizace(){
       view.zobrazDatumAktualizace();
   }
   
   private class TlacitkoIntervalListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent stisk) {
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //dodelat validaci
            zacatekObdobi = new LocalDate(view.getZacatekRok(), view.getZacatekMesic(), view.getZacatekDen());
            konecObdobi = new LocalDate(view.getKonecRok(), view.getKonecMesic(), view.getKonecDen());
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
            
            firmy = view.getZaskrtnuteFirmy();
            iteratorFirem = firmy.iterator();
            
            //zobrazení jednoho grafu pro jednu firmu
            while(iteratorFirem.hasNext()){
                nazevFirmy = (String)iteratorFirem.next();
                zobrazGraf(zacatekObdobi, konecObdobi, nazevFirmy);
            }
        }
   }
}
