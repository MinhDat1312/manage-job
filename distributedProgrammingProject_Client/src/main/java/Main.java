import com.formdev.flatlaf.FlatLightLaf;
import controller.LoginController;
import controller.MainController;
import entity.Employee;
import service.inteface.AllService;
import view.frame.iframe.ILoginFrame;
import view.frame.imp.LoginFrameImp;
import view.frame.imp.MainFrameImp;


import java.rmi.Naming;

public class Main {
    public static void main(String[] args) throws Exception {
        AllService allService = null;

        try {
             allService = (AllService) Naming.lookup("rmi://LAPTOP-7ERSHT8P:7101/allService");
            System.out.println("Successfully connected to RMI service: " + allService);
        } catch (Exception e) {
            System.err.println("Failed to connect to RMI service: " + e.getMessage());
            e.printStackTrace();
        }
        FlatLightLaf.setup();
        ILoginFrame loginFrame = new LoginFrameImp();
        LoginController loginController = new LoginController(loginFrame,
                allService.getAccountService());
        loginController.show();

    }
}
