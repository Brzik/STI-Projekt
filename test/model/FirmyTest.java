/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
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
public class FirmyTest {
    LocalDate datum;
    
    public FirmyTest() {
    }
    
    @Before
    public void setUp() {
        datum = new LocalDate();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testPridatAkcii() throws FatalException{
        Firmy.clear();
        Firmy.pridatAkcii(new Akcie(2, 5, 5, 8, datum, "firma", 8));
    }
    
    @Test(expected = FatalException.class)
    public void testPridatAkcii1() throws FatalException{
        Firmy.clear();
        Firmy.pridatAkcii(new Akcie(0, 0, 0, 0, datum, "firma", 0));
    }
    
    @Test(expected = FatalException.class)
    public void testPridatAkcii2() throws FatalException{
        Firmy.clear();
        Firmy.pridatAkcii(null);
    }

    @Test
    public void testPridatAkcii3() throws FatalException{
        Firmy.clear();
        Firmy.pridatAkcii(new Akcie(2, 5, 5, 8, datum, "firma", 8));
        Firmy.pridatAkcii(new Akcie(2, 5, 5, 8, datum.minusDays(1), "firma", 8));
    }
    
    @Test(expected = FatalException.class)
    public void testPridatAkcii4() throws FatalException{
        Firmy.clear();
        Firmy.pridatAkcii(new Akcie(2, 5, 5, 8, datum, "firma", 8));
        Firmy.pridatAkcii(new Akcie(2, 5, 5, 8, datum, "firma", 8));
    }
    
    @Test(expected = DatumException.class)
    public void testGetData() throws FatalException, DataException, DatumException{
        Firmy.getData(datum, datum.minusDays(2));
    }
    
    @Test(expected = FatalException.class)
    public void testGetData2() throws FatalException, DataException, DatumException{
        Firmy.clear();
        Firmy.getData(datum, datum.plusDays(2));
    }
    
    @Test(expected = DatumException.class)
    public void testGetData3() throws FatalException, DataException, DatumException{
        Firmy.getData(datum, null);
    }
    
    @Test(expected = DatumException.class)
    public void testGetData4() throws FatalException, DataException, DatumException{
        Firmy.getData(null, datum);
    }

    @Test
    public void testGetData5() throws FatalException, DataException, DatumException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        ArrayList list = new ArrayList();
        
        Object[] data = new Object[7];
        Object[] data1 = new Object[7];
        Object[] data2 = new Object[7];
        
        data[0] = (String)akcie2.getZkratka(); //zkratka
        data[1] = (double)akcie2.getPrumernaCena(); //průměrná cena
        data[2] = (double)akcie2.getObjem(); //průměrný objem
        data[3] = (double)akcie2.getOdchylkaMin(); //odchylka od min
        data[4] = (double)akcie2.getOdchylkaMax(); //odchylka od max
        data[5] = (double)Math.abs(((4+2)/2)-akcie2.getPrumernaCena()); //odchylka od dlouhodobeho prumeru
        data[6] = (boolean)false; //koupit?
        list.add(data);
        data2[0] = (String)akcie3.getZkratka(); //zkratka
        data2[1] = (double)akcie3.getPrumernaCena(); //průměrná cena
        data2[2] = (double)akcie3.getObjem(); //průměrný objem
        data2[3] = (double)akcie3.getOdchylkaMin(); //odchylka od min
        data2[4] = (double)akcie3.getOdchylkaMax(); //odchylka od max
        data2[5] = (double)Math.abs(((7+1)/2)-akcie3.getPrumernaCena()); //odchylka od dlouhodobeho prumeru
        data2[6] = (boolean)true; //koupit?
        list.add(data2);
        data1[0] = (String)akcie1.getZkratka(); //zkratka
        data1[1] = (double)akcie1.getPrumernaCena(); //průměrná cena
        data1[2] = (double)akcie1.getObjem(); //průměrný objem
        data1[3] = (double)akcie1.getOdchylkaMin(); //odchylka od min
        data1[4] = (double)akcie1.getOdchylkaMax(); //odchylka od max
        data1[5] = (double)Math.abs(8-akcie1.getPrumernaCena()); //odchylka od dlouhodobeho prumeru
        data1[6] = (boolean)false; //koupit?
        list.add(data1);

        assertArrayEquals(list.toArray() , Firmy.getData(datum, datum).toArray()); 
    }
    
    @Test
    public void testGetData6() throws FatalException, DataException, DatumException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, -300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, -100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, -200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, -400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, -500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        ArrayList list = new ArrayList();
        
