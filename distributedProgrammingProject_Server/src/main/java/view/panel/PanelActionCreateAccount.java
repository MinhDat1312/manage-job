package view.panel;

import view.table.TableActionEvent;
import view.button.ButtonAction;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelActionCreateAccount extends JPanel {
	
	private ButtonAction addUser;
	
	public PanelActionCreateAccount() {
        initComponent();
    }
	
	public void initComponent() {
		addUser=new ButtonAction();
		addUser.setIcon(new ImageIcon(getClass().getResource("/image/icon/addUser.png")));
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER,15,0));
		add(addUser);
	}

	public void initEvent(TableActionEvent event, int row) {
		addUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				event.onCreateTaiKhoan(row);
			}
		});
	}
}
