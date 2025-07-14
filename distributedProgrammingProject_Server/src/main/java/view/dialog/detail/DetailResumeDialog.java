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
import java.util.Date;
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
import entity.Resume;
import entity.Applicant;
import entity.constant.Gender;
import entity.constant.Status;
import view.button.Button;
import view.panel.GradientPanel;

public class DetailResumeDialog extends JDialog implements ActionListener{

	private Resume resumeDialog;
	private Applicant applicantDialog;
	private Resume resume;

	GradientPanel inforApplicantPanel, btnPanel;
	JLabel idLabel, nameLabel, phoneLabel, dateLabel, genderLabel, addressLabel,emailLabel,statusLabel, descriptionLabel,
			recruiterLabel, jobLabel;
	JTextField idText, nameText, phoneText, addressText, emailText,
			recruiterText, jobText;
	JComboBox genderText, statusText;
	UtilDateModel modelDate;
	JDatePickerImpl dateText;
	JTextArea descriptionText;
	JScrollPane scrollDescription;
	Button btnAdd, btnReset;
	GridBagConstraints gbc;


	public DetailResumeDialog(Dialog parent, boolean modal, Resume resume, Applicant applicant) {
		super(parent, modal);
		setTitle("Xem chi tiết hồ sơ");
		setResizable(false);
		setSize(800,680);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.resumeDialog=resume;
		this.applicantDialog=applicant;

		initComponent();
		addActionListener();

//		loadDataResumeApplicant();
	}

	public DetailResumeDialog(Frame parent, boolean modal, Resume resume) {
		super(parent, modal);
		setTitle("Xem chi tiết hồ sơ");
		setResizable(false);
		setSize(800,680);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.resume=resume;

//		tintuyendungDAO=new TinTuyenDungDAO(em);
//		nhatuyendungDAO=new NhaTuyenDungDAO(em);
//		ungvienDAO=new UngVienDAO(em);

		initComponent();
		addActionListener();

//		loadData();
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
		inforApplicantPanel.add(statusText, gbc);

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
		inforApplicantPanel.add(genderText, gbc);

		gbc.gridx=2; gbc.gridy=4;
		addressLabel=new JLabel("Địa chỉ"); addressLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(addressLabel, gbc);
		gbc.gridx=2; gbc.gridy=5; gbc.gridwidth=2;
		addressText=new JTextField(23); addressText.setFont(new Font("Segoe UI",0,16));
		addressText.setEditable(false);
		inforApplicantPanel.add(addressText, gbc);

		gbc.gridx=0; gbc.gridy=8;
		descriptionLabel=new JLabel("Mô tả hồ sơ"); descriptionLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(descriptionLabel, gbc);
		gbc.gridx=0; gbc.gridy=9; gbc.gridwidth=4;
		descriptionText=new JTextArea(10, 48); descriptionText.setFont(new Font("Segoe UI",0,16));
		descriptionText.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		descriptionText.setLineWrap(true);
		descriptionText.setWrapStyleWord(true);
		descriptionText.setEditable(false);
		scrollDescription=new JScrollPane(descriptionText);
		inforApplicantPanel.add(scrollDescription, gbc);

		gbc.gridx=0; gbc.gridy=6; gbc.gridwidth=1;
		recruiterLabel=new JLabel("Nhà tuyển dụng"); recruiterLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(recruiterLabel,gbc);
		gbc.gridx=0; gbc.gridy=7; gbc.gridwidth=2;
		recruiterText=new JTextField(23); recruiterText.setFont(new Font("Segoe UI",0,16));
		recruiterText.setEditable(false);
		inforApplicantPanel.add(recruiterText,gbc);

		gbc.gridx=2; gbc.gridy=6; gbc.gridwidth=1;
		jobLabel=new JLabel("Tin tuyển dụng"); jobLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(jobLabel,gbc);
		gbc.gridx=2; gbc.gridy=7; gbc.gridwidth=2;
		jobText=new JTextField(23); jobText.setFont(new Font("Segoe UI",0,16));
		jobText.setEditable(false);
		inforApplicantPanel.add(jobText,gbc);

		add(inforApplicantPanel, BorderLayout.CENTER);

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

	public void addActionListener() {
		btnReset.addActionListener(this);
	}


//	public void loadDataHoSoUngVien() {
//		idText.setText(hsDialog.getMaHS());
//		for(int i=0;i< statusText.getItemCount();i++) {
//			if(statusText.getItemAt(i).toString().equalsIgnoreCase(hsDialog.getTrangThai().getValue())) {
//				statusText.setSelectedIndex(i);
//				break;
//			}
//		}
//		nameText.setText(uvDialog.getTenUV());
//		emailText.setText(uvDialog.getEmail());
//		phoneText.setText(uvDialog.getSoDienThoai());
//		addressText.setText(uvDialog.getDiaChi());
//		modelDate.setValue(Date.from(uvDialog.getNgaySinh().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//		for(int i=0;i<genderText.getItemCount();i++) {
//			if(genderText.getItemAt(i).toString().equalsIgnoreCase(uvDialog.getGioiTinh().getValue())) {
//				genderText.setSelectedIndex(i);
//				break;
//			}
//		}
//		descriptionText.setText(hsDialog.getMoTa());
//
//		TinTuyenDung ttd=null;
//		NhaTuyenDung ntd=null;
//		if(hsDialog.getTinTuyenDung()!=null) {
//			ttd=tintuyendungDAO.getTinTuyenDung(hsDialog.getTinTuyenDung().getMaTTD());
//			ntd=nhatuyendungDAO.getNhaTuyenDung(ttd.getNhaTuyenDung().getMaNTD());
//		}
//		jobText.setText(ttd!=null?ttd.getTieuDe():"");
//		recruiterText.setText(ntd!=null?ntd.getTenNTD():"");
//
//	}

//	public void loadData() {
//		idText.setText(hoso.getMaHS());
//		for(int i=0;i< statusText.getItemCount();i++) {
//			if(statusText.getItemAt(i).toString().equalsIgnoreCase(hoso.getTrangThai().getValue())) {
//				statusText.setSelectedIndex(i);
//				break;
//			}
//		}
//
//		UngVien uv=ungvienDAO.getUngVien(hoso.getUngVien().getMaUV());
//
//		nameText.setText(uv.getTenUV());
//		emailText.setText(uv.getEmail());
//		phoneText.setText(uv.getSoDienThoai());
//		addressText.setText(uv.getDiaChi());
//		modelDate.setValue(Date.from(uv.getNgaySinh().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//		for(int i=0;i<genderText.getItemCount();i++) {
//			if(genderText.getItemAt(i).toString().equalsIgnoreCase(uv.getGioiTinh().getValue())) {
//				genderText.setSelectedIndex(i);
//				break;
//			}
//		}
//		descriptionText.setText(hoso.getMoTa());
//
//		TinTuyenDung ttd=null;
//		NhaTuyenDung ntd=null;
//		if(hoso.getTinTuyenDung()!=null) {
//			ttd=tintuyendungDAO.getTinTuyenDung(hoso.getTinTuyenDung().getMaTTD());
//			ntd=nhatuyendungDAO.getNhaTuyenDung(ttd.getNhaTuyenDung().getMaNTD());
//		}
//		jobText.setText(ttd!=null?ttd.getTieuDe():"");
//		recruiterText.setText(ntd!=null?ntd.getTenNTD():"");
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(btnReset)) {
			this.dispose();
		}
	}
}
