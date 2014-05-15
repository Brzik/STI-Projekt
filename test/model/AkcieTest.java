/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jan Brzobohatý
 */
public class AkcieTest {
    Akcie akcie;
    
    public AkcieTest() {
    }
    
    @Before
    public void setUp() {    
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetDatumToString() throws FatalException {
        LocalDate datum = new LocalDate();
        akcie = new Akcie(5, 4, 2, 1, datum, "firma", 4);
        System.out.println(akcie.getDatumToDate()+ " ..... " +datum.toString());
    }
}