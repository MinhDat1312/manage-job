package view.frame.imp;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import entity.Employee;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.iframe.IHomeFrame;

public class HomeFrameImp extends JFrame implements IHomeFrame {

    Employee employee;
    HomeFrameImp parent;
    JPanel homePanel, imgPanel;

    public HomeFrameImp(Employee employee) {
        this.employee=employee;
        this.parent=this;

//		Tạo component bên phải
        initComponent();
    }

    public void initComponent() {
        homePanel=new JPanel();
        homePanel.setLayout(new BorderLayout(10, 10));

        imgPanel=new JPanel(); imgPanel.setPreferredSize(new Dimension(1100, 800));
		imgPanel.setBackground(Color.WHITE);
		JLabel imgLabel=new JLabel();
		ImageIcon imgIcon=new ImageIcon(getClass().getResource("/image/timvieclam.png"));
		Image img=imgIcon.getImage().getScaledInstance(1600, 800, Image.SCALE_SMOOTH);
		imgLabel.setIcon(new ImageIcon(img));
		imgPanel.add(imgLabel);

        homePanel.add(imgPanel);
    }


    @Override
    public void visible() {
        setVisible(true);
    }

    @Override
    public void close() {
        this.dispose();
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }

    @Override
    public void setFrameListener(IFrameListener listener) {

    }

    @Override
    public JPanel getPanel() {
        return this.homePanel;
    }

    @Override
    public void showData(List data, Object... objects) {

    }

    @Override
    public JTable getTable() {
        return null;
    }

    @Override
    public Object getSearchCriteria() {
        return null;
    }

    @Override
    public String getSearchText() {
        return "";
    }

    @Override
    public int getSelectedRow() {
        return 0;
    }
}
