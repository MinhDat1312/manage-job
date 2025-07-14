package view.frame.imp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import entity.*;

import lombok.Getter;
import lombok.Setter;
import view.button.ButtonAction;
import entity.constant.Level;
import view.button.Button;
import view.frame.iframe.IJobFrame;
import view.frame.iframe.ilistener.IFrameListener;
import view.panel.GradientPanel;
import view.panel.GradientRoundPanel;
import view.panel.RoundPanel;

public class JobFrameImp extends JFrame
		implements ActionListener, MouseListener, FocusListener, IJobFrame {

	private Employee employee;
	private JobFrameImp parent;
	private IFrameListener<Job> frameListener;

	private ArrayList<Component> updates;
	private ArrayList<Component> deletes;
	@Getter
	@Setter
	private ArrayList<Button> btnCreateInvoices;
	private ArrayList<RoundPanel> panelJobs;

//	Component danh sách tin tuyển dụng
	JPanel jobPanel, centerPanelJob,
		logoPanel;
	JLabel searchNameLabel, searchSalaryLabel, searchRecuiterLabel, searchLevelLabel,
		titleResume,titleLabel, recruiterLabel, salaryLabel;
	@Getter
	JTextField searchNameText, searchSalaryText;
	Button btnSearch, btnReset;
	@Getter
	JComboBox searchRecruiterBox, searchLevelBox;
	JScrollPane scroll;
	GradientPanel danhsachCenterPanel;
	GradientRoundPanel timkiemPanel,
	danhsachPanel, danhsachNorthPanel;
	ButtonAction update, delete;

	public JobFrameImp(Employee employee) {
		this.employee=employee;
		this.parent=this;

		updates = new ArrayList<Component>();
		deletes = new ArrayList<Component>();
		btnCreateInvoices = new ArrayList<Button>();
		panelJobs = new ArrayList<RoundPanel>();

//		Tạo component bên phải
		initComponent();
//		Thêm sự kiện
		addActionListener();
		addFocusListener();
	}

	public void initComponent() {
		jobPanel=new JPanel();
		jobPanel.setLayout(new BorderLayout(5,5));

//		Hiển thị tìm kiếm và danh sách tin tuyển dụng
		centerPanelJob=new JPanel();
		centerPanelJob.setLayout(new BorderLayout(10, 10));
		centerPanelJob.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelJob.setBackground(new Color(89, 145, 144));
//		Tìm kiếm tin tuyển dụng
		timkiemPanel=new GradientRoundPanel();
		timkiemPanel.setLayout(new BorderLayout(5,5));

		JPanel resSearch=new JPanel();
		resSearch.setOpaque(false);
		resSearch.setLayout(new GridBagLayout());
		resSearch.setBackground(Color.WHITE);
		GridBagConstraints gbc= new GridBagConstraints();
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.EAST;
		searchNameLabel=new JLabel("Tiêu đề tin tuyển dụng:");
		searchNameLabel.setFont(new Font("Segoe UI",1,16));
		searchNameLabel.setForeground(Color.WHITE);
		resSearch.add(searchNameLabel, gbc);
		gbc.gridx=1; gbc.gridy=0; gbc.anchor=GridBagConstraints.WEST;
		searchNameText=new JTextField(15);
		searchNameText.setFont(new Font("Segoe UI",0,16));
		searchNameText.setOpaque(false);
		searchNameText.setOpaque(false);
		resSearch.add(searchNameText, gbc);

		gbc.gridx=2; gbc.gridy=0; gbc.anchor=GridBagConstraints.EAST;
		searchSalaryLabel=new JLabel("Mức lương:");
		searchSalaryLabel.setFont(new Font("Segoe UI",1,16));
		searchSalaryLabel.setForeground(Color.WHITE);
		resSearch.add(searchSalaryLabel, gbc);
		gbc.gridx=3; gbc.gridy=0; gbc.anchor=GridBagConstraints.WEST;
		searchSalaryText=new JTextField(15);
		searchSalaryText.setFont(new Font("Segoe UI",0,16));
		searchSalaryText.setOpaque(false);
		searchSalaryText.setForeground(Color.WHITE);
		resSearch.add(searchSalaryText, gbc);

		gbc.gridx=0; gbc.gridy=1; gbc.anchor=GridBagConstraints.EAST;
		searchRecuiterLabel=new JLabel("Nhà tuyển dụng:");
		searchRecuiterLabel.setFont(new Font("Segoe UI",1,16));
		searchRecuiterLabel.setForeground(Color.WHITE);
		resSearch.add(searchRecuiterLabel, gbc);
		gbc.gridx=1; gbc.gridy=1; gbc.anchor=GridBagConstraints.WEST;
		searchRecruiterBox=new JComboBox();
		searchRecruiterBox.setFont(new Font("Segoe UI",0,16));
		searchRecruiterBox.setForeground(Color.WHITE);
		searchRecruiterBox.setBackground(new Color(89, 145, 144));
		searchRecruiterBox.setPreferredSize(new Dimension(225,26));
		searchRecruiterBox.addItem("Chọn nhà tuyển dụng");
		resSearch.add(searchRecruiterBox, gbc);

		gbc.gridx=2; gbc.gridy=1; gbc.anchor=GridBagConstraints.EAST;
		searchLevelLabel=new JLabel("Trình độ:");
		searchLevelLabel.setFont(new Font("Segoe UI",1,16));
		searchLevelLabel.setForeground(Color.WHITE);
		resSearch.add(searchLevelLabel, gbc);
		gbc.gridx=3; gbc.gridy=1; gbc.anchor=GridBagConstraints.WEST;
		searchLevelBox=new JComboBox();
		searchLevelBox.setFont(new Font("Segoe UI",0,16));
		searchLevelBox.setBackground(new Color(89, 145, 144));
		searchLevelBox.setForeground(Color.WHITE);
		searchLevelBox.addItem("Chọn trình độ");
		Level[] trinhdos= Level.class.getEnumConstants();
		for(Level t: trinhdos) {
			searchLevelBox.addItem(t.getValue());
		}
		searchLevelBox.setPreferredSize(new Dimension(225,26));
		resSearch.add(searchLevelBox, gbc);

		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setLayout(new BorderLayout(0,5));
		resBtnSearch.setBorder(BorderFactory.createEmptyBorder(10,10,10,23));
		btnSearch=new Button("Tìm kiếm"); btnSearch.setFont(new Font("Segoe UI",0,16));
		btnSearch.setPreferredSize(new Dimension(120,25));
		btnSearch.setBackground(new Color(0,102,102));
		btnSearch.setForeground(Color.WHITE);
		btnReset=new Button("Làm lại"); btnReset.setFont(new Font("Segoe UI",0,16));
		btnReset.setPreferredSize(new Dimension(120,25));
		btnReset.setBackground(Color.RED);
		btnReset.setForeground(Color.WHITE);
		resBtnSearch.add(btnSearch, BorderLayout.NORTH); resBtnSearch.add(btnReset, BorderLayout.SOUTH);

		timkiemPanel.add(resSearch, BorderLayout.CENTER);
		timkiemPanel.add(resBtnSearch, BorderLayout.EAST);

//		Danh sách tin tuyển dụng
		danhsachPanel=new GradientRoundPanel();
		danhsachPanel.setLayout(new BorderLayout(10, 10));

		danhsachNorthPanel=new GradientRoundPanel();
		danhsachNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachNorthPanel.setBackground(Color.WHITE);
		titleResume=new JLabel("Danh sách tin tuyển dụng");
		titleResume.setFont(new Font("Segoe UI",1,16));
		titleResume.setForeground(Color.WHITE);
		titleResume.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleResume, BorderLayout.WEST);

		danhsachCenterPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#259195"));
		danhsachCenterPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,20));
		danhsachCenterPanel.setPreferredSize(new Dimension(getWidth(), 30*50));
		danhsachCenterPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));

		scroll=new JScrollPane(danhsachCenterPanel);

		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(scroll, BorderLayout.CENTER);

		centerPanelJob.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelJob.add(danhsachPanel, BorderLayout.CENTER);

		jobPanel.add(centerPanelJob, BorderLayout.CENTER);
	}

	//	Listener
	public void addActionListener() {
		btnSearch.addActionListener(this);
		btnReset.addActionListener(this);
	}

	public void addFocusListener() {
		searchNameText.addFocusListener(this);
		searchSalaryText.addFocusListener(this);

		addPlaceHolder(searchNameText);
		addPlaceHolder(searchSalaryText);
	}

	public RoundPanel panelJob(Job job) {
		DecimalFormat df = new DecimalFormat("#,###");
		RoundPanel panel=new RoundPanel();
		panel.setPreferredSize(new Dimension(400, 100));
		panel.setBackground(new Color(89, 145, 144));
		panel.setLayout(new BorderLayout(10,0));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 10));

		logoPanel=new JPanel();
		logoPanel.setOpaque(false);
		logoPanel.setPreferredSize(new Dimension(100,100));
		if(getClass().getResource("/image/recruiterLogo/"+job.getRecruiter().getLogo())!=null) {
			if(logoPanel.getComponents()!=null) {
				logoPanel.removeAll();
				logoPanel.revalidate();
				logoPanel.repaint();
			}

			ImageIcon imageIcon=new ImageIcon(getClass().getResource("/image/recruiterLogo/"+
					job.getRecruiter().getLogo()));
			Image image=imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
			JLabel logoLabel=new JLabel(); logoLabel.setIcon(new ImageIcon(image));
			logoPanel.add(logoLabel);
			logoPanel.revalidate();
			logoPanel.repaint();
		}
		else {
			logoPanel.removeAll();
			logoPanel.revalidate();
			logoPanel.repaint();
		}

		JPanel centerPanel=new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new GridLayout(3,1));
		titleLabel=new JLabel(job.getTitle());
		titleLabel.setFont(new Font("Segoe UI",0,16));
		titleLabel.setForeground(Color.WHITE);
		recruiterLabel=new JLabel(job.getRecruiter().getName());
		recruiterLabel.setFont(new Font("Segoe UI",0,16));
		recruiterLabel.setForeground(Color.WHITE);
		salaryLabel=new JLabel(df.format(job.getSalary())+" VNĐ");
		salaryLabel.setFont(new Font("Segoe UI",0,16));
		salaryLabel.setForeground(Color.WHITE);
		centerPanel.add(titleLabel);
		centerPanel.add(recruiterLabel);
		centerPanel.add(salaryLabel);

		JPanel eastPanel=new JPanel();
		eastPanel.setBackground(new Color(89, 145, 144));
		eastPanel.setLayout(new BorderLayout());
		JPanel res=new JPanel();
		res.setOpaque(false);
		ButtonAction update=new ButtonAction();
		update.setIcon(new ImageIcon(getClass().getResource("/image/icon/update.png")));
		update.setName(String.valueOf(job.getId()));
		ButtonAction delete=new ButtonAction();
		delete.setIcon(new ImageIcon(getClass().getResource("/image/icon/delete.png")));
		delete.setName(String.valueOf(job.getId()));
		res.add(update); res.add(delete);
		Button btnCreateInvoice = new Button("Tạo hóa đơn");
		if(job.getPostedInvoice()!=null){
			btnCreateInvoice.setText("Đã tạo");
			btnCreateInvoice.setEnabled(false);
		}
		btnCreateInvoice.setFont(new Font("Segoe UI",0,16));
		btnCreateInvoice.setPreferredSize(new Dimension(120,25));
		btnCreateInvoice.setBackground(new Color(0,102,102));
		btnCreateInvoice.setForeground(Color.WHITE);
		btnCreateInvoice.setName(String.valueOf(job.getId()));

		if(job.getEndDate().compareTo(LocalDate.now())<=0
			|| job.getNumberOfPositions() == job.getJobResumes().size()) {
			eastPanel.add(btnCreateInvoice, BorderLayout.SOUTH);
		} else {
			eastPanel.add(res, BorderLayout.SOUTH);
		}

		panel.add(logoPanel, BorderLayout.WEST);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(eastPanel, BorderLayout.EAST);

		update.addMouseListener(this);
		delete.addMouseListener(this);
		btnCreateInvoice.addActionListener(this);

		updates.add(update);
		deletes.add(delete);
		btnCreateInvoices.add(btnCreateInvoice);

		return panel;
	}

	//	Trạng thái text chuột không nằm trong ô
	public void addPlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.ITALIC);
		text.setFont(font);
		text.setForeground(new Color(0, 102, 102));
		text.setText("Nhập dữ liệu");
	}

	//	Xóa trạng thái text chuột không nằm trong ô
	public void removePlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.PLAIN);
		text.setFont(font);
		text.setForeground(new Color(0, 102, 102));
	}

	@Override
	public void visible() {
		setVisible(true);
	}

	@Override
	public void close() {
		this.dispose();
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(rootPane, message);
	}

	@Override
	public void setFrameListener(IFrameListener<Job> listener) {
		this.frameListener = listener;
	}

	public JPanel getPanel() {
		return this.jobPanel;
	}

	@Override
	public void showData(List<Job> data, Object... objects) {
		updates.clear();
		deletes.clear();
		panelJobs.clear();

		danhsachCenterPanel.setPreferredSize(
				new Dimension(danhsachCenterPanel.getWidth(),data.size() * 50));
		danhsachCenterPanel.removeAll();
		danhsachCenterPanel.revalidate();
		danhsachCenterPanel.repaint();

		for(Job i: data) {
			RoundPanel panel=panelJob(i);
			panelJobs.add(panel);
			danhsachCenterPanel.add(panel);
		}
	}

	@Override
	public JTable getTable() {
		return null;
	}

	@Override
	public Object getSearchCriteria() {
		return new Object[]{
				searchNameText.getText(), searchSalaryText.getText(),
				searchRecruiterBox.getSelectedItem().toString(),
				searchLevelBox.getSelectedItem().toString(),
		};
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
	public void showAllRecruiter(List<Recruiter> data) {
		searchRecruiterBox.removeAllItems();
		searchRecruiterBox.addItem("Chọn nhà tuyển dụng");
		data.stream().forEach(j->{searchRecruiterBox.addItem(j.getName());});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnSearch)) {
			frameListener.onSearch(getSearchCriteria());
		}
		else if(obj.equals(btnReset)) {
			frameListener.onReset();
		} else {
			for(int i=0; i<btnCreateInvoices.size(); i++) {
				if(obj.equals(btnCreateInvoices.get(i))) {
					frameListener.onExport(
						new Object[]{
								Integer.parseInt(btnCreateInvoices.get(i).getName()),
								employee
						}
					);
					break;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		for(int i=0; i<updates.size();i++) {
			if(obj.equals(updates.get(i))) {
				frameListener.onUpdate(Integer.parseInt(updates.get(i).getName()));
				break;
			}
		}
		for(int i=0; i<deletes.size();i++) {
			if(obj.equals(deletes.get(i))) {
				frameListener.onDelete(Integer.parseInt(deletes.get(i).getName()));
				break;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(searchNameText)) {
			if(searchNameText.getText().equals("Nhập dữ liệu")) {
				searchNameText.setText(null);
				searchNameText.requestFocus();

				removePlaceHolder(searchNameText);
			}
		}
		else if(obj.equals(searchSalaryText)) {
			if(searchSalaryText.getText().equals("Nhập dữ liệu")) {
				searchSalaryText.setText(null);
				searchSalaryText.requestFocus();

				removePlaceHolder(searchSalaryText);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(searchNameText)) {
			if(searchNameText.getText().length()==0) {
				addPlaceHolder(searchNameText);
				searchNameText.setText("Nhập dữ liệu");
			}
		}
		else if(obj.equals(searchSalaryText)) {
			if(searchSalaryText.getText().length()==0) {
				addPlaceHolder(searchSalaryText);
				searchSalaryText.setText("Nhập dữ liệu");
			}
		}
	}
}
