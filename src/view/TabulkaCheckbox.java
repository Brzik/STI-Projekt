/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import model.DataException;
import model.DatumException;
import model.FatalException;
import model.Model;
import org.joda.time.LocalDate;

/**
 *
 * @author Michaela
 */
public class TabulkaCheckbox {

    //nazvy sloupecku tabulky
    private String prvni = "Zkratka firmy", druhy = "Průměrná cena", treti = "Průměrný objem";
    private String ctvrty = "Maximum", paty = "Minimum", sesty = "Dlouhodobý průměr";
    private String sedmy = "Kupovat", osmy = "Graf";
    Object jmenaSloupcu[] = {prvni, druhy, treti, ctvrty, paty, sesty, sedmy, osmy};
    Object data[][] = {};
    DefaultTableModel dtm;
    JTable tabulka;
    Model model = Model.getModel();

    public JTable tabulkaUI() throws DataException, FatalException, DatumException {
        dtm = new DefaultTableModel(data, jmenaSloupcu);
        tabulka = new JTable(dtm);

        naplneniTabulkyDaty(tabulka, dtm);
        checkBoxProPosledniSloupec(tabulka);
        velikostSloupecku(tabulka);
        cteniRadku();

        tabulka.setVisible(true);


        return tabulka;
    }
    //zobrazi prazdnou tabulku

    protected JTable prazdnaTabulka() {
        dtm = new DefaultTableModel(data, jmenaSloupcu);
        tabulka = new JTable(dtm);
        tabulka.setVisible(true);
        return tabulka;
    }

    //nastavi natvrdo velikost sloupcu tabulky
    public void velikostSloupecku(JTable tabulka) {
        tabulka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabulka.getColumnModel().getColumn(0).setPreferredWidth(80);
        tabulka.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabulka.getColumnModel().getColumn(2).setPreferredWidth(106);
        tabulka.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabulka.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabulka.getColumnModel().getColumn(5).setPreferredWidth(130);
        tabulka.getColumnModel().getColumn(6).setPreferredWidth(80);
        tabulka.getColumnModel().getColumn(7).setPreferredWidth(70);


    }

    public void cteniRadku() {

        ListSelectionModel selectionModel = tabulka.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);  //mozna to bude chtit multiple selection
        selectionModel.addListSelectionListener(new RowListener(this));
    }

    class RowListener implements ListSelectionListener {

        TabulkaCheckbox ctiRadek;

        public RowListener(TabulkaCheckbox ctiR) {
            ctiRadek = ctiR;
            tabulka = ctiRadek.tabulka;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                ListSelectionModel model = tabulka.getSelectionModel();
                int lead = model.getLeadSelectionIndex();
                zobrazDataZVybranehoRadku(lead);
            }
        }

        private void zobrazDataZVybranehoRadku(int indexRadku) {
            int sloupecky = tabulka.getColumnCount();
            String s = "";
            for (int i = 0; i < sloupecky - 1; i++) {
                Object o = tabulka.getValueAt(indexRadku, i);
                s += o.toString();
                if (i < sloupecky - 1) {
                    s += ", ";
                }
            }
//            readRow.label.setText(s);
            System.out.println(s);
        }
    }

//    public void tabulkaListener(JTable tabulka){
//        tabulka.getModel().addTableModelListener(new TableModelListener() {
//            JTable tabulka;
//            String selectedData;
//            @Override
//            public void tableChanged(TableModelEvent e) {
//                List<String> dataTabulky = new ArrayList<>();
//                tabulka.getValueAt(0,0);
//                System.out.println("Selected Rows: " + );
//                System.out.println("Selected: " + dataTabulky);
//            }
//        });
//    }
//    public void tabulkaListener(JTable tabulka) {
//        tabulka.setCellSelectionEnabled(true);
//        ListSelectionModel cellSelectionModel = tabulka.getSelectionModel();
//        cellSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);  //umozni vyber vice radku najednou
//
//
//        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
//            JTable tabulka;
//            
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                String selectedData = null;
//
//                //int[] selectedRow = tabulka.getSelectedRows();
//                //int[] selectedColumns = tabulka.getSelectedColumns();
//                List<String> dataTabulky = new ArrayList<>();
//                for (int i = 0; i < tabulka.getRowCount(); i++) {
//                    for (int j = 0; j < tabulka.getColumnCount(); j++) {
//                        selectedData = (String) tabulka.getValueAt(i, j);
//                        dataTabulky.add(selectedData);
//                    }
//                }
//                System.out.println("Selected: " + dataTabulky);
//            }
//        });
//    }
    //prida checkbox do posledniho sloupecku
    public void checkBoxProPosledniSloupec(JTable tabulka) {
        //checkbox pro posledni sloupecek
        for (int columnNumber = 7; columnNumber < tabulka.getColumnCount(); columnNumber++) {
            TableColumn tc = tabulka.getColumnModel().getColumn(columnNumber);
            tc.setCellEditor(tabulka.getDefaultEditor(Boolean.class));
            tc.setCellRenderer(tabulka.getDefaultRenderer(Boolean.class));
            //tc.setHeaderRenderer(new CheckBoxHeader(new MyItemListener(), String.valueOf(columnNumber)));
        }
    }

    //naplni tabulku daty
    public void naplneniTabulkyDaty(JTable tabulka, DefaultTableModel dtm) throws DataException, DatumException, FatalException {
        LocalDate zacatek, konec;
        zacatek = new LocalDate();
        konec=new LocalDate();
        for (int i = 0; i < 14; i++) {
//            String zkratkaFirmy, prumCena, prumObjem, max, min, dlouhPrum, kupovat;
//            zkratkaFirmy="AAA";
//            prumCena="100";
//            prumObjem="1500";
//            max="450";
//            min="210";
//            dlouhPrum="360";
//            kupovat="ano";
            dtm.addRow(new Object[]{model.getDataTabulka(zacatek, konec), new Boolean(false)});
        }

        //hodnoty napsane natvrdo
        //staci jen predelat na for cyklus
//        dtm.addRow(new Object[]{"AAA", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
//        dtm.addRow(new Object[]{"CETV", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
//        dtm.addRow(new Object[]{"CEZ", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
//        dtm.addRow(new Object[]{"ERBAG", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
//        dtm.addRow(new Object[]{"FOREG", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
//        dtm.addRow(new Object[]{"KOMB", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
//        dtm.addRow(new Object[]{"NWRUK", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
//        dtm.addRow(new Object[]{"ORCO", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
//        dtm.addRow(new Object[]{"PEGAS", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
//        dtm.addRow(new Object[]{"TABAK", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
//        dtm.addRow(new Object[]{"TELEC", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
//        dtm.addRow(new Object[]{"TMR", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
//        dtm.addRow(new Object[]{"UNIPE", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
//        dtm.addRow(new Object[]{"VIG", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});


    }

    //vytahne data z tabulky, pokud se klikne do libovol radku
    public void ziskejDataZTabulky(JTable tabulka) {

        //projde radky 
        for (int i = 0; i < tabulka.getRowCount(); i++) {
            boolean isChecked = (Boolean) tabulka.getValueAt(i, 7);

            //pokud je zaskrtnuto,projde sloupecky a vytaha z nich data
            if (isChecked) {
                //vytvoreni arrayListu pro ulozeni dat
                List<String> dataTabulky = new ArrayList<>();
                //projde sloupecky
                for (int j = 0; j < tabulka.getColumnCount(); j++) {
                    dataTabulky.add(dtm.getValueAt(i, j).toString());
                    System.out.println(dataTabulky.add(dtm.getValueAt(i, j).toString()));
                }
            }
        }
    }
}
