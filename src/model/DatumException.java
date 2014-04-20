/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * DatumException je výjimka tykající se jakékoli chyby ohledně 
 * časového intervalu. 
 * 
 * @author Jan Brzobohatý
 */
public class DatumException extends Exception{
    
    /**
     * @param hlaska hlaska, která má být uložena jako zpráva ve výjimce
     */
    public DatumException(String hlaska){
        super("Error: " + hlaska);
    }
}
