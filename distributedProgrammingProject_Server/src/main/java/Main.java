import com.formdev.flatlaf.FlatLightLaf;
import controller.LoginController;
import dao.AccountDAO;
import entity.Account;
import service.imp.AccountServiceImp;
import service.imp.AllServiceImp;
import service.inteface.AllService;
import view.frame.iframe.ILoginFrame;
import view.frame.imp.LoginFrameImp;


public class Main {
    public static void main(String[] args) throws Exception {
        FlatLightLaf.setup();
        ILoginFrame loginFrame = new LoginFrameImp();
        AllService allService = new AllServiceImp();
        LoginController loginController = new LoginController(loginFrame,
                allService.getAccountService());
        loginController.show();
    }
}