        Object[] data = new Object[7];
        Object[] data1 = new Object[7];
        Object[] data2 = new Object[7];
        
        data[0] = (String)akcie2.getZkratka(); //zkratka
        data[1] = (double)akcie2.getPrumernaCena(); //průměrná cena
        data[2] = (double)akcie2.getObjem(); //průměrný objem
        data[3] = (double)akcie2.getOdchylkaMin(); //odchylka od min
        data[4] = (double)akcie2.getOdchylkaMax(); //odchylka od max
        data[5] = (double)Math.abs(((4+2)/2)-akcie2.getPrumernaCena()); //odchylka od dlouhodobeho prumeru
        data[6] = (boolean)false; //koupit?
        list.add(data);
        data2[0] = (String)akcie3.getZkratka(); //zkratka
        data2[1] = (double)akcie3.getPrumernaCena(); //průměrná cena
        data2[2] = (double)akcie3.getObjem(); //průměrný objem
        data2[3] = (double)akcie3.getOdchylkaMin(); //odchylka od min
        data2[4] = (double)akcie3.getOdchylkaMax(); //odchylka od max
        data2[5] = (double)Math.abs(((7+1)/2)-akcie3.getPrumernaCena()); //odchylka od dlouhodobeho prumeru
        data2[6] = (boolean)false; //koupit?
        list.add(data2);
        data1[0] = (String)akcie1.getZkratka(); //zkratka
        data1[1] = (double)akcie1.getPrumernaCena(); //průměrná cena
        data1[2] = (double)akcie1.getObjem(); //průměrný objem
        data1[3] = (double)akcie1.getOdchylkaMin(); //odchylka od min
        data1[4] = (double)akcie1.getOdchylkaMax(); //odchylka od max
        data1[5] = (double)Math.abs(8-akcie1.getPrumernaCena()); //odchylka od dlouhodobeho prumeru
        data1[6] = (boolean)false; //koupit?
        list.add(data1);

