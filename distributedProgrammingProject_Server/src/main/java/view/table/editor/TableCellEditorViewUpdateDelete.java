package view.table.editor;

import view.table.TableActionEvent;
import view.panel.PanelActionViewUpdateDelete;

import javax.swing.*;
import java.awt.*;

public class TableCellEditorViewUpdateDelete extends DefaultCellEditor {

    private TableActionEvent event;

    public TableCellEditorViewUpdateDelete(TableActionEvent event) {
        super(new JCheckBox());
        this.event=event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // TODO Auto-generated method stub
        PanelActionViewUpdateDelete action= new PanelActionViewUpdateDelete();
        action.initEvent(event, row);

        return action;
    }


}
