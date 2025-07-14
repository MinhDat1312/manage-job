package view.table.editor;

import view.table.TableActionEvent;
import view.panel.PanelActionViewCreateJob;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableCellEditorViewCreateJob extends DefaultCellEditor{
	
	private TableActionEvent event;
	
	public TableCellEditorViewCreateJob(TableActionEvent event) {
		super(new JCheckBox());
		this.event=event;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		PanelActionViewCreateJob action = new PanelActionViewCreateJob();
        action.initEvent(event, row);
        
        return action;
	}
	

}