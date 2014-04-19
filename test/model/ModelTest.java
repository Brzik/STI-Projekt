/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jan Brzobohatý
 */
public class ModelTest {
    
    public ModelTest() {
    }
    
    @Before
    public void setUp() {
        try {
            Model.getModel();
        } catch (DataException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetModel() throws Exception {
        assertFalse(false);
    }

    @Test
    public void testAktualizovat() throws Exception {
        assertFalse(false);
    }

    @Test
    public void testGetDataTabulka() {
        assertFalse(false);
    }

    @Test
    public void testGetDataGraf() throws Exception {
        assertFalse(false);
    }

    @Test
    public void testGetDlouhodobyPrumerFirmy() throws Exception {
        assertFalse(false);
    }

    @Test
    public void testJeSouborAktualni() {
        assertFalse(false);
    }
}