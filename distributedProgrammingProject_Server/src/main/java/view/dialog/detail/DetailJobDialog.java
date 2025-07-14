package view.dialog.detail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import util.DateFormatter;
import entity.Recruiter;
import entity.Job;
import entity.constant.WorkingType;
import entity.constant.Level;
import view.button.Button;
import view.label.ComboBoxRenderer;
import view.panel.GradientPanel;

public class DetailJobDialog extends JDialog implements ActionListener{

	GradientPanel inforJobPanel, btnPanel;
	JLabel idLabel, nameLabel, typeLabel, startLabel, endLabel, levelLabel, addressLabel,titleLabel,
			statusLabel, descriptionLabel,
			numberLabel, salaryLabel, professionLabel;
	JTextField idText, nameText, addressText, titleText,
			numberText, salaryText;
	JComboBox levelBox, statusBox, typeBox, professionBox;
	UtilDateModel modelDateStart, modelDateEnd;
	JDatePickerImpl startText, endText;
	JTextArea descriptionText;
	JScrollPane scrollDescription;
	Button btnAdd, btnReset;
	GridBagConstraints gbc;


	private Job job;
	private Recruiter recruiter;

	public DetailJobDialog(Dialog parent, boolean modal, Job job, Recruiter recruiter) {
		super(parent, modal);
		setTitle("Xem chi tiết tin tuyển dụng");
		setResizable(false);
		setSize(800,680);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.job=job;
		this.recruiter=recruiter;

		initComponent();

		addActionListener();

//		loadData();
	}

	public DetailJobDialog(Frame parent, boolean modal, Job job, Recruiter recruiter) {
		super(parent, modal);
		setTitle("Xem chi tiết tin tuyển dụng");
		setResizable(false);
		setSize(800,680);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.job=job;
		this.recruiter=recruiter;

		initComponent();

		addActionListener();

//		loadData();
	}

