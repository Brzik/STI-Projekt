package projekt;

import java.util.ArrayList;
import model.DataException;
import model.DatumException;
import model.FatalException;
import model.Model;
import org.joda.time.LocalDate;

/**
 *
 * @author Brzik
 */
public class STIProjekt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*
         * test
         */
        Model model;
        ArrayList<Object[]> dataTabulka;
        
        try {
            model = Model.getModel();
            System.out.println("adssad");
            LocalDate datum = new LocalDate();
            dataTabulka = model.getDataTabulka(datum.minusDays(6),datum.minusDays(3));
            System.out.println("ha");
            model.getDlouhodobyPrumerFirmy("AAA");
            System.out.println("dlouhodoby prumer");
            model.getDataGraf(datum.minusDays(6), datum.minusDays(3), "CETV");
            System.out.println("i graf");
            System.out.println(model.getPosledniDatumVSouboru());
        } catch (DataException | FatalException | DatumException ex) {
            System.err.println(ex.getMessage());
        }
        
    }
}
