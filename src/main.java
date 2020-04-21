import businessLayer.Utilities.alertSystem.AlertSystem;
import businessLayer.userTypes.SystemController;

public class main {
    public static void main(String args[]){
        SystemController systemController = SystemController.SystemController();
        systemController.setAlertSystem(new AlertSystem());



    }
}
