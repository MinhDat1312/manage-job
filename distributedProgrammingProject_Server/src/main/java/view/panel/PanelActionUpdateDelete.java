package view.panel;

import view.table.TableActionEvent;
import view.button.ButtonAction;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelActionUpdateDelete extends JPanel {
	
	private ButtonAction update;
	private ButtonAction delete;
	private int row;
	
	public PanelActionUpdateDelete() {
        initComponent();
    }
	
	public void initComponent() {
		update=new ButtonAction();
		update.setIcon(new ImageIcon(getClass().getResource("/image/icon/update.png")));
		delete=new ButtonAction();
		delete.setIcon(new ImageIcon(getClass().getResource("/image/icon/delete.png")));

		this.setLayout(new FlowLayout(FlowLayout.CENTER,15,0));
		add(update); add(delete);
	}

	public void initEvent(TableActionEvent event, int row) {
		this.row=row;
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				event.onUpdate(row);
			}
		});
		
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				event.onDelete(row);
			}
		});
	}

}
