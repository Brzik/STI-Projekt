package projekt;

import controller.Controller;
import model.Model;
import view.GUIAplikace;
import view.TabulkaCheckbox;
import view.View;
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
        View view = new View();
        
        Controller controller = new Controller(model, view);
        //controller.setVisible(true);

        //test tabulky
//       TabulkaCheckbox tc=new TabulkaCheckbox();
//        tc.tabulkaGUI();

      
        //akcieGUI ag=new akcieGUI(model);
    }
}
