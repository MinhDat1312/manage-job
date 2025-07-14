package view.panel;

import view.chart.PolarPieChart;

import javax.swing.*;
import java.awt.*;

public class PanelCircleChart extends JPanel {
    private PolarPieChart chart;
    private String title;
    private JPanel note;

    public PanelCircleChart(PolarPieChart chart, String title, JPanel note) {
        this.chart=chart;
        this.title=title;
        this.note=note;
    }

    public JPanel createPanelCircelChart() {
        JPanel panel=new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

        JLabel titleLabel=new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI",1,16));
        titleLabel.setForeground(Color.WHITE);

        JPanel res=new JPanel();
        res.setOpaque(false);
        res.add(chart);
        res.add(note);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(res, BorderLayout.CENTER);

        return panel;
    }
}
