package projekt;

import controller.Controller;
import model.Model;
import view.GUI;

//import view.View;

/**
 *
 * @author Jan Brzobohatý
 */
public class STIProjekt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                GUI view = GUI.getGUI();
//            }
//        });
        
        Model model = Model.getModel();
        GUI view = GUI.getGUI();
        
        Controller controller = new Controller(model, view);
        controller.setVisible(true);
    }
}
