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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import model.Model;
import model.DataException;
import model.DatumException;
import model.FatalException;

/**
 *
 * @author Michaela
 */
public abstract class GUIAplikace extends JFrame {

    //promenne
    //titulek okna
    private static String titulekOkna;
    //pro zadavani casu
    private static JTextField denOdText, denDoText, mesicOdText, mesicDoText, rokOdText, rokDoText;
    //tlacitka
    private static JButton casovyUsekOK, vykreslitGraf, aktualizovatData;
    //checkbox
    private static JCheckBox zaskrtavatko;
    //texty, popisky
    private static JLabel casovyUsekOd, casovyUsekDo, cOd, cDo, datumAktualizaceDat, posledniDatum, error;
    private static JLabel denOdLabel, denDoLabel, mesicOdLabel, mesicDoLabel, rokOdLabel, rokDoLabel;
    //okno
    private static JFrame okno;
    //layout
    private static JPanel rozlozeni, horni, tab, chyba;
    //tabulka
    static TabulkaCheckbox tabulka;
    //Model
    Model model = Model.getModel();
    //chyba
    DataException errorData;
    DatumException errorDatum;
    FatalException errorFatal;
    //pro zaplneni okna
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

   

    public GUIAplikace(Model model) throws DataException, FatalException, DatumException {
         vytvorAZobrazGUI();
    }

    public void pridejKomponentu(Container pane) throws DataException, FatalException, DatumException {


        nastavKomponenty();

       // nastavListenery();

        zobrazTabulku(pane);

    }
    
    //listenery pro textfieldy
    protected int getZacatekRok(){
        int rokOd=Integer.parseInt(rokOdText.getText());
        return rokOd;
    }
    protected int getZacatekMesic(){
        int mesicOd=Integer.parseInt(mesicOdText.getText());
        return mesicOd;
    }
    protected int getZacatekDen(){
        int denOd=Integer.parseInt(denOdText.getText());
        return denOd;
    }
    protected int getKonecRok(){
        int rokDo=Integer.parseInt(denDoText.getText());
        return rokDo;
    }
    protected int getKonecMesic(){
        int mesicDo=Integer.parseInt(mesicDoText.getText());
        return mesicDo;
    }
    protected int getKonecDen(){
        int denDo=Integer.parseInt(rokDoText.getText());
        return denDo;
    }

//    public void nastavListenery() {     
//        nastavListenerProCasUsek();
//        nastavListenerProGraf();
//        nastavListenerAktualizaceDat();
//       
//    }
    //vypne tlacitka
    protected void vypniTlacitka(){
        vykreslitGraf.setEnabled(false);
        aktualizovatData.setEnabled(false);
        casovyUsekOK.setEnabled(false);
    }
    protected void zapniTlacitka(){
        vykreslitGraf.setEnabled(true);
        aktualizovatData.setEnabled(true);
        casovyUsekOK.setEnabled(true);
    }
    
    //Listener pro casovy usek zadavany uzivatelem
    public void nastavListenerProCasUsek() {
        //casovyUsekOK.addActionListener(new IntervalListener());
    }
    
    //Listener pro tlacitko vykresleni grafu
    public void nastavListenerProGraf() {
        //vykreslitGraf.addActionListener(new GrafListener());
    }
    
    //listener pro tlacitko aktualizovat data
    public void nastavListenerAktualizaceDat(){
        //aktualizovatData.addActionListener(new AktualizaceListener());
    }
    

    public void nastavKomponenty() {
        //nastaveni Labelu
        titulekOkna = "Kupování akcií";
        casovyUsekOd = new JLabel("Časový úsek od:");
        casovyUsekDo = new JLabel("Časový úsek do:");
        cOd = new JLabel("čas od: ");
        cDo = new JLabel("čas do: ");
        denOdLabel = new JLabel("den: ");
        denDoLabel = new JLabel("den: ");
        mesicOdLabel = new JLabel("měsíc: ");
        mesicDoLabel = new JLabel("měsíc: ");
        rokOdLabel = new JLabel("rok: ");
        rokDoLabel = new JLabel("rok: ");

        datumAktualizaceDat = new JLabel("Datum poslední aktualizace: ");
        error = new JLabel();

        //nastaveni textfieldu
        denOdText = new JTextField(10);
        denDoText = new JTextField(10);
        mesicDoText = new JTextField(10);
        mesicOdText = new JTextField(10);
        rokOdText = new JTextField(10);
        rokDoText = new JTextField(10);


        //nastaveni popisku tlacitek
        casovyUsekOK = new JButton("OK");   //potvrzeni casoveho useku
        vykreslitGraf = new JButton("Vykresli graf");   //vykresleni grafu
        aktualizovatData = new JButton("Aktualizuj data");   //aktualizace dat
    }

    public void zobrazTabulku(Container pane) throws DataException, FatalException, DatumException {
        //razeni zprava doleva
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {

            c.fill = GridBagConstraints.HORIZONTAL;
        }

        if (shouldWeightX) {
            c.weightx = 0.5;
        }

        layoutProCasUsek(c, pane);
        layoutProPosledniAktualizaciDat(c, pane);
        layoutProTabulku(c, pane);
        layoutProTlacitkaGrafu(c, pane);
        layoutProTlacitkoAktualizace(c, pane);
        layoutProChyboveHlasky(c, pane);
    }

