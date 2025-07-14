package view.frame.iframe;

import entity.Employee;
import view.frame.iframe.ilistener.IMenuListener;

import javax.swing.*;

public interface IMainFrame extends IFrame {
    void showPanel(String name);
    void addPanel(JPanel panel, String name);
    void setUserInfo(Employee employee, String role);
    void setMenuListener(IMenuListener listener);
}
