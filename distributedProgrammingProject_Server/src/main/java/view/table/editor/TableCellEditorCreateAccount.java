package view.table.editor;

import view.table.TableActionEvent;
import view.panel.PanelActionCreateAccount;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableCellEditorCreateAccount extends DefaultCellEditor{
	
	private TableActionEvent event;
	
	public TableCellEditorCreateAccount(TableActionEvent event) {
		super(new JCheckBox());
		this.event=event;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		PanelActionCreateAccount action= new PanelActionCreateAccount();
        action.initEvent(event, row);
        
        return action;
	}
	

}
