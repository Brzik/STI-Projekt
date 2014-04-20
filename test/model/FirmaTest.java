/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jan Brzobohatý
 */
public class FirmaTest {
    Firma firma;
    LocalDate datum;
    
    public FirmaTest() {
    }
    
    @Before
    public void setUp() throws FatalException {
       datum = new LocalDate();
       firma = new Firma(new Akcie(5, 2, 2, 7, datum, "firma", 8));
       firma.pridatAkcii(new Akcie(6, 5, 4, 2, datum.plusDays(1), "a", 8));
       firma.pridatAkcii(new Akcie(6, 5, 2, 2, datum.plusDays(2), "a", 4));
       firma.pridatAkcii(new Akcie(6, 5, 7, 2, datum.plusDays(3), "a", 5));
       firma.pridatAkcii(new Akcie(6, 5, 8, 2, datum.plusDays(5), "a", 8));
    }
    
    @After
    public void tearDown(){
    }

    @Test(expected = FatalException.class)
    public void testPridatAkcii() throws FatalException{
        firma.pridatAkcii(null);
    }
    
    @Test(expected = FatalException.class)
    public void testPridatAkcii1() throws FatalException{
        firma.pridatAkcii(new Akcie(0, 0, 0, 0, datum, "firma", 0));
    }
    
    @Test(expected = FatalException.class)
    public void testPridatAkcii2() throws FatalException{
        firma.pridatAkcii(new Akcie(6, 5, 4, 2, datum, "", 8));
    }
    
    @Test(expected = FatalException.class)
    public void testPridatAkcii3() throws FatalException{
        firma.pridatAkcii(new Akcie(6, 5, 4, 2, null, "a", 8));
    }
    
    @Test
    public void testPridatAkcii4() throws FatalException{
        firma.pridatAkcii(new Akcie(6, 5, 4, 2, datum.minusDays(5), "a", 8));
    }
    
    @Test(expected = FatalException.class)
    public void testPridatAkcii5() throws FatalException{
        firma.pridatAkcii(new Akcie(6, 5, 4, 2, datum, "a", 8));
        firma.pridatAkcii(new Akcie(6, 5, 4, 2, datum, "a", 8));
    }
    
    @Test
    public void testPridatAkcii6() throws FatalException{
        firma.pridatAkcii(new Akcie(Double.MAX_VALUE, Double.MAX_VALUE, 9999999999999999.0, Double.MAX_VALUE, datum.minusDays(10), "a", Integer.MAX_VALUE));
    }
    
    @Test
    public void testPridatAkcii7() throws FatalException{
        firma.pridatAkcii(new Akcie(Double.MAX_VALUE, Double.MAX_VALUE, 9999999999999999.0, Double.MIN_VALUE, datum.minusDays(10), "a", Integer.MAX_VALUE));
    }

    @Test
    public void testGetDlouhodobyPrumer() throws FatalException {
        firma.pridatAkcii(new Akcie(Double.MAX_VALUE, Double.MAX_VALUE, 999.0, Double.MIN_VALUE, datum.minusDays(9), "a", Integer.MAX_VALUE));
        assertEquals((2.0+4.0+2.0+7.0+8.0+999.0)/6, firma.getDlouhodobyPrumer(),0.0000001);
    }

    @Test
    public void testGetData() throws FatalException, DataException, DatumException{
        Object[] pole = new Object[7];
        pole[0] = "firma";
        pole[1] = 2.0; //prumerna cena
        pole[2] = 8.0; //prumerny objem
        pole[3] = 2.0; //odchylka od min
        pole[4] = 5.0; //odchylka od max
        pole[5] = Math.abs(firma.getDlouhodobyPrumer()-2); //odchylka od dlouhodobeho prumeru
        pole[6] = 7.0; //prumerny propad za 3 dny
        assertArrayEquals(pole , firma.getData(datum, datum));
    }
    
