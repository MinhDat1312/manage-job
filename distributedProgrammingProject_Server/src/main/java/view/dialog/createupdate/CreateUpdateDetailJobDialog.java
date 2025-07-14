package view.dialog.createupdate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.*;

import dao.ProfessionDAO;
import entity.*;
import lombok.Getter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import util.DateFormatter;
import entity.constant.WorkingType;
import entity.constant.Level;
import view.button.Button;
import view.combobox.ComboBoxMultiSelection;
import view.dialog.idialog.IDialogListener;
import view.dialog.list.ListResumesDialog;
import view.panel.GradientPanel;

public class CreateUpdateDetailJobDialog extends JDialog implements ActionListener{


	private Frame parent;
	private Recruiter recruiter;
	private Employee employee;
	@Getter
	private JobResume jobResume;
	@Getter
	private Job job;
	@Getter
	private boolean confirmed;
	private boolean checked;
	private List<Profession> professions;
	private List<Resume> resumes;

	GradientPanel inforJobPanel, btnPanel;
	JLabel idLabel, nameLabel, typeLabel, startLabel, endLabel, levelLabel, addressLabel,
			titleLabel,statusLabel, descriptionLabel,
			numberLabel, salaryLabel, professionLabel;
	JTextField idText, nameText, addressText, titleText,
			numberText, salaryText;
	JComboBox levelBox, statusBox, typeBox;
	UtilDateModel modelDateStart, modelDateEnd;
	JDatePickerImpl startText, endText;
	JTextArea descriptionText;
	JScrollPane scrollDescription;
	Button btnAdd, btnReset;
	GridBagConstraints gbc;
	ComboBoxMultiSelection comboBoxMultiSelection;

//	Add job
	public CreateUpdateDetailJobDialog(Frame parent, boolean modal, Recruiter recruiter) {
		super(parent, modal);
		setTitle("Tạo tin tuyển dụng");
		setResizable(false);
		setSize(800,680);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.parent=parent;
		this.recruiter=recruiter;
		this.job=new Job();
		this.confirmed=false;
		this.professions = new ProfessionDAO(Profession.class).getAll();
		this.checked=true;

		initComponent();
		addActionListener();

		loadDataRecruiter();
	}

//	Update and Detail job
	public CreateUpdateDetailJobDialog(Frame parent, boolean modal, Recruiter recruiter, Job job, Object... isDetail) {
		this(parent, modal, recruiter);
		this.job=job;
		this.checked = false;

		if(isDetail.length > 0 && (boolean)isDetail[0]) {
			setTitle("Chi tiết tin tuyển dụng");
			this.checked = (boolean)isDetail[0];
			btnPanel.remove(btnAdd);
			onUnable();
		} else {
			setTitle("Cập nhật tin tuyển dụng");
			btnAdd.setText("Cập nhật");
			statusBox.setEnabled(true);
		}

		loadDataJob();
	}

//	Detail job search
	public CreateUpdateDetailJobDialog(Frame parent, boolean modal, Job job, Employee employee, List<Resume> resumes){
		super(parent, modal);
		setTitle("Xem chi tiết việc làm");
		setResizable(false);
		setSize(800,680);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.parent=parent;
		this.job=job;
		this.employee=employee;
		this.professions = new ProfessionDAO(Profession.class).getAll();
		this.resumes=resumes;
		this.jobResume = new JobResume();
		this.confirmed=false;

		initComponent();
		onUnable();
		statusBox.setEnabled(false);
		btnAdd.setText("Hồ sơ");

		addActionListener();
		loadDataJob();
		nameText.setText(job.getRecruiter().getName());
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
		if(checked) statusBox.setEnabled(false);
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
		levelBox.setPreferredSize(new Dimension(155,25));
		inforJobPanel.add(levelBox, gbc);

		gbc.gridx=0; gbc.gridy=4;
		numberLabel=new JLabel("Số lượng"); numberLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(numberLabel, gbc);
		gbc.gridx=0; gbc.gridy=5;
		numberText=new JTextField(10); numberText.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(numberText, gbc);

		gbc.gridx=1; gbc.gridy=4;
		salaryLabel=new JLabel("Lương"); salaryLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(salaryLabel, gbc);
		gbc.gridx=1; gbc.gridy=5;
		salaryText=new JTextField(11); salaryText.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(salaryText, gbc);

		gbc.gridx=2; gbc.gridy=4;
		professionLabel=new JLabel("Ngành nghề"); professionLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(professionLabel, gbc);
		gbc.gridx=2; gbc.gridy=5; gbc.gridwidth=2;
		comboBoxMultiSelection = new ComboBoxMultiSelection();
		comboBoxMultiSelection.setModel(new DefaultComboBoxModel<>(
				professions.stream()
						.map(Profession::getName)
						.toArray(String[]::new))
		);
		comboBoxMultiSelection.setPreferredSize(new Dimension(330,25));
		inforJobPanel.add(comboBoxMultiSelection, gbc);

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
		inforJobPanel.add(endText, gbc);

		gbc.gridx=0; gbc.gridy=8;
		descriptionLabel=new JLabel("Mô tả công việc"); descriptionLabel.setFont(new Font("Segoe UI",0,16));
		inforJobPanel.add(descriptionLabel, gbc);
		gbc.gridx=0; gbc.gridy=9; gbc.gridwidth=4;
		descriptionText=new JTextArea(10, 50); descriptionText.setFont(new Font("Segoe UI",0,16));
		descriptionText.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		descriptionText.setLineWrap(true);
		descriptionText.setWrapStyleWord(true);
		scrollDescription=new JScrollPane(descriptionText);
		inforJobPanel.add(scrollDescription, gbc);

		add(inforJobPanel, BorderLayout.CENTER);

//		Button
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 40));

		btnAdd=new Button("Tạo mới"); btnAdd.setFont(new Font("Segoe UI",0,16));
		btnAdd.setPreferredSize(new Dimension(120,25));
		btnAdd.setBackground(new Color(0,102,102));
		btnAdd.setForeground(Color.WHITE);

		btnReset=new Button("Hủy"); btnReset.setFont(new Font("Segoe UI",0,16));
		btnReset.setPreferredSize(new Dimension(120,25));
		btnReset.setBackground(Color.WHITE);
		btnReset.setForeground(Color.BLACK);

		btnPanel.add(btnAdd); btnPanel.add(btnReset);

		add(btnPanel, BorderLayout.SOUTH);
	}

	public void addActionListener() {
		btnAdd.addActionListener(this);
		btnReset.addActionListener(this);
	}

	public void loadDataRecruiter() {
		nameText.setText(recruiter.getName());
	}

	public void loadDataJob() {
		idText.setText(String.valueOf(job.getId()));
		statusBox.setSelectedIndex(job.isVisible()?0:1);
		titleText.setText(job.getTitle());
		for(int i=0;i<typeBox.getItemCount();i++) {
			if(typeBox.getItemAt(i).toString().equalsIgnoreCase(job.getWorkingType().getValue())) {
				typeBox.setSelectedIndex(i);
				break;
			}
		}
		for(int i=0;i<levelBox.getItemCount();i++) {
			if(levelBox.getItemAt(i).toString().equalsIgnoreCase(job.getLevel().getValue())) {
				levelBox.setSelectedIndex(i);
				break;
			}
		}
		List<String> professionString = job.getProfessions().stream()
				.map(Profession::getName).collect(Collectors.toList());
		comboBoxMultiSelection.setSelectedItems(professionString);
		numberText.setText(String.valueOf(job.getNumberOfPositions()));
		salaryText.setText(String.valueOf((int)job.getSalary()));
		modelDateStart.setValue(Date.from(job.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		modelDateEnd.setValue(Date.from(job.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		descriptionText.setText(job.getDescription());
	}

	public void onUnable(){
		btnReset.setText("Thoát");
		btnReset.setBackground(Color.RED);
		btnReset.setForeground(Color.WHITE);
		titleText.setEditable(false);
		numberText.setEditable(false);
		salaryText.setEditable(false);
		comboBoxMultiSelection.setEnabled(false);
		levelBox.setEnabled(false);
		typeBox.setEnabled(false);
		startText.getComponent(1).setEnabled(false);
		endText.getComponent(1).setEnabled(false);
		descriptionText.setEditable(false);
	}

	public void addUpdate(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Pattern pattern = Pattern.compile("^[0-9]+$");

		String title=titleText.getText();
		WorkingType type=null;
		for(WorkingType h: WorkingType.class.getEnumConstants()) {
			if(h.getValue().equalsIgnoreCase(typeBox.getSelectedItem().toString())) {
				type=h;
				break;
			}
		}
		Level level=null;
		for(Level h: Level.class.getEnumConstants()) {
			if(h.getValue().equalsIgnoreCase(levelBox.getSelectedItem().toString())) {
				level=h;
				break;
			}
		}
		String numberOf=numberText.getText();
		String salary=salaryText.getText();
		List<Profession> professionList = new ArrayList<>();
		List<String> professionNameList = (List<String>) comboBoxMultiSelection.getSelectedItems()
				.stream()
				.map(Object::toString)
				.collect(Collectors.toList());
		for(Profession p : professions){
			if(professionNameList.contains(p.getName())) {
				professionList.add(p);
			}
		}
		String startDate=format.format(modelDateStart.getValue());
		String endDate=format.format(modelDateEnd.getValue());
		String description=descriptionText.getText();

		boolean status = false;
		if(checked){
			status = LocalDate.parse(startDate).compareTo(LocalDate.now()) >= 0
					&& LocalDate.parse(startDate).isBefore(LocalDate.parse(endDate))
					? true : false;
		} else {
			status = statusBox.getSelectedItem().toString().equalsIgnoreCase("Khả dụng")
				? true : false;
		}

		if(title.isEmpty() || numberOf.isEmpty() || salary.isEmpty() || description.isEmpty()){
			JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin tin tuyển dụng");
		} else if(!pattern.matcher(numberOf).matches() || !pattern.matcher(salary).matches()) {
			JOptionPane.showMessageDialog(rootPane, "Số lượng và lương phải là số lớn hơn 0");
		} else if(professionList.size() <= 0){
			JOptionPane.showMessageDialog(rootPane, "Chọn ngành nghề cho tin tuyển dụng");
		} else if(LocalDate.parse(startDate).compareTo(LocalDate.parse(endDate)) >= 0
			|| LocalDate.parse(startDate).isAfter(LocalDate.now())
			|| LocalDate.parse(endDate).compareTo(LocalDate.now()) <= 0) {
			JOptionPane.showMessageDialog(rootPane, "Khoảng thời gian không hợp lệ");
		} else {
			job.setTitle(title);
			job.setDescription(description);
			job.setStartDate(LocalDate.parse(startDate));
			job.setEndDate(LocalDate.parse(endDate));
			job.setLevel(level);
			job.setNumberOfPositions(Integer.parseInt(numberOf));
			job.setSalary(Double.parseDouble(salary));
			job.setWorkingType(type);
			job.setVisible(status);
			job.setRecruiter(recruiter);
			job.setProfessions(professionList);

			confirmed = true;
			this.dispose();
		}
	}

	public void reset(){
		statusBox.setSelectedIndex(0);
		titleText.setText("");
		typeBox.setSelectedIndex(0);
		levelBox.setSelectedIndex(0);
		comboBoxMultiSelection.clearSelectedItems();
		numberText.setText("");
		salaryText.setText("");
		modelDateStart.setValue(new Date());
		modelDateEnd.setValue(new Date());
		descriptionText.setText("");

		confirmed = false;
	}

	public void apply(){
		ListResumesDialog dialog = new ListResumesDialog(
				null, true, job, employee
		);
		dialog.showDataResume(resumes);
		dialog.setDialogListener(new IDialogListener<JobResume>() {
			@Override
			public void onSearch(Object criteria) {

			}

			@Override
			public void onView(int row) {
				if(row < 0){
					dialog.showMessage("Chọn hồ sơ để ứng tuyển");
					return;
				}
				int checkOption = JOptionPane.showConfirmDialog(rootPane, "Có chắc chắn ứng tuyển?");
				if(checkOption == JOptionPane.YES_OPTION){
					int idResume = Integer.parseInt(dialog.getTable().getValueAt(row, 0).toString());
					int idJob = job.getId();

					jobResume.setId(new JobResume.JobResumeId(idJob, idResume));
					confirmed = true;

					dialog.close();

					dispose();
				}
			}

			@Override
			public void onReset() {

			}
		});
		dialog.visible();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnAdd)) {
			if(btnAdd.getText().equalsIgnoreCase("Hồ sơ")) {
				apply();
			} else {
				addUpdate();
			}
		}
		else if(obj.equals(btnReset)) {
			if(btnReset.getText().equalsIgnoreCase("Thoát")){
				dispose();
			} else {
				reset();
			}
		}
	}
}
