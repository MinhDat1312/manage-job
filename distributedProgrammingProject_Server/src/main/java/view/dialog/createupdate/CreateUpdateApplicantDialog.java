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
import java.util.Date;
import java.util.Properties;

import javax.swing.*;


import entity.embeddable.Address;
import entity.embeddable.Contact;
import exception.*;
import lombok.Getter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import util.DateFormatter;
import dao.ApplicantDAO;
import entity.Applicant;
import entity.constant.Gender;
import util.FilterImp;
import view.button.Button;
import view.frame.imp.ApplicantFrameImp;
import view.panel.GradientPanel;

public class CreateUpdateApplicantDialog extends JDialog implements ActionListener{

	@Getter
	private Applicant applicant;
	@Getter
	private boolean confirmed;
	private ApplicantFrameImp parent;

	GradientPanel inforApplicantPanel, btnPanel;
	JLabel idLabel, nameLabel, phoneLabel, dateLabel, genderLabel, addressLabel,emailLabel;
	JTextField idText, nameText, phoneText, emailText;
	JTextField[] addressFields;
	JComboBox genderText;
	UtilDateModel modelDate;
	JDatePickerImpl dateText;
	Button btnAdd, btnReset;

	public CreateUpdateApplicantDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Thêm mới ứng viên");
		setResizable(false);
		setSize(700,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.applicant = new Applicant();
		this.parent=(ApplicantFrameImp) parent;
		this.confirmed = false;

		initComponent();
		addActionListener();
	}

	public CreateUpdateApplicantDialog(Frame parent, boolean modal, Applicant applicant) {
		this(parent, modal);
		this.applicant=applicant;
		setTitle("Cập nhật ứng viên");
		btnAdd.setText("Cập nhật");

		loadDataApplicant();
	}

	public void initComponent() {
		inforApplicantPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		inforApplicantPanel.setBackground(Color.WHITE);
		inforApplicantPanel.setLayout(new GridBagLayout());
		inforApplicantPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc= new GridBagConstraints();

//		Thông tin ứng viên
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.WEST;
		idLabel=new JLabel("Mã ứng viên"); idLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(idLabel, gbc);
		gbc.gridx=0; gbc.gridy=1;
		idText=new JTextField(10); idText.setFont(new Font("Segoe UI",0,16));
		idText.setEditable(false);
		inforApplicantPanel.add(idText, gbc);

		gbc.gridx=0; gbc.gridy=2; gbc.gridwidth = 2;
		nameLabel=new JLabel("Họ tên"); nameLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(nameLabel, gbc);
		gbc.gridx=0; gbc.gridy=3;
		nameText=new JTextField(20); nameText.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(nameText, gbc);

		gbc.gridx=2; gbc.gridy=0;
		gbc.gridwidth = 1;
		dateLabel=new JLabel("Ngày sinh"); dateLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(dateLabel, gbc);
		gbc.gridx=2; gbc.gridy=1;
		modelDate=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelDate, p);
		dateText=new JDatePickerImpl(panelDate, new DateFormatter());
		dateText.setPreferredSize(new Dimension(150,25));
		modelDate.setValue(new Date());
		inforApplicantPanel.add(dateText, gbc);

		gbc.gridx=0; gbc.gridy=4; gbc.gridwidth = 2;
		phoneLabel=new JLabel("Số điện thoại"); phoneLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(phoneLabel, gbc);
		gbc.gridx=0; gbc.gridy=5;
		phoneText=new JTextField(20); phoneText.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(phoneText, gbc);

		gbc.gridx=2; gbc.gridy=2; gbc.gridwidth = 1;
		genderLabel=new JLabel("Giới tính"); genderLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(genderLabel, gbc);
		gbc.gridx=2; gbc.gridy=3;
		genderText=new JComboBox(); genderText.setFont(new Font("Segoe UI",0,16));
		Gender[] gioitinhs= Gender.class.getEnumConstants();
		for(Gender g: gioitinhs) {
			genderText.addItem(g.getValue());
		}
		genderText.setPreferredSize(new Dimension(150,25));
		inforApplicantPanel.add(genderText, gbc);

