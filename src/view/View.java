/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.util.ArrayList;
import model.Model;

/**
 *
 * @author Michaela
 */
public class View {
    
    Model model=Model.getModel();
    akcieGUI akcie=new akcieGUI(model);
     
    
    //listenery pro tlacitka
    public void addIntervalListener(Controller.TlacitkoIntervalListener tlacitkoIntervalListener) {
        akcie.nastavListenerProCasUsek();
    }

    public void addAktualizaceListener(Controller.TlacitkoAktualizaceListener tlacitkoAktualizaceListener) {
        akcie.nastavListenerAktualizaceDat();
    }

    public void addGrafListener(Controller.TlacitkoGrafListener tlacitkoGrafListener) {
        akcie.nastavListenerProGraf();
    }

    //metoda vlozi data do tabulky
    public void pushDataToTable(ArrayList<Object[]> dataTabulka) {
        akcie.zobrazPlnouTabulku();
    }

    //metoda vypise chzbovou hlasku
    public void zobrazChybu(String message) {
        akcie.layoutProChyboveHlasky(null, akcie);
    }

    //metoda zobrazi graf vybranych firem
    public void zobrazGraf(Object[][] dataGraf) {
        
    }

    //vypne tlacitka, aby se na ne nedalo kliknout
    public void vypnoutTlacitka() {
        akcie.vypniTlacitka();
    }
    //vypraydni tabulku
    public void smazTabulku() {
        akcie.zobrazPrazdnouTabulku();
    }
    //zapne tlactika-da se na ne kliknout
    public void zapnoutTlacitka() {
        akcie.zapniTlacitka();
    }
    
    public void zobrazDatumAktualizace(String posledniDatumVSouboru, boolean aktualni) {
        
    }

  

    //zobrazi GUI
    public void setVisible() {
      akcieGUI ag=new akcieGUI(model);
    }
    
    //getry
    public int getZacatekRok(){
        return akcie.getZacatekRok();
    }
    public int getZacatekMesic(){
        return akcie.getZacatekMesic();
    }
    public int getZacatekDen(){
        return akcie.getZacatekDen();
    }
    public int getKonecRok(){
        return akcie.getKonecRok();
    }
    public int getKonecMesic(){
        return akcie.getKonecMesic();
    }
    public int getKonecDen(){
        return akcie.getKonecDen();
    }
      public ArrayList<String> getZaskrtnuteFirmy() {
        return null;
    }
    
    
    
    

    
    
}
