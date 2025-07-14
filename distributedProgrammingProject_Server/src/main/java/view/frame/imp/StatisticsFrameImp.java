package view.frame.imp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.*;

import entity.Account;
import entity.Invoice;
import lombok.Getter;
import util.RandomColor;
import view.chart.*;
import entity.Employee;
import entity.constant.Month;
import view.button.Button;
import view.frame.StatisticsEmployeeFrame;
import view.frame.StatisticsInvoiceFrame;
import view.frame.StatisticsJobFrame;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.iframe.IStatisticsFrame;
import view.panel.GradientRoundPanel;
import view.panel.PanelCircleChart;


public class StatisticsFrameImp extends JFrame implements ActionListener, IStatisticsFrame {

	private Employee employee;
	private StatisticsFrameImp parent;
	private ArrayList<Integer> years;
	private IFrameListener frameListener;

//	Component thống kê
	JPanel statisticsPanel, northPanel, centerPanel, northWestPanel,
		northNorthPanel, wrapPanel, titlePanel;
	Button btnStatisticsEmployee, btnStatisticsJob, btnStatisticsInvoice;
	@Getter
	JComboBox yearBox;

	public StatisticsFrameImp(Employee employee) {
		this.employee=employee;
		this.parent=this;
		this.years=new ArrayList<Integer>();

//		Tạo component bên phải
		initComponent();
		addActionListener();
	}

	public void initComponent() {
		statisticsPanel=new JPanel();
		statisticsPanel.setLayout(new BorderLayout(5,5));
		statisticsPanel.setBackground(new Color(89, 145, 144));
		statisticsPanel.setBackground(new Color(89, 145, 144));

//		Các nút thống kê
		btnStatisticsInvoice = createButton("Thống kê hợp đồng");
		btnStatisticsJob = createButton("Thống kê tin tuyển dụng");
		btnStatisticsEmployee = createButton("Thống kê nhân viên");

		northNorthPanel=new GradientRoundPanel();
		northNorthPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 19, 5));
		northNorthPanel.add(btnStatisticsInvoice);
		northNorthPanel.add(btnStatisticsJob);
		northNorthPanel.add(btnStatisticsEmployee);

//		Biểu đồ tròn và đường
		northPanel=new JPanel();
		northPanel.setOpaque(false);
		northPanel.setLayout(new BorderLayout(5,5));

//		Biểu đồ tròn
		northWestPanel= new GradientRoundPanel();
		northWestPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
//		thống kê theo hình thức
//		thống kê theo trình độ
//		thống kê theo ngành nghề
		northPanel.add(northWestPanel, BorderLayout.CENTER);