	public void initComponent() {
		inforJobPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		inforJobPanel.setBackground(Color.WHITE);
		inforJobPanel.setLayout(new GridBagLayout());
		inforJobPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		gbc= new GridBagConstraints();

//		Thông tin tin tuyển dụng
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.WEST;
		idLabel=new JLabel("Mã tin tuyển dụng"); idLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(idLabel, gbc);
		gbc.gridx=0; gbc.gridy=1;
		idText=new JTextField(10); idText.setFont(new Font("Segoe UI",0,16));
		idText.setEditable(false);
		inforJobPanel.add(idText, gbc);

		gbc.gridx=1; gbc.gridy=0;
		statusLabel=new JLabel("Trạng thái"); statusLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(statusLabel, gbc);
		gbc.gridx=1; gbc.gridy=1;
		String[] trangthais= {"Khả dụng", "Không khả dụng"};
		statusBox=new JComboBox(trangthais); statusBox.setFont(new Font("Segoe UI",0,16));
		statusBox.setPreferredSize(new Dimension(160,26));
		inforJobPanel.add(statusBox, gbc);

		gbc.gridx=2; gbc.gridy=0;
		nameLabel=new JLabel("Nhà tuyển dụng"); nameLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(nameLabel, gbc);
		gbc.gridx=2; gbc.gridy=1; gbc.gridwidth=2;
		nameText=new JTextField(23); nameText.setFont(new Font("Segoe UI",0,16));
		nameText.setEditable(false);
		inforJobPanel.add(nameText, gbc);

		gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=1;
		titleLabel=new JLabel("Tiêu đề"); titleLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(titleLabel,gbc);
		gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2;
		titleText=new JTextField(23); titleText.setFont(new Font("Segoe UI",0,16));
		titleText.setEditable(false);
		inforJobPanel.add(titleText,gbc);

		gbc.gridx=2; gbc.gridy=2; gbc.gridwidth=1;
		typeLabel=new JLabel("Hình thức làm việc"); typeLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(typeLabel, gbc);
		gbc.gridx=2; gbc.gridy=3;
		typeBox=new JComboBox(); typeBox.setFont(new Font("Segoe UI",0,16));
		WorkingType[] hinhthucs= WorkingType.class.getEnumConstants();
		for(WorkingType h: hinhthucs) {
			typeBox.addItem(h.getValue());
		}
		typeBox.setPreferredSize(new Dimension(156,25));
		inforJobPanel.add(typeBox,gbc);

		gbc.gridx=3; gbc.gridy=2;
		levelLabel=new JLabel("Trình độ"); levelLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(levelLabel, gbc);
		gbc.gridx=3; gbc.gridy=3;
		levelBox=new JComboBox(); levelBox.setFont(new Font("Segoe UI",0,16));
		Level[] trinhdos= Level.class.getEnumConstants();
		for(Level t: trinhdos) {
			levelBox.addItem(t.getValue());
		}
		levelBox.setPreferredSize(new Dimension(150,25));
		inforJobPanel.add(levelBox, gbc);

		gbc.gridx=0; gbc.gridy=4;
		numberLabel=new JLabel("Số lượng"); numberLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(numberLabel, gbc);
		gbc.gridx=0; gbc.gridy=5;
		numberText=new JTextField(10); numberText.setFont(new Font("Segoe UI",0,16));
		numberText.setEditable(false);
		inforJobPanel.add(numberText, gbc);

		gbc.gridx=1; gbc.gridy=4;
		salaryLabel=new JLabel("Lương"); salaryLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(salaryLabel, gbc);
		gbc.gridx=1; gbc.gridy=5;
		salaryText=new JTextField(11); salaryText.setFont(new Font("Segoe UI",0,16));
		salaryText.setEditable(false);
		inforJobPanel.add(salaryText, gbc);

		gbc.gridx=2; gbc.gridy=4;
		professionLabel=new JLabel("Ngành nghề"); professionLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(professionLabel, gbc);
		gbc.gridx=2; gbc.gridy=5; gbc.gridwidth=2;
		professionBox = new JComboBox<String>();
		professionBox.setFont(new Font("Segoe UI",0,16));
		professionBox.setPreferredSize(new Dimension(328,25));
		professionBox.setRenderer(new ComboBoxRenderer("Chọn ngành nghề"));
//		Profession[] nganhnghes= Profession.class.getEnumConstants();
//		for(Profession n: nganhnghes) {
//			professionBox.addItem(n.getValue());
//		}
		professionBox.setSelectedIndex(-1);
		inforJobPanel.add(professionBox, gbc);

		gbc.gridx=0; gbc.gridy=6; gbc.gridwidth=1;
		startLabel=new JLabel("Ngày đăng tin"); startLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(startLabel, gbc);
		gbc.gridx=0; gbc.gridy=7;
		modelDateStart=new UtilDateModel();
		modelDateStart.setSelected(true);
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year","Year");
		JDatePanelImpl panelStart=new JDatePanelImpl(modelDateStart, p);
		startText=new JDatePickerImpl(panelStart, new DateFormatter());
		startText.setPreferredSize(new Dimension(140, 24));
		startText.getComponent(1).setEnabled(false);
		inforJobPanel.add(startText, gbc);

		gbc.gridx=1; gbc.gridy=6;
		endLabel=new JLabel("Ngày hết hạn"); endLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(endLabel, gbc);
		gbc.gridx=1; gbc.gridy=7;
		modelDateEnd=new UtilDateModel();
		modelDateEnd.setSelected(true);
		Properties q=new Properties();
		q.put("text.day", "Day"); q.put("text.month", "Month"); q.put("text.year","Year");
		JDatePanelImpl panelEnd=new JDatePanelImpl(modelDateEnd, q);
		endText=new JDatePickerImpl(panelEnd, new DateFormatter());
		endText.setPreferredSize(new Dimension(157, 24));
		endText.getComponent(1).setEnabled(false);
		inforJobPanel.add(endText, gbc);

		gbc.gridx=0; gbc.gridy=8;
		descriptionLabel=new JLabel("Mô tả công việc"); descriptionLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(descriptionLabel, gbc);
		gbc.gridx=0; gbc.gridy=9; gbc.gridwidth=4;
		descriptionText=new JTextArea(10, 48); descriptionText.setFont(new Font("Segoe UI",0,16));
		descriptionText.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		descriptionText.setLineWrap(true);
		descriptionText.setWrapStyleWord(true);
		descriptionText.setEditable(false);
		scrollDescription=new JScrollPane(descriptionText);
		inforJobPanel.add(scrollDescription, gbc);

		add(inforJobPanel, BorderLayout.CENTER);

//		Button
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 50));

		btnReset=new Button("Hủy"); btnReset.setFont(new Font("Segoe UI",0,16));
		btnReset.setPreferredSize(new Dimension(120,25));
		btnReset.setBackground(Color.RED);
		btnReset.setForeground(Color.WHITE);

		btnPanel.add(btnReset);

		add(btnPanel, BorderLayout.SOUTH);
	}

	public void loadData() {
//		idText.setText(ttd.getMaTTD());
//		statusBox.setSelectedIndex(ttd.isTrangThai()?0:1);
//		nameText.setText(ntd.getTenNTD());
//		titleText.setText(ttd.getTieuDe());
//		for(int i=0;i<typeBox.getItemCount();i++) {
//			if(typeBox.getItemAt(i).toString().equalsIgnoreCase(ttd.getHinhThuc().getValue())) {
//				typeBox.setSelectedIndex(i);
//				break;
//			}
//		}
//		for(int i=0;i<levelBox.getItemCount();i++) {
//			if(levelBox.getItemAt(i).toString().equalsIgnoreCase(ttd.getTrinhDo().getValue())) {
//				levelBox.setSelectedIndex(i);
//				break;
//			}
//		}
//		for(int i=0;i<professionBox.getItemCount();i++) {
//			if(professionBox.getItemAt(i).toString().equalsIgnoreCase(ttd.getNganhNghe().getValue())) {
//				professionBox.setSelectedIndex(i);
//				break;
//			}
//		}
//		numberText.setText(String.valueOf(ttd.getSoLuong()));
//		salaryText.setText(String.valueOf((int)ttd.getLuong()));
//		modelDateStart.setValue(Date.from(ttd.getNgayDangTin().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//		modelDateEnd.setValue(Date.from(ttd.getNgayHetHan().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//		descriptionText.setText(ttd.getMoTa());
	}

	public void addActionListener() {
		btnReset.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnReset)) {
			this.dispose();
		}
	}
}
