/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Brzik
 */
public class ModelTest {
    
    public ModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of Model method, of class Model.
     */
    @Test
    public void testModel() {
        System.out.println("Model");
        Model instance = new Model();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aktualizovat method, of class Model.
     */
    @Test
    public void testAktualizovat() throws MalformedURLException, FileNotFoundException, IOException {
        System.out.println("aktualizovat");
        Model instance = new Model();
        assertEquals(instance.aktualizovat(), true);
    }

    /**
     * Test of getPrumernaCena method, of class Model.
     */
    @Test
    public void testGetPrumernaCena() {
        System.out.println("getPrumernaCena");
        Model instance = new Model();
        double expResult = 0.0;
        double result = instance.getPrumernaCena();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getData method, of class Model.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        Date zacatek = null;
        Date konec = null;
        Model instance = new Model();
        ArrayList expResult = null;
        ArrayList result = instance.getData(zacatek, konec);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirmu method, of class Model.
     */
    @Test
    public void testGetFirmu() {
        System.out.println("getFirmu");
        String nazev = "";
        Model instance = new Model();
        ArrayList expResult = null;
        ArrayList result = instance.getFirmu(nazev);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of jeSouborAktualni method, of class Model.
     */
    @Test
    public void testJeSouborAktualni() {
        System.out.println("jeSouborAktualni");
        Model instance = new Model();
        boolean expResult = false;
        boolean result = instance.jeSouborAktualni();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}