    @Test(expected = DatumException.class)
    public void testGetData1() throws FatalException, DataException, DatumException{
        firma.getData(datum, datum.minusDays(1));
    }
    
    @Test(expected = DataException.class)
    public void testGetData2() throws FatalException, DataException, DatumException{
        firma.getData(datum.minusDays(6), datum.minusDays(2));
    }
    
    @Test
    public void testGetData3() throws FatalException, DataException, DatumException{
        Object[] pole = new Object[7];
        pole[0] = "firma";
        pole[1] = 4.6; //prumerna cena
        pole[2] = 6.6; //prumerny objem
        pole[3] = 4.4; //odchylka od min
        pole[4] = 5.8; //odchylka od max
        pole[5] = Math.abs(firma.getDlouhodobyPrumer()-4.6); //odchylka od dlouhodobeho prumeru
        pole[6] = 0.0; //prumerny propad za 3 dny
        assertArrayEquals(pole , firma.getData(datum, datum.plusDays(10)));
    }
    
    @Test
    public void testGetData4() throws FatalException, DataException, DatumException{
        Object[] pole = new Object[7];
        pole[0] = "firma";
        pole[1] = (2.0+4.0+2.0+7.0+8.0+1.0+2.0)/7; //prumerna cena
        pole[2] = (8.0+8.0+4.0+5.0+8.0+1.0+2.0)/7; //prumerny objem
        pole[3] = (2.0+5.0+5.0+5.0+5.0)/7; //odchylka od min
        pole[4] = (5.0+6.0+6.0+6.0+6.0)/7; //odchylka od max
        pole[5] = Math.abs(((2.0+4.0+2.0+7.0+8.0+1.0+2.0)/7)-((2.0+4.0+2.0+7.0+8.0+1.0+2.0)/7)); //odchylka od dlouhodobeho prumeru
        pole[6] = 7.0; //prumerny propad za 3 dny
        firma.pridatAkcii(new Akcie(0, 0, 1, 9, datum.plusDays(8), "a", 1));
        firma.pridatAkcii(new Akcie(0, 0, 2, 5, datum.plusDays(10), "a", 2));
        assertArrayEquals(pole , firma.getData(datum, datum.plusDays(10)));
    }

    @Test
    public void testGetGraf() throws DataException, FatalException, DatumException {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd. MM. yyyy");
        Object[][] pole = new Object[5][2];
        pole[0][0] = datum.toString(formatter);
        pole[0][1] = 2.0;
        pole[1][0] = datum.plusDays(1).toString(formatter);
        pole[1][1] = 4.0;
        pole[2][0] = datum.plusDays(2).toString(formatter);
        pole[2][1] = 2.0;
        pole[3][0] = datum.plusDays(3).toString(formatter);
        pole[3][1] = 7.0;
        pole[4][0] = datum.plusDays(5).toString(formatter);
        pole[4][1] = 8.0;
        assertArrayEquals(pole , firma.getGraf(datum.minusDays(2), datum.plusDays(7)));
    }
    
    @Test
    public void testGetGraf2() throws DataException, FatalException, DatumException {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd. MM. yyyy");
        Object[][] pole = new Object[1][2];
        pole[0][0] = datum.toString(formatter);
        pole[0][1] = 2.0;
        assertArrayEquals(pole , firma.getGraf(datum, datum));
    }
    
    @Test(expected = DatumException.class)
    public void testGetGraf3() throws DataException, FatalException, DatumException {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd. MM. yyyy");
        Object[][] pole = new Object[0][2];
        assertArrayEquals(pole , firma.getGraf(datum, datum.minusDays(1)));
    }
    
    @Test(expected = DataException.class)
    public void testGetGraf4() throws DataException, FatalException, DatumException {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd. MM. yyyy");
        Object[][] pole = new Object[0][2];
        assertArrayEquals(pole , firma.getGraf(datum.minusDays(5), datum.minusDays(1)));
    }

}