package view.frame.imp;

import javax.swing.*;

import entity.Employee;
import view.button.MenuButton;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.iframe.IMainFrame;
import view.frame.iframe.ilistener.IMenuListener;
import view.panel.MenuPanel;
import view.panel.RoundPanel;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainFrameImp extends JFrame
		implements IMainFrame {

	private IMenuListener menuListener;
	private Map<String, JPanel > panelMap;
	private String role;

//	Component
	MenuPanel menu;
	JPanel leftPanel, menuPanel, mainPanel,northPanel;
	JLabel userLabel, iconUserLabel, roleLeftLabel;
	RoundPanel centerPanel;

	CardLayout cardLayout;

	public MainFrameImp(String role) {
		setTitle("Dịch vụ tìm việc làm");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		this.role = role;
		panelMap = new HashMap<>();

//		Tạo menu bar bên trái
		initLeft();
//		Tạo component bên phải
		initComponent();
//		Thêm vào frame
		add(leftPanel, BorderLayout.WEST);
		add(mainPanel, BorderLayout.CENTER);

	}

	public void initLeft() {
		leftPanel=new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(new Color(89, 145, 144));

		ImageIcon imgIcon = new ImageIcon(getClass().getResource("/image/Logo2.png"));
		Image image = imgIcon.getImage().getScaledInstance(30, 40, Image.SCALE_SMOOTH);
		roleLeftLabel=new JLabel("", SwingConstants.CENTER);
		roleLeftLabel.setFont(new Font("Segoe UI",1,16));
		roleLeftLabel.setForeground(Color.WHITE);
		roleLeftLabel.setPreferredSize(new Dimension(getWidth(), 50));
		roleLeftLabel.setIcon(new ImageIcon(image));

		menu=new MenuPanel(this);

		menuPanel=new JPanel();
		menuPanel.setLayout(new BorderLayout());
		menuPanel.setBackground(Color.decode("#259195"));
		menuPanel.add(roleLeftLabel, BorderLayout.NORTH);
		menuPanel.add(menu, BorderLayout.CENTER);

		leftPanel.add(menuPanel);
	}

	public void initComponent() {
		mainPanel=new JPanel();
		mainPanel.setLayout(new BorderLayout(5,5));
		mainPanel.setBackground(new Color(89, 145, 144));

//		Hiển thị tài khoản
		northPanel=new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		northPanel.setBackground(new Color(89, 145, 144));

		userLabel=new JLabel();
		userLabel.setFont(new Font("Segoe UI",1,16));
		userLabel.setForeground(Color.WHITE);
		iconUserLabel=new JLabel();
		iconUserLabel.setIcon(new ImageIcon(getClass().getResource("/image/icon/user.png")));

		northPanel.add(userLabel); northPanel.add(iconUserLabel);

//		Hiển thị nội dung của từng menu
		centerPanel=new RoundPanel();
		cardLayout = new CardLayout();
		centerPanel.setLayout(cardLayout);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanel.setBackground(new Color(89, 145, 144));

		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
	}

	public String getVaiTro() {
		return role;
	}

	private void setSelected(MenuButton menuButton) {
        for (Component com : menu.getComponents()) {
            if (com instanceof MenuButton) {
				MenuButton b = (MenuButton) com;
                b.setSelected(false);
            }
        }
        menuButton.setSelected(true);
    }

	@Override
	public void showPanel(String name) {
		if(panelMap.containsKey(name)) {
			cardLayout.show(centerPanel, name);
			setTitle(name);
			centerPanel.revalidate();
			centerPanel.repaint();
		}
	}

	@Override
	public void addPanel(JPanel panel, String name) {
		panelMap.put(name, panel);
		centerPanel.add(panel, name);
	}

	@Override
	public void setUserInfo(Employee employee, String role) {
		roleLeftLabel.setText(role);
		userLabel.setText("Welcome " + employee.getName());
	}

	@Override
	public void setMenuListener(IMenuListener listener) {
		this.menuListener = listener;
		for(Component c: menu.getComponents()) {
			if(c.getClass().equals(MenuButton.class)) {
				((MenuButton)c).addActionListener(e -> {
					MenuButton button = (MenuButton) e.getSource();
					setSelected(button);
					if(menuListener != null) {
                        try {
                            menuListener.onMenuListener(button.getText());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
				});
			}
		}
	}

	@Override
	public void visible() {
		setVisible(true);
		for (Component com : menu.getComponents()) {
			if (com instanceof MenuButton) {
				if(((MenuButton)com).getText().equals("Trang chủ")){
					setSelected(((MenuButton)com));
				}
			}
		}
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
		return this.centerPanel;
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
