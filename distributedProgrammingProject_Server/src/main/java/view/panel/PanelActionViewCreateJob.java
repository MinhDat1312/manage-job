package view.panel;

import view.table.TableActionEvent;
import view.button.ButtonAction;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelActionViewCreateJob extends JPanel {
	
	private ButtonAction view;
	private ButtonAction create;
	
	public PanelActionViewCreateJob() {
        initComponent();
    }
	
	public void initComponent() {
		view=new ButtonAction();
		view.setIcon(new ImageIcon(getClass().getResource("/image/icon/view.png")));
		create=new ButtonAction();
		create.setIcon(new ImageIcon(getClass().getResource("/image/icon/tintuyendung24.png")));
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER,15,0));
		add(view); add(create);
	}

	public void initEvent(TableActionEvent event, int row) {
		view.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				event.onViewTinTuyenDung(row);;
			}
		});
		
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				event.onCreateTinTuyenDung(row);
			}
		});
	}
}