/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.swing.JLabel;
import view.GUIAplikace;
import view.akcieGUI;

/**
 * DatumException je výjimka tykající se jakékoli chyby ohledně časového
 * intervalu.
 *
 * @author Jan Brzobohatý
 */
public class DatumException extends Exception {
    
    akcieGUI error;

    /**
     * @param hlaska hlaska, která má být uložena jako zpráva ve výjimce
     */
    public DatumException(String hlaska) {
        
        error = new akcieGUI();
        error.setTitle("Error: " + hlaska);
//        super("Error: " + hlaska);
    }
}
