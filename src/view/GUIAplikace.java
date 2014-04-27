/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Model;
import controller.AktualizaceListener;
import controller.Controller;
import controller.GrafListener;
import controller.IntervalListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;


/**
 *
 * @author Michaela
 */
public abstract class GUIAplikace extends JFrame {

    //promenne
    //titulek okna
    private static String titulekOkna;
    //pro zadavani casu
    private static JTextField casOd, casDo;
    //tlacitka
    private static JButton casovyUsekOK, vykreslitGraf, aktualizovatData;
    //checkbox
    private static JCheckBox zaskrtavatko;
    //texty, popisky
    private static JLabel casovyUsek, cOd, cDo, datumAktualizaceDat,posledniDatum, error;
    //okno
    private static JFrame okno;
    //layout
    private static JPanel rozlozeni, horni, tab, chyba;
    //tabulka
    static TabulkaCheckbox tabulka;
    //Model
    Model model=Model.getModel();
    
    //pro zaplneni okna
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    //konstruktor
    public GUIAplikace(Model model) {
        
        vytvorAZobrazGUI();
    }
    
    
    
    public   void pridejKomponentu(Container pane) {
        
        
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
        
        
        //listenery
        casovyUsekOK.addActionListener(new IntervalListener());
        vykreslitGraf.addActionListener(new GrafListener());
        aktualizovatData.addActionListener(new AktualizaceListener());

        
        
        //razeni zprava doleva
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

    
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

       
        if (shouldWeightX) {
            c.weightx = 0.5;
        }
        
        //zadani casoveho useku
        c.gridx = 0;
        c.gridy = 0;
        pane.add(casovyUsek, c);

       
        c.gridx = 1;
        c.gridy = 0;
        pane.add(cOd, c);

        
        c.gridx = 2;
        c.gridy = 0;
        //c.weightx = 0.1;
        pane.add(casOd, c);
        
        c.gridx = 3;
        c.gridy = 0;
        pane.add(cDo, c);

        
        c.gridx = 4;
        c.gridy = 0;
        pane.add(casDo, c);
        
        c.gridx = 5;
        c.gridy = 0;
        pane.add(casovyUsekOK, c);
        
        //datum posledni aktualizace souboru
        c.insets = new Insets(5,0,0,0);
        c.gridx = 0;
        c.gridy = 1;
        pane.add(datumAktualizaceDat, c);
        
        
        //JLabel posledniDatum = new JLabel(model.getPosledniDatumVSouboru());
        JLabel pom=new JLabel("prozatim");
        c.gridx = 1;
        c.gridy = 1;
        pane.add(pom, c);
        
        c.gridx = 5;
        c.gridy = 1;
        pane.add(aktualizovatData, c);
        
        
        //tabulka
        tabulka=new TabulkaCheckbox();
        
        
        //c.ipady = 40;
        c.insets = new Insets(20,0,0,0);
        c.weighty = 1.0;   
        c.gridx = 0;       
        c.gridwidth = 6;   //nastaveni sirky tabulky
        c.gridy = 3;       
        pane.add(new JScrollPane(tabulka.tabulkaUI()), c);  //diky JScrollPane se zobrazi nazvy sloupecku
        
        //tlacitko pro vykresleni grafu
        c.gridx = 5;
        c.gridy = 4;
        c.gridwidth = 1;
        pane.add(vykreslitGraf, c);
        
        //chybove hlasky
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 6;
        c.insets = new Insets(10,0,0,0);
        c.anchor = GridBagConstraints.PAGE_END; 
        pane.add(error, c);
        
    }
    
    private  void vytvorAZobrazGUI() {
        //pokud bychom chteli hezci okno,staci odkomentovat
        //JFrame.setDefaultLookAndFeelDecorated(true);

        //vytvori okno
        JFrame frame = new JFrame("Kupovani akcii");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        //nastav content pane
        pridejKomponentu(frame.getContentPane());

        //ukaz okno
        frame.pack();
        frame.setVisible(true);
    }
}

