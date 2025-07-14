package view.table.renderer;

import view.panel.PanelActionUpdateDelete;
import view.panel.PanelActionViewUpdateDelete;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TableCellRendererViewUpdateDelete extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        // TODO Auto-generated method stub
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        PanelActionViewUpdateDelete action = new PanelActionViewUpdateDelete();
        if (isSelected == false && row % 2 == 0) {
            action.setBackground(Color.decode("#5faeb1"));
        }
        else if(isSelected){
            action.setBackground(Color.decode("#33FF33"));
        }
        else {
            action.setBackground(com.getBackground());
        }
        return action;
    }
}
