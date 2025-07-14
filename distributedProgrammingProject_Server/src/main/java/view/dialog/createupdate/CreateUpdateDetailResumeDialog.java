package view.dialog.createupdate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


import dao.JobDAO;
import dao.ProfessionDAO;
import dao.RecruiterDAO;
import entity.*;
import entity.constant.Level;
import lombok.Getter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import util.DateFormatter;
import entity.constant.Gender;
import entity.constant.Status;
import view.button.Button;
import view.combobox.ComboBoxMultiSelection;
import view.panel.GradientPanel;

public class CreateUpdateDetailResumeDialog extends JDialog implements ActionListener{

	private Frame parent;
	private Employee employee;
	private Applicant applicant;
	private Job job;
	@Getter
	private JobResume jobResume;
	@Getter
	private Resume resume;
	@Getter
	private boolean confirmed = false;
	private JobDAO jobDAO;
	private RecruiterDAO recruiterDAO;
	private List<Profession> professions;
	private File selectedFile;
	private final String OUTPUT_DIR_PATH = System.getProperty("user.dir") + File.separator + "resumes";
	private int check = 0;

	GradientPanel inforApplicantPanel, btnPanel;
	JLabel idLabel, nameLabel, phoneLabel, dateLabel, genderLabel, addressLabel,emailLabel,statusLabel, descriptionLabel,
			recruiterLabel, jobLabel, levelLabel, professionLabel;
	JTextField idText, nameText, phoneText, addressText, emailText,
			recruiterText, jobText, descriptionText;
	JComboBox genderText, statusText, levelText;
	UtilDateModel modelDate;
	JDatePickerImpl dateText;
	Button btnAdd, btnReset, btnFile;
	GridBagConstraints gbc;
	ComboBoxMultiSelection comboBoxMultiSelection;

//	Add resume
	public CreateUpdateDetailResumeDialog(Frame parent, boolean modal, Applicant applicant, Employee employee) {
		super(parent, modal);
		check = 0;
		setTitle("Tạo hồ sơ");
		setResizable(false);
		setSize(800,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.parent=parent;
		this.employee=employee;
		this.applicant=applicant;
		this.professions = new ProfessionDAO(Profession.class).getAll();
		this.resume = new Resume();
		this.selectedFile = null;

		initComponent();
		addActionListener();

		loadDataApplicant();
	}

	//	Detail resume when apply job
	public CreateUpdateDetailResumeDialog(Dialog parent, boolean modal,
										  Resume resume, Applicant applicant, JobResume jobResume){
		super(parent, modal);
		check = 1;
		setTitle("Chi tiết hồ sơ");
		setResizable(false);
		setSize(800,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.resume = resume;
		this.applicant = applicant;
		this.jobResume=jobResume;
		this.professions = new ProfessionDAO(Profession.class).getAll();

		initComponent();
		addActionListener();
		btnPanel.remove(btnAdd);

		if(jobResume != null){
			addRecruiterJob();
		}

		loadDataApplicant();
		loadDataResume();
	}

//	Update resume
	public CreateUpdateDetailResumeDialog(Frame parent, boolean modal,
				  Applicant applicant, Employee employee, Resume resume) {
		this(parent, modal, applicant, employee);
		check = 2;
		setTitle("Cập nhật hồ sơ");

		this.resume=resume;

		btnAdd.setText("Cập nhật");

		loadDataApplicant();
		loadDataResume();
	}

//	Detail resume
	public CreateUpdateDetailResumeDialog(Frame parent, boolean modal, Resume resume){
		super(parent, modal);
		setTitle("Chi tiết hồ sơ");
		setResizable(false);
		setSize(800,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		check = 4;
		this.resume = resume;
		this.applicant = resume.getApplicant();
		this.professions = new ProfessionDAO(Profession.class).getAll();

		initComponent();
		addActionListener();
		btnPanel.remove(btnAdd);
		unable();

		loadDataApplicant();
		loadDataResume();
	}

	//	Update status resume when apply job
	public CreateUpdateDetailResumeDialog(Frame parent, boolean modal,
				   Employee employee, JobResume jobResume){
		super(parent, modal);
		check = 3;
		setTitle("Cập nhật hồ sơ");
		setResizable(false);
		setSize(800,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.parent=parent;
		this.employee=employee;
		this.jobResume = jobResume;
		this.applicant = jobResume.getResume().getApplicant();
		this.professions = new ProfessionDAO(Profession.class).getAll();
		this.selectedFile = null;

		initComponent();
		addRecruiterJob();
		unable();
		btnAdd.setText("Cập nhật");

		addActionListener();
		loadDataApplicant();
		loadDataResume();
	}

	public void initComponent() {
		inforApplicantPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		inforApplicantPanel.setBackground(Color.WHITE);
		inforApplicantPanel.setLayout(new GridBagLayout());
		inforApplicantPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		gbc= new GridBagConstraints();

//		Thông tin hồ sơ
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.WEST;
		idLabel=new JLabel("Mã hồ sơ"); idLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(idLabel, gbc);
		gbc.gridx=0; gbc.gridy=1;
		idText=new JTextField(10); idText.setFont(new Font("Segoe UI",0,16));
		idText.setEditable(false);
		inforApplicantPanel.add(idText, gbc);

		if(check == 1 || check == 3){
			gbc.gridx=1; gbc.gridy=0;
			statusLabel=new JLabel("Trạng thái"); statusLabel.setFont(new Font("Segoe UI",0,16));
			inforApplicantPanel.add(statusLabel, gbc);
			gbc.gridx=1; gbc.gridy=1;
			statusText=new JComboBox(); statusText.setFont(new Font("Segoe UI",0,16));
			Status[] trangthais= Status.class.getEnumConstants();
			for(Status t: trangthais) {
				statusText.addItem(t.getValue());
			}
			statusText.setPreferredSize(new Dimension(156,26));
			if(check == 1 || check == 0) statusText.setEnabled(false);
			inforApplicantPanel.add(statusText, gbc);
		}

		gbc.gridx=2; gbc.gridy=0;
		nameLabel=new JLabel("Họ tên ứng viên"); nameLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(nameLabel, gbc);
		gbc.gridx=2; gbc.gridy=1; gbc.gridwidth=2;
		nameText=new JTextField(23); nameText.setFont(new Font("Segoe UI",0,16));
		nameText.setEditable(false);
		inforApplicantPanel.add(nameText, gbc);

		gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=1;
		emailLabel=new JLabel("Email"); emailLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(emailLabel,gbc);
		gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2;
		emailText=new JTextField(23); emailText.setFont(new Font("Segoe UI",0,16));
		emailText.setEditable(false);
		inforApplicantPanel.add(emailText,gbc);

		gbc.gridx=2; gbc.gridy=2; gbc.gridwidth=1;
		phoneLabel=new JLabel("Số điện thoại"); phoneLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(phoneLabel, gbc);
		gbc.gridx=2; gbc.gridy=3; gbc.gridwidth=2;
		phoneText=new JTextField(23); phoneText.setFont(new Font("Segoe UI",0,16));
		phoneText.setEditable(false);
		inforApplicantPanel.add(phoneText, gbc);

		gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=1;
		dateLabel=new JLabel("Ngày sinh"); dateLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(dateLabel, gbc);
		gbc.gridx=0; gbc.gridy=5;
		modelDate=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelDate, p);
		dateText=new JDatePickerImpl(panelDate, new DateFormatter());
		dateText.setPreferredSize(new Dimension(150,25));
		dateText.getComponent(1).setEnabled(false);
		modelDate.setValue(new Date());
		inforApplicantPanel.add(dateText, gbc);

		gbc.gridx=1; gbc.gridy=4;
		genderLabel=new JLabel("Giới tính"); genderLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(genderLabel, gbc);
		gbc.gridx=1; gbc.gridy=5;
		genderText=new JComboBox(); genderText.setFont(new Font("Segoe UI",0,16));
		Gender[] gioitinhs= Gender.class.getEnumConstants();
		for(Gender g: gioitinhs) {
			genderText.addItem(g.getValue());
		}
		genderText.setPreferredSize(new Dimension(156,25));
		genderText.setEnabled(false);
		inforApplicantPanel.add(genderText, gbc);

		gbc.gridx=2; gbc.gridy=4;
		addressLabel=new JLabel("Địa chỉ"); addressLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(addressLabel, gbc);
		gbc.gridx=2; gbc.gridy=5; gbc.gridwidth=2;
		addressText=new JTextField(23); addressText.setFont(new Font("Segoe UI",0,16));
		addressText.setEditable(false);
		inforApplicantPanel.add(addressText, gbc);

		gbc.gridx=0; gbc.gridy=6;
		professionLabel=new JLabel("Ngành nghề"); professionLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(professionLabel, gbc);
		gbc.gridx=0; gbc.gridy=7; gbc.gridwidth=2;
		comboBoxMultiSelection = new ComboBoxMultiSelection();
		comboBoxMultiSelection.setModel(new DefaultComboBoxModel<>(
				professions.stream()
				.map(Profession::getName)
				.toArray(String[]::new))
		);
		comboBoxMultiSelection.setPreferredSize(new Dimension(330,25));
		if (check == 1) comboBoxMultiSelection.setEnabled(false);
		inforApplicantPanel.add(comboBoxMultiSelection, gbc);

		gbc.gridx=2; gbc.gridy=6; gbc.gridwidth=1;
		levelLabel=new JLabel("Trình độ"); levelLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(levelLabel, gbc);
		gbc.gridx=2; gbc.gridy=7;
		levelText=new JComboBox(); levelText.setFont(new Font("Segoe UI",0,16));
		Level[] levels= Level.class.getEnumConstants();
		for(Level l: levels) {
			levelText.addItem(l.getValue());
		}
		levelText.setPreferredSize(new Dimension(156,25));
		if (check == 1) levelText.setEnabled(false);
		inforApplicantPanel.add(levelText, gbc);

		gbc.gridx=0; gbc.gridy=10;
		descriptionLabel=new JLabel("Mô tả hồ sơ"); descriptionLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(descriptionLabel, gbc);
		gbc.gridx=0; gbc.gridy=11; gbc.gridwidth=4;
		JPanel panelDescription = new JPanel();
		panelDescription.setOpaque(false);
		btnFile=new Button("Chọn file"); btnFile.setFont(new Font("Segoe UI",0,16));
		btnFile.setPreferredSize(new Dimension(125,25));
		btnFile.setBackground(new Color(0,102,102));
		btnFile.setForeground(Color.WHITE);
		if(check == 1){
			btnFile.setText("Không chọn");
			btnFile.setEnabled(false);
		}
		descriptionText=new JTextField(39); descriptionText.setFont(new Font("Segoe UI",0,16));
		descriptionText.setEditable(false);
		panelDescription.add(btnFile);
		panelDescription.add(descriptionText);
		inforApplicantPanel.add(panelDescription, gbc);

		add(inforApplicantPanel, BorderLayout.CENTER);

//		Button
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 50));

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

	public void addRecruiterJob(){
		gbc.gridx=0; gbc.gridy=8; gbc.gridwidth=1;
		recruiterLabel=new JLabel("Nhà tuyển dụng"); recruiterLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(recruiterLabel,gbc);
		gbc.gridx=0; gbc.gridy=9; gbc.gridwidth=2;
		recruiterText=new JTextField(23); recruiterText.setFont(new Font("Segoe UI",0,16));
		recruiterText.setEditable(false);
		inforApplicantPanel.add(recruiterText,gbc);

		gbc.gridx=2; gbc.gridy=8; gbc.gridwidth=1;
		jobLabel=new JLabel("Tin tuyển dụng"); jobLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(jobLabel,gbc);
		gbc.gridx=2; gbc.gridy=9; gbc.gridwidth=2;
		jobText=new JTextField(23); jobText.setFont(new Font("Segoe UI",0,16));
		jobText.setEditable(false);
		inforApplicantPanel.add(jobText,gbc);
	}

	public void addActionListener() {
		btnAdd.addActionListener(this);
		btnReset.addActionListener(this);
		btnFile.addActionListener(this);
	}

	public void loadDataApplicant() {
		idText.setText(String.valueOf(applicant.getId()));
		nameText.setText(applicant.getName());
		emailText.setText(applicant.getContact().getEmail());
		phoneText.setText(applicant.getContact().getPhone());
		addressText.setText(applicant.getAddress().getCity());
		modelDate.setValue(Date.from(applicant.getDateOfBirth().atStartOfDay(ZoneId.systemDefault()).toInstant()));

		for(int i=0;i< genderText.getItemCount();i++) {
			if(genderText.getItemAt(i).toString().equals(applicant.getGender().getValue())) {
				genderText.setSelectedIndex(i);
				break;
			}
		}
	}

	public void loadDataResume() {
		idText.setText(String.valueOf(
				jobResume == null ? resume.getId() : jobResume.getResume().getId()
		));
		if(check == 1 || check == 3){
			for(int i=0;i< statusText.getItemCount();i++) {
				if(statusText.getItemAt(i).toString().equalsIgnoreCase(jobResume.getStatus().getValue())) {
					statusText.setSelectedIndex(i);
					break;
				}
			}

			if(check == 1){
				btnFile.setText("Không chọn");
				btnFile.setEnabled(false);
				levelText.setEditable(false);
				comboBoxMultiSelection.setEnabled(false);
			}

			jobText.setText(jobResume.getJob().getTitle());
			recruiterText.setText(jobResume.getJob().getRecruiter().getName());
		}
		descriptionText.setText(
				jobResume == null ? resume.getDescription() : jobResume.getResume().getDescription()
		);

		for(int i=0;i< levelText.getItemCount();i++) {
			if(levelText.getItemAt(i).toString().equals(
					jobResume == null ? resume.getLevel().getValue() : jobResume.getResume().getLevel().getValue()
			)) {
				levelText.setSelectedIndex(i);
				break;
			}
		}

		List<Profession> list = jobResume == null ? resume.getProfessions() : jobResume.getResume().getProfessions();
		List<String> professionString = list.stream()
				.map(Profession::getName).collect(Collectors.toList());
		comboBoxMultiSelection.setSelectedItems(professionString);
	}

    public void addUpdate(){
		if(check == 3){
			Status status=null;
			for(Status i: Status.class.getEnumConstants()) {
				if(i.getValue().equalsIgnoreCase(statusText.getSelectedItem().toString())) {
					status=i;
				}
			}

			if(status.getValue().equalsIgnoreCase(Status.NONE.getValue())){
				JOptionPane.showMessageDialog(rootPane, "Trạng thái hồ sơ không hợp lệ");
				return;
			}

			if(status.getValue().equalsIgnoreCase(Status.ACCEPTED.getValue())){
				int confirm = JOptionPane.showConfirmDialog(null,
						"Trạng thái hồ sơ có thể tạo hóa đơn cho ứng viên. Tạo hóa đơn?");
				if(confirm == JOptionPane.YES_OPTION){
					jobResume.setStatus(status);
					confirmed = true;
					dispose();
				}
			} else {
				jobResume.setStatus(status);
				confirmed = true;
				dispose();
			}
		} else {
			String description = descriptionText.getText();
			if(check != 0) selectedFile = new File(OUTPUT_DIR_PATH, description);

			Level level=null;
			for(Level i: Level.class.getEnumConstants()) {
				if(i.getValue().equalsIgnoreCase(levelText.getSelectedItem().toString())) {
					level=i;
				}
			}
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

			if(professionList.size() <= 0){
				JOptionPane.showMessageDialog(rootPane, "Chọn ngành nghề");
			}
			else if(selectedFile == null || !selectedFile.exists()) {
				JOptionPane.showMessageDialog(rootPane, "Chọn file hoặc file không tồn tại");
			}
			else {
				resume.setApplicant(applicant);
				applicant.setResume(resume);
				resume.setEmployee(employee);
				resume.setDescription(description);
				resume.setLevel(level);
				resume.setProfessions(professionList);

				File destinationDir = new File(OUTPUT_DIR_PATH);
				if (!destinationDir.exists()) {
					destinationDir.mkdirs();
				}
				File destinationFile = new File(destinationDir, selectedFile.getName());
				try {
					Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Lỗi khi lưu file!");
				}

				confirmed=true;
				dispose();
			}
		}
    }

    public void reset(){
		if(check == 1){
			statusText.setSelectedIndex(0);
		}
		levelText.setSelectedIndex(0);
		comboBoxMultiSelection.clearSelectedItems();
		selectedFile = null;
		descriptionText.setText("");

		confirmed = false;
    }

	public void loadFile(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn file ứng viên");
		fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			descriptionText.setText(selectedFile.getName());
		}
	}

	public void unable(){
		comboBoxMultiSelection.setEnabled(false);
		levelText.setEnabled(false);
		descriptionText.setEnabled(false);
		btnFile.setText("Không chọn");
		btnFile.setEnabled(false);

		if(check == 3 && jobResume.getStatus() == Status.ACCEPTED){
			statusText.setEnabled(false);
			btnPanel.remove(btnAdd);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnAdd)) {
            addUpdate();
		}
		else if(obj.equals(btnReset)) {
            if (check == 1 || check == 3 || check == 4) {
                this.dispose();
            } else {
                reset();
            }
        } else if(obj.equals(btnFile)) {
			check = 0;
			loadFile();
		}
	}
}
