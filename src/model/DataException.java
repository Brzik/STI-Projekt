/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import javax.swing.JLabel;
import view.GUIAplikace;
import view.akcieGUI;

/**
 * DataException je výjimka tykající se jakékoli chyby ohledně 
 * aktualizace dat v aplikaci. 
 * 
 * @author Jan Brzobohatý
 */
public class DataException extends Exception{
    
    /**
     * @param hlaska hlaska, která má být uložena jako zpráva ve výjimce
     */
    public DataException(String hlaska){
        
     
        super("Error: " + hlaska);
       
    }
}