    public void layoutProChyboveHlasky(GridBagConstraints c, Container pane) {

        // FatalException chyba=new FatalException(titulekOkna);
        //chybove hlasky
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 7;
        c.insets = new Insets(10, 0, 0, 0);
        c.anchor = GridBagConstraints.PAGE_END;
        pane.add(error, c);
    }

    public void layoutProTlacitkaGrafu(GridBagConstraints c, Container pane) {
        //tlacitko pro vykresleni grafu
        c.gridx = 6;
        c.gridy = 7;
        c.gridwidth = 1;
        pane.add(vykreslitGraf, c);
    }

    public void layoutProTlacitkoAktualizace(GridBagConstraints c, Container pane) {
        //aktualizace dat
        c.gridx = 6;
        c.gridy = 8;
        pane.add(aktualizovatData, c);
    }

    public void layoutProTabulku(GridBagConstraints c, Container pane) throws DataException, FatalException, DatumException {
        //tabulka
        tabulka = new TabulkaCheckbox();


        c.insets = new Insets(20, 10, 10, 10);
        //c.weighty = 1.0;
        c.gridx = 0;
        c.gridwidth = 7;   //nastaveni sirky tabulky
        c.gridy = 6;
        pane.add(new JScrollPane(tabulka.tabulkaUI()), c);  //diky JScrollPane se zobrazi nazvy sloupecku
    }

    public void layoutProPosledniAktualizaciDat(GridBagConstraints c, Container pane) {
        //datum posledni aktualizace souboru
        c.insets = new Insets(5, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 5;
        pane.add(datumAktualizaceDat, c);


        //JLabel posledniDatum = new JLabel(model.getPosledniDatumVSouboru());

        JLabel pom = new JLabel("datum");
        c.gridx = 1;
        c.gridy = 5;
        pane.add(pom, c);
    }

    public void layoutProCasUsek(GridBagConstraints c, Container pane) {
        //zadani casoveho useku
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;

        c.insets = new Insets(5, 10, 10, 10);
        pane.add(casovyUsekOd, c);


        //den od label
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        //c.fill=GridBagConstraints.EAST;
        pane.add(denOdLabel, c);

        //den od text
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        pane.add(denOdText, c);

        //mesic od label
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 1;
        pane.add(mesicOdLabel, c);

        //mesic od text
        c.gridx = 4;
        c.gridy = 0;
        c.gridwidth = 1;
        pane.add(mesicOdText, c);

        //rok od label
        c.gridx = 5;
        c.gridy = 0;
        c.gridwidth = 1;
        pane.add(rokOdLabel, c);

        //rok od text
        c.gridx = 6;
        c.gridy = 0;
        c.gridwidth = 1;
        pane.add(rokOdText, c);

        //zadani casoveho useku do
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.insets = new Insets(5, 10, 10, 10);
        pane.add(casovyUsekDo, c);

        //den do label
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
//        c.insets = new Insets(5, 10, 10, 10);
        pane.add(denDoLabel, c);

        //den do text
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
//        c.insets = new Insets(5, 10, 10, 10);
        pane.add(denDoText, c);

        //mesic do label
        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 1;
//        c.insets = new Insets(5, 10, 10, 10);
        pane.add(mesicDoLabel, c);

        //mesic do text
        c.gridx = 4;
        c.gridy = 2;
        c.gridwidth = 1;
//        c.insets = new Insets(5, 10, 10, 10);
        pane.add(mesicDoText, c);

        //rok do label
        c.gridx = 5;
        c.gridy = 2;
        c.gridwidth = 1;
//        c.insets = new Insets(5, 10, 10, 10);
        pane.add(rokDoLabel, c);

        //rok do text
        c.gridx = 6;
        c.gridy = 2;
        c.gridwidth = 1;
//        c.insets = new Insets(5, 10, 10, 10);
        pane.add(rokDoText, c);

        //talcitko pro potvryeni cas useku
        c.gridx = 6;
        c.gridy = 4;
        c.gridwidth = 1;
//        c.insets = new Insets(5, 10, 10, 10);
        pane.add(casovyUsekOK, c);
    }
    
    //zobrazi prazdnou tabulku
    protected void zobrazPrazdnouTabulku(){
        tabulka.prazdnaTabulka();
    }
    //zobrazi tabulku s daty
    protected void zobrazPlnouTabulku() throws DataException, FatalException, DatumException{
        tabulka.tabulkaUI();
    }

    protected void vytvorAZobrazGUI() throws DataException, FatalException, DatumException {
        //pokud bychom chteli hezci okno,staci odkomentovat
        //JFrame.setDefaultLookAndFeelDecorated(true);

        //vytvori okno
        JFrame frame = new JFrame("Kupovani akcii");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //minimalni velikost okna
        // frame.setMinimumSize(new Dimension(500, 500));
        //frame.setBounds(0, 0, 600, 300);
        frame.setResizable(false);


        //nastav content pane
        pridejKomponentu(frame.getContentPane());

        //ukaz okno
        frame.pack();
        frame.setVisible(true);
    }
}
