package view.frame.iframe;

import entity.Employee;
import view.frame.iframe.ilistener.ILoginListener;

import java.awt.*;

public interface ILoginFrame extends IFrame{
    String getEmailText();
    String getPasswordText();
    void showLoading(boolean isLoading);
    void setEmailFieldColor(Color color);
    void setPasswordFieldColor(Color color);
    void showMainFrame(Employee employee, String role);
    void setOnLoginListener(ILoginListener listener);
}