//		Biểu đồ đường
		centerPanel=new GradientRoundPanel();
		centerPanel.setLayout(new BorderLayout());

		titlePanel=new JPanel();
		titlePanel.setOpaque(false);

		JLabel titleLabel=new JLabel("Tổng doanh thu hợp đồng theo tháng của nhân viên", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Segoe UI",1,18));
		titleLabel.setForeground(Color.WHITE);
		titlePanel.add(titleLabel);

		yearBox=new JComboBox();
		yearBox.setFont(new Font("Segoe UI",0,16));
		yearBox.setBackground(new Color(89, 145, 144));
		yearBox.setForeground(Color.WHITE);
		yearBox.addItem("Chọn năm");
		titlePanel.add(yearBox);

		centerPanel.add(titlePanel, BorderLayout.NORTH);

		wrapPanel= new JPanel();
		wrapPanel.setOpaque(false);
		wrapPanel.setLayout(new BorderLayout(5,5));
		wrapPanel.add(northPanel, BorderLayout.NORTH);
		wrapPanel.add(centerPanel, BorderLayout.CENTER);

//		statisticsPanel.add(northNorthPanel, BorderLayout.NORTH);
		statisticsPanel.add(wrapPanel, BorderLayout.CENTER);

		add(statisticsPanel);
	}

	public Button createButton(String title) {
		Button btn=new Button(title);
		btn.setFont(new Font("Segoe UI",0,16));
		btn.setPreferredSize(new Dimension(240,25));
		btn.setBackground(new Color(0,102,102));
		btn.setForeground(Color.WHITE);
		return btn;
	}

	public void addActionListener() {
		btnStatisticsInvoice.addActionListener(this);
		btnStatisticsJob.addActionListener(this);
		btnStatisticsEmployee.addActionListener(this);

		yearBox.addActionListener(this);
	}

	@Override
	public JPanel getPanel() {
		return this.statisticsPanel;
	}

	@Override
	public void visible() {
		setVisible(true);
	}

	@Override
	public void close() {
		dispose();
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(rootPane, message);
	}

	@Override
	public void setFrameListener(IFrameListener listener) {
		this.frameListener = listener;
	}

	@Override
	public void showData(List data, Object... objects) {}

	@Override
	public JTable getTable() {
		return null;
	}

	@Override
	public Object getSearchCriteria() {
		return null;
	}

	@Override
	public String getSearchText() {
		return "";
	}

	@Override
	public int getSelectedRow() {
		return 0;
	}

	@Override
	public void addYearsBox(List<Invoice> invoice) {
		for(Invoice i: invoice) {
			int year = i.getCreatedDate().getYear();
			if(!years.contains(year)) {
				years.add(year);
			}
		}
		years.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o2-o1;
			}
		});
		yearBox.removeAllItems();
		yearBox.addItem("Chọn năm");
		for(Integer y: years) {
			yearBox.addItem(y);
		}
	}

	@Override
	public void createPieChart(Map<?, Long> statistics, String title) {
		PolarPieChart panel=new PolarPieChart();
		panel.setChartType(PolarPieChart.PeiChartType.DONUT_CHART);
		Set<Color> tmp = new HashSet<>();
		statistics.entrySet()
			.forEach(entry -> {
				Color c;
				do{
					c = RandomColor.getRandomColor();
				}while(c.equals(Color.WHITE) || tmp.contains(c));
				try {
					Method getValueMethod = entry.getKey() instanceof Enum<?>
							? entry.getKey().getClass().getMethod("getValue")
							: null;
					panel.addData(new ModelPieChart(c,
							getValueMethod != null
							? getValueMethod.invoke(entry.getKey()).toString()
							: entry.getKey().toString(),
							entry.getValue())
					);
					tmp.add(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		JPanel note=new JPanel();
		note.setOpaque(false);
		note.setLayout(new GridLayout(panel.getList().size(),1));
		for(ModelPieChart i: panel.getList()) {
			PolarAreaLabel label=new PolarAreaLabel();
			label.setText(i.getName());
			label.setBackground(i.getColor());
			label.setForeground(Color.WHITE);
			label.setFont(new Font("Segoe UI",1,13));
			note.add(label);
		}
		northWestPanel.add(
				new PanelCircleChart(panel, title, note)
						.createPanelCircelChart()
		);
	}

	@Override
	public void createLineChart(Object... objects) {
		List<Account> accounts = (List<Account>) objects[0];
		Map<Integer, List<Double>> data = (Map<Integer, List<Double>>) objects[1];

		LineChart lineChart=new LineChart();
		lineChart.setOpaque(false);
		Set<Color> tmp = new HashSet<>();
		for(Account account:accounts) {
			Color c;
			do{
				c = RandomColor.getRandomColor();
			}while(c.equals(Color.WHITE) || tmp.contains(c));
			lineChart.addLegend(account.getEmployee().getName(), c, c.darker());
			tmp.add(c);
		}
		data.entrySet()
				.forEach(entry -> {
					lineChart.addData(new ModelChart(
							"Tháng "+String.valueOf(entry.getKey()),
							entry.getValue().stream().mapToDouble(Double::doubleValue).toArray()
					));
				});

		lineChart.start();
		centerPanel.removeAll();
		centerPanel.add(titlePanel, BorderLayout.NORTH);
		centerPanel.add(lineChart, BorderLayout.CENTER);
		centerPanel.revalidate();
		centerPanel.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
//		if(obj.equals(btnStatisticsJob)) {
//			wrapPanel.removeAll();
//			wrapPanel.add(new StatisticsJobFrame(employee).getPanel());
//			wrapPanel.revalidate();
//			wrapPanel.repaint();
//		}
//		else if(obj.equals(btnStatisticsInvoice)) {
//			wrapPanel.removeAll();
//			wrapPanel.add(new StatisticsInvoiceFrame(employee).getPanel());
//			wrapPanel.revalidate();
//			wrapPanel.repaint();
//		}
//		else if(obj.equals(btnStatisticsEmployee)) {
//			wrapPanel.removeAll();
//			wrapPanel.add(new StatisticsEmployeeFrame(employee).getPanel());
//			wrapPanel.revalidate();
//			wrapPanel.repaint();
//		}
		if(obj.equals(yearBox)) {
			int year=-1;
			if(yearBox.getSelectedItem() != null
				&& !yearBox.getSelectedItem().toString().equalsIgnoreCase("Chọn năm")) {
				year=Integer.parseInt(yearBox.getSelectedItem().toString());
				frameListener.onUpdate(year);
			}
		}
	}

}
