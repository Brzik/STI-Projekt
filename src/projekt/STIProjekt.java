package projekt;

import controller.Controller;
import model.Model;
import view.GUIAplikace;
import view.TabulkaCheckbox;
import view.akcieGUI;
//import view.View;

/**
 *
 * @author Brzik
 */
public class STIProjekt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Model model = Model.getModel();
        /*  View view = new View();
         
        
         Controller controller = new Controller(model, view);
        
         controller.setVisible(true);*/

        /*
         * test
         */
//        Model model;
//        ArrayList<Object[]> dataTabulka;
//        
//        try {
//            model = Model.getModel();
//            System.out.println("adssad");
//            LocalDate datum = new LocalDate();
//            dataTabulka = model.getDataTabulka(datum.minusDays(6),datum.minusDays(3));
//            System.out.println("ha");
//            model.getDlouhodobyPrumerFirmy("AAA");
//            System.out.println("dlouhodoby prumer");
//            model.getDataGraf(datum.minusDays(6), datum.minusDays(3), "CETV");
//            System.out.println("i graf");
//            System.out.println(model.getPosledniDatumVSouboru());
//        } catch (DataException | FatalException | DatumException ex) {
//            System.err.println(ex.getMessage());
//        }


        //test tabulky
//       TabulkaCheckbox tc=new TabulkaCheckbox();
//        tc.tabulkaGUI();

      
        akcieGUI ag=new akcieGUI(model);
    }
}
