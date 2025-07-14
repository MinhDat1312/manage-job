package view.frame.imp;

import controller.MainController;
import entity.Employee;
import lombok.Getter;
import view.button.ButtonAnimate;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.iframe.ILoginFrame;
import view.frame.iframe.ilistener.ILoginListener;
import view.frame.iframe.IMainFrame;
import view.textfield.password.PasswordField;
import view.textfield.text.TextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.*;

public class LoginFrameImp extends JFrame
        implements ActionListener, FocusListener, ILoginFrame {

    private ILoginListener loginListener;

    JPanel panelRight, panelLeft;
    JDialog loadingDialog;
    JLabel imgLabel, loginTitle,
            emailLabel, passLabel;
    @Getter
    JProgressBar progressBar;
    TextField emailText;
    PasswordField passText;
    ButtonAnimate btnLogin;


    public LoginFrameImp() {
        setTitle("Đăng nhập");
        setSize(800, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        initComponent();
        addActionListener();
        addFocusListener();

    }

    public void initComponent() {
//		Phần login bên trái
        panelLeft = new JPanel();
        panelLeft.setPreferredSize(new Dimension(400, 500));
        panelLeft.setBackground(new Color(0, 102, 102));
        panelLeft.setLayout(new GridBagLayout());
        GridBagConstraints gbcL = new GridBagConstraints();
        gbcL.anchor = GridBagConstraints.CENTER;

        imgLabel = new JLabel();
        ImageIcon imgIcon = new ImageIcon(getClass().getResource("/image/Logo2.png"));
        Image image = imgIcon.getImage().getScaledInstance(140, 160, Image.SCALE_SMOOTH);
        imgLabel.setIcon(new ImageIcon(image));
        panelLeft.add(imgLabel, gbcL);

        add(panelLeft, BorderLayout.WEST);

//		Phần login bên phải
        panelRight = new JPanel();
        panelRight.setPreferredSize(new Dimension(400, 500));
        panelRight.setBackground(new Color(255, 255, 255, 255));
        panelRight.setLayout(new GridBagLayout());
        GridBagConstraints gbcR = new GridBagConstraints();

        loginTitle = new JLabel("ĐĂNG NHẬP");
        loginTitle.setFont(new Font("Segoe UI", 1, 30));
        loginTitle.setForeground(new Color(0, 102, 102));
        gbcR.anchor = GridBagConstraints.CENTER;
        gbcR.insets = new Insets(0, 5, 20, 5);
        gbcR.gridwidth = GridBagConstraints.REMAINDER;
        panelRight.add(loginTitle, gbcR);

        emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Segoe UI", 1, 14));
        emailLabel.setBackground(new Color(102, 102, 102));
        gbcR.anchor = GridBagConstraints.WEST;
        gbcR.gridy = 1;
        gbcR.insets = new Insets(5, 10, 5, 5);
        panelRight.add(emailLabel, gbcR);

        emailText = new TextField();
        emailText.setShadowColor(new Color(0, 102, 102));
        emailText.setPreferredSize(new Dimension(350, 45));
        emailText.setFont(new Font("Segoe UI", 0, 13));
        gbcR.gridy = 2;
        gbcR.insets = new Insets(5, 5, 5, 5);
        panelRight.add(emailText, gbcR);

        passLabel = new JLabel("Mật khẩu");
        passLabel.setFont(new Font("Segoe UI", 1, 14));
        passLabel.setBackground(new Color(102, 102, 102));
        gbcR.gridy = 3;
        gbcR.insets = new Insets(5, 10, 5, 5);
        panelRight.add(passLabel, gbcR);

        passText = new PasswordField();
        passText.setPreferredSize(new Dimension(350, 45));
        passText.setFont(new Font("Segoe UI", 0, 13));
        passText.setShadowColor(new Color(0, 102, 102));
        gbcR.gridy = 4;
        gbcR.insets = new Insets(5, 5, 5, 5);
        panelRight.add(passText, gbcR);

        btnLogin = new ButtonAnimate();
        btnLogin.setText("Đăng Nhập");
        btnLogin.setFont(new Font("Segoe UI", 1, 14));
        btnLogin.setForeground(new Color(250, 250, 250));
        btnLogin.setBackground(new Color(0, 102, 102));
        btnLogin.setPreferredSize(new Dimension(200, 35));
        btnLogin.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"),
                "Enter pressed");
        btnLogin.getActionMap().put("Enter pressed", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                loginListener.onLogin(getEmailText(), getPasswordText());
            }
        });
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(0, 80, 80));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(0, 102, 102));
            }
        });

        gbcR.gridy = 5;
        gbcR.gridwidth = GridBagConstraints.REMAINDER;
        gbcR.anchor = GridBagConstraints.CENTER;
        gbcR.fill = GridBagConstraints.HORIZONTAL;
        gbcR.insets = new Insets(20, 50, 20, 50);
        panelRight.add(btnLogin, gbcR);

        add(panelRight, BorderLayout.EAST);
    }

    public void addActionListener() {
        btnLogin.addActionListener(this);
    }

    public void addFocusListener() {
        emailText.addFocusListener(this);
        passText.addFocusListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        var obj = e.getSource();
        if (obj.equals(btnLogin)) {
            loginListener.onLogin(getEmailText(), getPasswordText());
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        // TODO Auto-generated method stub
        var obj=e.getSource();
        if(obj.equals(emailText)) {
            if(emailText.getText().isEmpty()) {
                emailText.setText(null);
                emailText.requestFocus();
                setEmailFieldColor(Color.GREEN);
            }
        }
        else if(obj.equals(passText)) {
            if(passText.getText().equals("")) {
                passText.setText(null);
                passText.requestFocus();
                setPasswordFieldColor(Color.GREEN);
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        // TODO Auto-generated method stub
        var obj=e.getSource();
        if(obj.equals(emailText)) {
            if(emailText.getText().length()==0) {
                setEmailFieldColor(Color.RED);
            }
        }
        else if(obj.equals(passText)) {
            if(passText.getText().length()==0) {
                setPasswordFieldColor(Color.RED);
            }
        }
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
    public String getEmailText() {
        return emailText.getText();
    }

    @Override
    public String getPasswordText() {
        return passText.getText();
    }

    @Override
    public void showLoading(boolean isLoading) {
        if(isLoading){
            loadingDialog = new JDialog(this, "Đang đăng nhập...", true);
            loadingDialog.setSize(300, 150);
            loadingDialog.setLocationRelativeTo(this);
            loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(new Color(0, 102, 102));
            JLabel loadingLabel = new JLabel("Vui lòng đợi...", SwingConstants.CENTER);
            loadingLabel.setFont(new Font("Segoe UI", 1, 13));
            loadingLabel.setForeground(new Color(250, 250, 250));
            progressBar = new JProgressBar(0, 100);
            progressBar.setStringPainted(true);
            progressBar.setPreferredSize(new Dimension(300, 20));

            panel.add(loadingLabel, BorderLayout.CENTER);
            panel.add(progressBar, BorderLayout.SOUTH);
            loadingDialog.add(panel);
            loadingDialog.setVisible(true);
        } else {
            if(loadingDialog != null) {
                loadingDialog.dispose();
            }
        }
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
        return this.getPanel();
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

    @Override
    public void setEmailFieldColor(Color color) {
        emailText.setShadowColor(color);
    }

    @Override
    public void setPasswordFieldColor(Color color) {
        passText.setShadowColor(color);
    }

    @Override
    public void showMainFrame(Employee employee, String role) {
        System.out.println("Login successful");
        SwingUtilities.invokeLater(() -> {
            IMainFrame mainFrame = new MainFrameImp(role);
            MainController mainController = new MainController(mainFrame, employee, role);
            mainController.show();
        });
    }

    @Override
    public void setOnLoginListener(ILoginListener listener) {
        this.loginListener = listener;
    }
}