        assertArrayEquals(list.toArray() , Firmy.getData(datum, datum).toArray()); 
    }
    
    @Test
    public void testGetData7() throws FatalException, DataException, DatumException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        ArrayList list = new ArrayList();
        
        Object[] data = new Object[7];
        Object[] data1 = new Object[7];
        Object[] data2 = new Object[7];
        
        data[0] = (String)akcie2.getZkratka(); //zkratka
        data[1] = (double)akcie2.getPrumernaCena(); //průměrná cena
        data[2] = (double)akcie2.getObjem(); //průměrný objem
        data[3] = (double)akcie2.getOdchylkaMin(); //odchylka od min
        data[4] = (double)akcie2.getOdchylkaMax(); //odchylka od max
        data[5] = (double)Math.abs(((4+2)/2)-akcie2.getPrumernaCena()); //odchylka od dlouhodobeho prumeru
        data[6] = (boolean)false; //koupit?
        list.add(data);
        data2[0] = (String)akcie3.getZkratka(); //zkratka
        data2[1] = (double)akcie3.getPrumernaCena(); //průměrná cena
        data2[2] = (double)akcie3.getObjem(); //průměrný objem
        data2[3] = (double)akcie3.getOdchylkaMin(); //odchylka od min
        data2[4] = (double)akcie3.getOdchylkaMax(); //odchylka od max
        data2[5] = (double)Math.abs(((7+1)/2)-akcie3.getPrumernaCena()); //odchylka od dlouhodobeho prumeru
        data2[6] = (boolean)false; //koupit?
        list.add(data2);
        data1[0] = (String)akcie1.getZkratka(); //zkratka
        data1[1] = (double)akcie1.getPrumernaCena(); //průměrná cena
        data1[2] = (double)akcie1.getObjem(); //průměrný objem
        data1[3] = (double)akcie1.getOdchylkaMin(); //odchylka od min
        data1[4] = (double)akcie1.getOdchylkaMax(); //odchylka od max
        data1[5] = (double)Math.abs(8-akcie1.getPrumernaCena()); //odchylka od dlouhodobeho prumeru
        data1[6] = (boolean)false; //koupit?
        list.add(data1);

        assertArrayEquals(list.toArray() , Firmy.getData(datum, datum.plusDays(5)).toArray()); 
    }
    
    @Test
    public void testGetData8() throws FatalException, DataException, DatumException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        ArrayList list = new ArrayList();
        
        Object[] data = new Object[7];
        Object[] data1 = new Object[7];
        Object[] data2 = new Object[7];
        
        data[0] = (String)akcie2.getZkratka(); //zkratka
        data[1] = (double)akcie2.getPrumernaCena(); //průměrná cena
        data[2] = (double)akcie2.getObjem(); //průměrný objem
        data[3] = (double)akcie2.getOdchylkaMin(); //odchylka od min
        data[4] = (double)akcie2.getOdchylkaMax(); //odchylka od max
        data[5] = (double)Math.abs(((4+2)/2)-akcie2.getPrumernaCena()); //odchylka od dlouhodobeho prumeru
        data[6] = (boolean)false; //koupit?
        list.add(data);
        data2[0] = (String)akcie3.getZkratka(); //zkratka
        data2[1] = (double)akcie3.getPrumernaCena(); //průměrná cena
        data2[2] = (double)akcie3.getObjem(); //průměrný objem
        data2[3] = (double)akcie3.getOdchylkaMin(); //odchylka od min
        data2[4] = (double)akcie3.getOdchylkaMax(); //odchylka od max
        data2[5] = (double)Math.abs(((7+1)/2)-akcie3.getPrumernaCena()); //odchylka od dlouhodobeho prumeru
        data2[6] = (boolean)true; //koupit?
        list.add(data2);
        data1[0] = (String)akcie1.getZkratka(); //zkratka
        data1[1] = (double)akcie1.getPrumernaCena(); //průměrná cena
        data1[2] = (double)akcie1.getObjem(); //průměrný objem
        data1[3] = (double)akcie1.getOdchylkaMin(); //odchylka od min
        data1[4] = (double)akcie1.getOdchylkaMax(); //odchylka od max
        data1[5] = (double)Math.abs(8-akcie1.getPrumernaCena()); //odchylka od dlouhodobeho prumeru
        data1[6] = (boolean)false; //koupit?
        list.add(data1);

        assertArrayEquals(list.toArray() , Firmy.getData(datum, datum.plusDays(2)).toArray());
    }
    
    @Test
    public void testGetData9() throws FatalException, DataException, DatumException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        ArrayList list = new ArrayList();
        
        Object[] data = new Object[7];
        Object[] data1 = new Object[7];
        Object[] data2 = new Object[7];
        
        data[0] = (String)akcie2.getZkratka(); //zkratka
        data[1] = (double)(akcie2.getPrumernaCena()+akcie.getPrumernaCena())/2; //průměrná cena
        data[2] = (double)(akcie2.getObjem()+akcie.getObjem())/2; //průměrný objem
        data[3] = (double)(akcie2.getOdchylkaMin()+akcie.getOdchylkaMin())/2; //odchylka od min
        data[4] = (double)(akcie2.getOdchylkaMax()+akcie.getOdchylkaMax())/2; //odchylka od max
        data[5] = (double)Math.abs(((4+2)/2)-(double)data[1]); //odchylka od dlouhodobeho prumeru
        data[6] = (boolean)false; //koupit?
        list.add(data);
        data2[0] = (String)akcie3.getZkratka(); //zkratka
        data2[1] = (double)(akcie3.getPrumernaCena()+akcie4.getPrumernaCena())/2; //průměrná cena
        data2[2] = (double)(akcie3.getObjem()+akcie4.getObjem())/2; //průměrný objem
        data2[3] = (double)(akcie3.getOdchylkaMin()+akcie4.getOdchylkaMin())/2; //odchylka od min
        data2[4] = (double)(akcie3.getOdchylkaMax()+akcie4.getOdchylkaMax())/2; //odchylka od max
        data2[5] = (double)Math.abs(((7+1)/2)-(double)data2[1]); //odchylka od dlouhodobeho prumeru
        data2[6] = (boolean)true; //koupit?
        list.add(data2);
        data1[0] = (String)akcie1.getZkratka(); //zkratka
        data1[1] = (double)akcie1.getPrumernaCena(); //průměrná cena
        data1[2] = (double)akcie1.getObjem(); //průměrný objem
        data1[3] = (double)akcie1.getOdchylkaMin(); //odchylka od min
        data1[4] = (double)akcie1.getOdchylkaMax(); //odchylka od max
        data1[5] = (double)Math.abs(8-akcie1.getPrumernaCena()); //odchylka od dlouhodobeho prumeru
        data1[6] = (boolean)false; //koupit?
        list.add(data1);

        assertArrayEquals(list.toArray() , Firmy.getData(datum.minusDays(3), datum.plusDays(1)).toArray());
    }
    
    @Test
    public void testGetDlouhodobyPrumer() throws FatalException, DataException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        assertEquals((akcie.getPrumernaCena()+akcie2.getPrumernaCena())/2, Firmy.getDlouhodobyPrumer("firma"),0.000001);
        assertEquals((akcie1.getPrumernaCena()), Firmy.getDlouhodobyPrumer("firma1"),0.000001);
        assertEquals((akcie3.getPrumernaCena()+akcie4.getPrumernaCena())/2, Firmy.getDlouhodobyPrumer("firma2"),0.000001);
    }
    
    @Test(expected = DataException.class)
    public void testGetDlouhodobyPrumer1() throws FatalException, DataException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        assertEquals((akcie.getPrumernaCena()+akcie2.getPrumernaCena())/2, Firmy.getDlouhodobyPrumer("firma4"),0.000001);
    }
    
    @Test(expected = DataException.class)
    public void testGetDlouhodobyPrumer2() throws FatalException, DataException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        assertEquals((akcie.getPrumernaCena()+akcie2.getPrumernaCena())/2, Firmy.getDlouhodobyPrumer(""),0.000001);
    }
    
    @Test(expected = DataException.class)
    public void testGetDlouhodobyPrumer3() throws FatalException, DataException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        assertEquals((akcie.getPrumernaCena()+akcie2.getPrumernaCena())/2, Firmy.getDlouhodobyPrumer(null),0.000001);
    }
    
    @Test(expected = FatalException.class)
    public void testGetDlouhodobyPrumer4() throws FatalException, DataException{
        Firmy.clear();
        
        Firmy.getDlouhodobyPrumer("");
    }

    @Test
    public void testGetGraf() throws FatalException, DataException, DatumException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        Object[][] pole = new Object[2][2];
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd. MM. yyyy");
        pole[0][0] = datum.minusDays(1).toString(formatter);
        pole[0][1] = 2.0;
        pole[1][0] = datum.toString(formatter);
        pole[1][1] = 4.0;
        
        assertArrayEquals(pole , Firmy.getGraf(datum.minusDays(3),datum.plusDays(2),"firma"));
    }
    
    @Test
    public void testGetGraf1() throws FatalException, DataException, DatumException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        Object[][] pole = new Object[1][2];
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd. MM. yyyy");
        pole[0][0] = datum.toString(formatter);
        pole[0][1] = 4.0;
        
        assertArrayEquals(pole , Firmy.getGraf(datum,datum,"firma"));
    }
    
    @Test(expected = DatumException.class)
    public void testGetGraf2() throws FatalException, DataException, DatumException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        Object[][] pole = new Object[1][2];
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd. MM. yyyy");
        pole[0][0] = datum.toString(formatter);
        pole[0][1] = 4.0;
        
        assertArrayEquals(pole , Firmy.getGraf(datum,datum.minusDays(2),"firma"));
    }
    
    @Test(expected = DataException.class)
    public void testGetGraf3() throws FatalException, DataException, DatumException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        Object[][] pole = new Object[1][2];
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd. MM. yyyy");
        pole[0][0] = datum.toString(formatter);
        pole[0][1] = 4.0;
        
        assertArrayEquals(pole , Firmy.getGraf(datum,datum.minusDays(2),"firma5"));
    }
    
    @Test(expected = DataException.class)
    public void testGetGraf4() throws FatalException, DataException, DatumException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        Object[][] pole = new Object[1][2];
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd. MM. yyyy");
        pole[0][0] = datum.toString(formatter);
        pole[0][1] = 4.0;
        
        assertArrayEquals(pole , Firmy.getGraf(datum.minusDays(5),datum.minusDays(2),"firma"));
    }
    
    @Test(expected = FatalException.class)
    public void testClear() throws FatalException, DataException, DatumException{
        Firmy.clear();
        Akcie akcie = new Akcie(1, 5, 2, 300, datum.minusDays(1), "firma", 2);
        Akcie akcie1 = new Akcie(2, 4, 8, 100, datum, "firma1", 7);
        Akcie akcie2 = new Akcie(5, 2, 4, 200, datum, "firma", 5);
        Akcie akcie3 = new Akcie(4, 1, 7, 400, datum, "firma2", 4);
        Akcie akcie4 = new Akcie(2, 5, 1, 500, datum.minusDays(1), "firma2", 3);
        
        Firmy.pridatAkcii(akcie);
        Firmy.pridatAkcii(akcie1);
        Firmy.pridatAkcii(akcie2);
        Firmy.pridatAkcii(akcie3);
        Firmy.pridatAkcii(akcie4);
        
        Firmy.clear();
        
        Firmy.getGraf(datum.minusDays(5),datum.minusDays(2),"firma");
    }
}