		String[] addressLabels = {"Thành phố", "Quốc gia", "Quận", "Số nhà", "Đường", "Phường"};
		addressFields = new JTextField[addressLabels.length];
		for (int i = 0; i < addressLabels.length; i += 2) {
			int row = i / 2;
			for (int j = 0; j < 2; j++) {
				int index = i + j;
				if (index >= addressLabels.length) break;

				int col = j*2;

				gbc.gridwidth =  j == 0 ? 2 : 1;
				gbc.gridx = col;
				gbc.gridy = 6 + row * 2;
				gbc.anchor = GridBagConstraints.WEST;
				JLabel addressLabel = new JLabel(addressLabels[index]);
				addressLabel.setFont(new Font("Segoe UI", 0, 16));
				inforApplicantPanel.add(addressLabel, gbc);

				gbc.gridx = col;
				gbc.gridy = 6 + row * 2 + 1;
				addressFields[index] = new JTextField(20);
				addressFields[index].setFont(new Font("Segoe UI", 0, 16));
				inforApplicantPanel.add(addressFields[index], gbc);
			}
		}

		gbc.gridx=2; gbc.gridy=4; gbc.gridwidth = 1;
		emailLabel=new JLabel("Email"); emailLabel.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(emailLabel,gbc);
		gbc.gridx=2; gbc.gridy=5;
		emailText=new JTextField(20); emailText.setFont(new Font("Segoe UI",0,16));
		inforApplicantPanel.add(emailText,gbc);

		add(inforApplicantPanel, BorderLayout.CENTER);

//		Button
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 35));

		btnAdd=new Button("Thêm mới"); btnAdd.setFont(new Font("Segoe UI",0,16));
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

	public void loadDataApplicant() {
		idText.setText(String.valueOf(applicant.getId()));
		nameText.setText(applicant.getName());
		phoneText.setText(applicant.getContact().getPhone());
		addressFields[0].setText(applicant.getAddress().getCity());
		addressFields[1].setText(applicant.getAddress().getCountry());
		addressFields[2].setText(applicant.getAddress().getDistrict());
		addressFields[3].setText(applicant.getAddress().getNumber());
		addressFields[4].setText(applicant.getAddress().getStreet());
		addressFields[5].setText(applicant.getAddress().getWard());
		emailText.setText(applicant.getContact().getEmail());
		modelDate.setValue(Date.from(applicant.getDateOfBirth().atStartOfDay(ZoneId.systemDefault()).toInstant()));

		for(int i=0;i< genderText.getItemCount();i++) {
			if(genderText.getItemAt(i).toString().equals(applicant.getGender().getValue())) {
				genderText.setSelectedIndex(i);
				break;
			}
		}
	}

	public void addUpdate () {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

		String name = nameText.getText().trim();
		String phone = phoneText.getText().trim();
		String city = addressFields[0].getText().trim();
		String country = addressFields[1].getText().trim();
		String district = addressFields[2].getText().trim();
		String number = addressFields[3].getText().trim();
		String street = addressFields[4].getText().trim();
		String ward = addressFields[5].getText().trim();
		String birthDate = format.format(modelDate.getValue());
		String genderStr = (String) genderText.getSelectedItem();
		String email=emailText.getText();

		if (name.isEmpty() || phone.isEmpty() || city.isEmpty() || country.isEmpty()
				|| district.isEmpty() || number.isEmpty()
				|| street.isEmpty() || ward.isEmpty() || email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin");
		} else {
			var check=new FilterImp();
			try {

				if(check.checkName(name) && check.checkPhone(phone)
						&& check.checkBirthday(LocalDate.parse(birthDate))
						&& check.checkUserEmail(email)) {

					applicant.setName(name);
					applicant.setContact(new Contact(phone, email));
					Address address = new Address(number, street, ward, district, city, country);
					applicant.setAddress(address);
					applicant.setDateOfBirth(LocalDate.parse(birthDate));
					for (Gender g : Gender.values()) {
						if (g.getValue().equalsIgnoreCase(genderStr)) {
							applicant.setGender(g);
							break;
						}
					}

					confirmed = true;
					dispose();
				}

			} catch (checkName | checkPhone | checkBirthday | checkUserEmail e){
				JOptionPane.showMessageDialog(rootPane, e.getMessage());
			}
		}
	}

	public void reset () {
		nameText.setText("");
		phoneText.setText("");
		addressFields[0].setText("");
		addressFields[1].setText("");
		addressFields[2].setText("");
		addressFields[3].setText("");
		addressFields[4].setText("");
		addressFields[5].setText("");
		genderText.setSelectedIndex(0);
		emailText.setText("");
		modelDate.setValue(new Date());

		confirmed = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnAdd)) {
			addUpdate();
		}
		else if(obj.equals(btnReset)) {
			reset();
		}
	}
}
