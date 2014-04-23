/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import model.Model;

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
    private JPanel celkovyLayout;

    //konstruktor
    public celkoveGUI(Model model) {

        //nastaveni Labelu
        titulekOkna = "Kupování akcií";
        casovyUsek = new JLabel("Časový úsek");
        cOd = new JLabel("čas od: ");
        cDo = new JLabel("čas do: ");
        datumAktualizaceDat = new JLabel("Poslední aktualizace dat proběhla: ");
        error = new JLabel();
        
        //nastaveni textfieldu
        casDo=new JTextField( 10);
        casOd=new JTextField( 10);
        
       

        //nastaveni popisku tlacitek
        casovyUsekOK = new JButton("OK");   //potvrzeni casoveho useku
        vykreslitGraf = new JButton("Vykresli graf");   //vykresleni grafu
        aktualizovatData = new JButton("Aktualizuj data");   //aktualizace dat

        //listenery k tlacitkum
        casovyUsekListener(new CasovyUsekButtonListener());
        vykreslitGrafListener(new VykreslitGrafButtonListener());
        aktualizovatDataListener(new AktualizovatDataButtonListener());

        //nastaveni velikosti okna, titulku a ukonceni aplikace po zavreni okna
        setSize(600, 500);
        setTitle(titulekOkna);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //nastaveni layoutu
        celkovyLayout = new JPanel();
        Container con=this.getContentPane();
        con.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        
        
        g.fill=GridBagConstraints.HORIZONTAL;
        g.gridx=0;
        g.gridy=0;
        g.ipadx=10;
        con.add(casovyUsek, g);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx=1;
        g.gridy=0;
        g.ipadx=10;
        con.add(cOd, g);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx=2;
        g.gridy=0;
        g.ipadx=10;
        con.add(casOd, g);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx=3;
        g.gridy=0;
        g.ipadx=10;
        con.add(cDo, g);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx=4;
        g.gridy=0;
        g.ipadx=10;
        con.add(casDo, g);
        g.anchor = GridBagConstraints.HORIZONTAL;
        g.gridx=5;
        g.gridy=0;
        g.ipadx=10;
        con.add(casovyUsekOK, g);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx=0;
        g.gridy=1;
        g.ipadx=10;
        con.add(datumAktualizaceDat, g);
        g.fill = GridBagConstraints.HORIZONTAL;
        JLabel pom = new JLabel("PRIDAT DATUM ");
        g.gridx=1;
        g.gridy=1;
        g.ipadx=10;
        con.add(pom, g);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx=5;
        g.gridy=1;
        g.ipadx=10;
        con.add(aktualizovatData, g);
        g.fill = GridBagConstraints.CENTER;
        TabulkaCheckbox tabulka = new TabulkaCheckbox();
        g.gridx=0;
        g.gridy=20;
        g.ipadx=10;
        con.add(tabulka.tabulkaGUI(), g);
        
//        horni = new JPanel();
//        horni.setLayout(new GridLayout(1, 6));   //pro zadani casoveho useku a vypis posledni aktualizace
//        
//
//
//
//        //zaplneni layoutu
//
//        //HORNI CAST
//        //zadavani casoveho useku
//        add(horni, BorderLayout.PAGE_START);
//        horni.add(casovyUsek);     //textovy popisek
//        horni.add(cOd);
//       // horni.add(casOd);
//        horni.add(cDo);
//        //horni.add(casDo);
//        horni.add(casovyUsekOK);   //tlacitko

        //aktualizace dat
        // add(vypln, BorderLayout.LINE_START);
        //aktualizace.add(datumAktualizaceDat);
        //JLabel pom = new JLabel("PRIDAT DATUM POSLEDNIHO STAZENI SOUBORU");
        //aktualizace.add(pom);  //predelat na datum->tahat ze souboru
        //aktualizace.add(aktualizovatData);   //tlacitko

        //STRED
        //tabulka s informacemi o firmach
        



        //add(vykreslitGraf, BorderLayout.LINE_END);    //tlacitko 

        //DOLNI CAST
        //vypis chyb
        //add(error, BorderLayout.PAGE_END);




    }

    //zpracuje udalost nad tlacitkem casovyUsek
    public void casovyUsekListener(ActionListener action) {
        casovyUsekOK.addActionListener(action);
    }

    //obslouzi udalost nad tlacitkem casovy usek
    private class CasovyUsekButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //zpracovani casoveho useku
        }
    }

    //pro tlacitko vykreslit graf
    public void vykreslitGrafListener(ActionListener action) {
        vykreslitGraf.addActionListener(action);
    }

    private class VykreslitGrafButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //donoveho okna vykresli graf
        }
    }

    //pro tlacitko aktualizovat data
    public void aktualizovatDataListener(ActionListener action) {
        aktualizovatData.addActionListener(action);
    }

    private class AktualizovatDataButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //aktualizuje soubor=znovu stahne
        }
    }
}
