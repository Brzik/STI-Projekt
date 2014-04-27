/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Model;
import model.FatalException;
import model.DataException;
import view.GUIAplikace;


/**
 *
 * @author Jan Brzobohatý
 */
public class AktualizaceListener implements ActionListener{
    
    
    Model model=Model.getModel();
    

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            model.aktualizovat();   //aktualizuje data
            System.out.println("aktualizuju data");
        } catch (DataException ex) {
            //musi se jeste otestovat, jestli to takhle bude fungovat
            DataException.class.getConstructors();
        } catch (FatalException ex) {
            FatalException.class.getConstructors();
        }
    }
    
    
    
}
