/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * DataException je výjimka tykající se jakékoli chyby ohledně 
 * aktualizace dat v aplikaci. 
 * 
 * @author Jan Brzobohatý
 */
public class DataException extends Exception{  
    public DataException(String hlaska){     
        super("Error: " + hlaska); 
    }
}
