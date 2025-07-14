package view.panel;

import view.table.TableActionEvent;
import view.button.ButtonAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelActionViewUpdateDelete extends JPanel {

    private ButtonAction update;
    private ButtonAction delete;
    private ButtonAction view;
    private int row;

    public PanelActionViewUpdateDelete() {
        initComponent();
    }

    public void initComponent() {
        update=new ButtonAction();
        update.setIcon(new ImageIcon(getClass().getResource("/image/icon/update.png")));
        delete=new ButtonAction();
        delete.setIcon(new ImageIcon(getClass().getResource("/image/icon/delete.png")));
        view=new ButtonAction();
        view.setIcon(new ImageIcon(getClass().getResource("/image/icon/view.png")));

        this.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
        add(update); add(delete); add(view);
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

        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                event.onViewDetail(row);
            }
        });
    }

}
