/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.swing.JLabel;

/**
 * FatalException je výjimka tykající se jakékoli chyby ohledně 
 * aktualizace dat v aplikaci takové, že program nesmí pokračovat,
 * dokud se nepokusí o novou aktualizaci dat. 
 * 
 * @author Jan Brzobohatý
 */
public class FatalException extends Exception{
    
    JLabel error;
    
    /**
     * @param hlaska hlaska, která má být uložena jako zpráva ve výjimce
     */
    public FatalException(String hlaska){
        
        error = new JLabel("Error: " + hlaska);
//        super("FatalError: " + hlaska);
    }
}
