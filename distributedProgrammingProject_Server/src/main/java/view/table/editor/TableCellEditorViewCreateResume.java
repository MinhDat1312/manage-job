package view.table.editor;

import view.table.TableActionEvent;
import view.panel.PanelActionViewCreateResume;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableCellEditorViewCreateResume extends DefaultCellEditor{
	
	private TableActionEvent event;
	
	public TableCellEditorViewCreateResume(TableActionEvent event) {
		super(new JCheckBox());
		this.event=event;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		PanelActionViewCreateResume action = new PanelActionViewCreateResume();
        action.initEvent(event, row);
        
        return action;
	}
	

}
