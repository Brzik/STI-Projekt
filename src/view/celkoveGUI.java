/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Michaela
 */

//celkovy vzhled aplikace, tohle dedi ostatni tridy
public abstract class celkoveGUI extends JFrame {

    //promenne
    //titulek okna
    private String titulekOkna;
    //pro zadavani casu
    private JTextField casOd, casDo;
    //tlacitka
    private JButton casovyUsekOK, vykreslitGraf, aktualizovatData;
    //checkbox
    private JCheckBox zaskrtavatko;
    //texty, popisky
    private JLabel casovyUsek, cOd, cDo, datumAktualizaceDat, error;
    //okno
    private JFrame okno;
    //layout
    private JPanel celkovyLayout, horni;

    //konstruktor
    public celkoveGUI() {

        //nastaveni Labelu
        titulekOkna = "Kupování akcií";
        casovyUsek = new JLabel("Časový úsek");
        cOd = new JLabel("čas od: ");
        cDo = new JLabel("čas do: ");
        datumAktualizaceDat = new JLabel("Poslední aktualizace dat proběhla: ");
        error = new JLabel();

        //nastaveni popisku tlacitek
        casovyUsekOK = new JButton("OK");   //potvrzeni casoveho useku
        vykreslitGraf = new JButton("Vykresli graf");   //vykresleni grafu
        aktualizovatData = new JButton("Aktualizuj data");   //aktualizace dat

        //listenery k tlacitkum
        casovyUsekListener(new CasovyUsekButtonListener());
        vykreslitGrafListener(new VykreslitGrafButtonListener());
        aktualizovatDataListener(new AktualizovatDataButtonListener());

        //nastaveni velikosti okna, titulku a ukonceni aplikace po zavreni okna
        okno.setSize(550, 400);
        okno.setTitle(titulekOkna);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //nastaveni layoutu
        celkovyLayout = new JPanel();
        celkovyLayout.setLayout(new BorderLayout());
        horni = new JPanel();
        horni.setLayout(new GridLayout(2, 4));   //pro zadani casoveho useku a vypis posledni aktualizace



        //zaplneni layoutu

        //HORNI CAST
        //zadavani casoveho useku
        add(horni, BorderLayout.PAGE_START);
        horni.add(casovyUsek);     //textovy popisek
        horni.add(cOd, casOd);
        horni.add(cDo, casDo);
        horni.add(casovyUsekOK);   //tlacitko

        //aktualizace dat
        horni.add(datumAktualizaceDat);
        JLabel pom = new JLabel("PRIDAT DATUM POSLEDNIHO STAZENI SOUBORU");
        horni.add(pom);  //predelat na datum->tahat ze souboru
        horni.add(aktualizovatData);   //tlacitko

        //STRED
        //tabulka s informacemi o firmach
        TabulkaCheckbox tabulka=new TabulkaCheckbox();
        add(tabulka.tabulkaGUI(), BorderLayout.CENTER);
        
        

        add(vykreslitGraf, BorderLayout.LINE_END);    //tlacitko 

        //DOLNI CAST
        //vypis chyb
        add(error, BorderLayout.PAGE_END);




    }

    //zpracuje udalost nad tlacitkem casovyUsek
    public void casovyUsekListener(ActionListener action) {
        casovyUsekOK.addActionListener(action);
    }
    
    //obslouzi udalost nad tlacitkem casovy usek
    private class CasovyUsekButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //zpracovani casoveho useku
        }
    }
    
    //pro tlacitko vykreslit graf
    public void vykreslitGrafListener(ActionListener action){
        vykreslitGraf.addActionListener(action);
    }
    
    private class VykreslitGrafButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //donoveho okna vykresli graf
        }
    }
    
    //pro tlacitko aktualizovat data
    public void aktualizovatDataListener(ActionListener action){
        aktualizovatData.addActionListener(action);
    }
    
    private class AktualizovatDataButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //aktualizuje soubor=znovu stahne
        }
    }
    
}
