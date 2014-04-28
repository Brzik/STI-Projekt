/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import model.Model;

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

    public JTable tabulkaUI() {
        dtm = new DefaultTableModel(data, jmenaSloupcu);
        tabulka = new JTable(dtm);


        //hodnoty napsane natvrdo
        //staci jen predelat na for cyklus
        dtm.addRow(new Object[]{"AAA", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
        dtm.addRow(new Object[]{"CETV", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
        dtm.addRow(new Object[]{"CEZ", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
        dtm.addRow(new Object[]{"ERBAG", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
        dtm.addRow(new Object[]{"FOREG", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
        dtm.addRow(new Object[]{"KOMB", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
        dtm.addRow(new Object[]{"NWRUK", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
        dtm.addRow(new Object[]{"ORCO", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
        dtm.addRow(new Object[]{"PEGAS", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
        dtm.addRow(new Object[]{"TABAK", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
        dtm.addRow(new Object[]{"TELEC", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
        dtm.addRow(new Object[]{"TMR", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
        dtm.addRow(new Object[]{"UNIPE", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});
        dtm.addRow(new Object[]{"VIG", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)});



        //checkbox pro posledni sloupecek
        for (int columnNumber = 7; columnNumber < tabulka.getColumnCount(); columnNumber++) {
            TableColumn tc = tabulka.getColumnModel().getColumn(columnNumber);
            tc.setCellEditor(tabulka.getDefaultEditor(Boolean.class));
            tc.setCellRenderer(tabulka.getDefaultRenderer(Boolean.class));
            //tc.setHeaderRenderer(new CheckBoxHeader(new MyItemListener(), String.valueOf(columnNumber)));
        }
        tabulka.setVisible(true);
        velikostSloupecku(tabulka);

        return tabulka;
    }

    public void velikostSloupecku(JTable tabulka) {
        tabulka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabulka.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabulka.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabulka.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabulka.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabulka.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabulka.getColumnModel().getColumn(5).setPreferredWidth(130);
        tabulka.getColumnModel().getColumn(6).setPreferredWidth(80);
        tabulka.getColumnModel().getColumn(7).setPreferredWidth(70);
       
        
//        table.getColumnModel().getColumn(1).setPreferredWidth(120);
//        table.getColumnModel().getColumn(2).setPreferredWidth(100);
//        table.getColumnModel().getColumn(3).setPreferredWidth(90);
//        table.getColumnModel().getColumn(4).setPreferredWidth(90);
//        table.getColumnModel().getColumn(6).setPreferredWidth(120);
//        table.getColumnModel().getColumn(7).setPreferredWidth(100);
//        table.getColumnModel().getColumn(8).setPreferredWidth(95);
//        table.getColumnModel().getColumn(9).setPreferredWidth(40);
//        table.getColumnModel().getColumn(10).setPreferredWidth(400);

    }
}
