/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Michaela
 */
public class Tabulka extends JFrame {

    //tabulka
    private JTable tabulka;
    //titulek tabulky
    private String titulek;
    //nazvy sloupecku tabulky
    private String prvni, druhy, treti, ctvrty, paty, sesty, sedmy, osmy;
    
    //checkBox pro posledni sloupecek
    private JCheckBox chciGraf;

    public Tabulka() {
        //check box
        chciGraf=new JCheckBox();
       

        //nazvy sloupecku
        prvni = "Zkratka firmy";
        druhy = "Průměrná cena";
        treti = "Průměrný objem";
        ctvrty = "Maximum";
        paty = "Minimum";
        sesty = "Dlouhodobý průměr";
        sedmy = "Kupovat";
        osmy = "Graf";

 
        tabulka=new JTable(new MojeTabulka());
        
        //pro test vzhledu tabulky, pak smazat
        JScrollPane scrollPane = new JScrollPane(tabulka);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);
        panel.add(scrollPane, BorderLayout.CENTER);
        
    }
    class MojeTabulka extends AbstractTableModel{
        
         //vytvoreni sloupecku 
        private String jmenaSloupecku[] = {
            prvni, druhy, treti, ctvrty, paty, sesty, sedmy, osmy
        };
        
        //naplneni tabulky data, celkem 14 firem
        //TAHAT DATA ZE SOUBORU
        //zatim jen hodnoty pro test
        private Object dataTabulky[][] = {
            {"AAA", "Prum cena", "prum objem", "max", "min", "dlouhodob prum", "kupovat", new Boolean(false)},
            {"CETV", "Prum cena2", "prum objem2", "max2", "min2", "dlouhodob prum2", "kupovat2", new Boolean(false)}
        };
        
       
        

        @Override
        public String getColumnName(int col) {
            return jmenaSloupecku[col];
        }
        
        @Override
        public int getRowCount() {
            return dataTabulky.length;
        }

        @Override
        public int getColumnCount() {
            return jmenaSloupecku.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return dataTabulky[rowIndex][columnIndex];
        }
        
        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        
        @Override
        public void setValueAt(Object value, int row, int col) {
            dataTabulky[row][col] = value;
            fireTableCellUpdated(row, col);
        }
        
    }
}
