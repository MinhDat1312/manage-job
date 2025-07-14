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
import exception.checkBirthday;
import exception.checkDateOfWork;
import exception.checkName;
import exception.checkPhone;
import lombok.Getter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import util.DateFormatter;
import entity.Employee;
import entity.constant.Gender;
import entity.constant.Role;
import util.FilterImp;
import view.button.Button;
import view.panel.GradientPanel;
import view.frame.imp.EmployeeFrameImp;

public class CreateUpdateEmployeeDialog extends JDialog implements ActionListener{

	@Getter
	private Employee employee;
	@Getter
	private boolean confirmed;
	private EmployeeFrameImp parent;

	GradientPanel inforEmployeePanel, btnPanel;
	JLabel idLabel, nameLabel, phoneLabel, dateLabel, genderLabel, addressLabel, dateWorkLabel, roleLabel;
	JTextField idText, nameText, phoneText;
	JTextField[] addressFields;
	JComboBox genderText, roleText;
	UtilDateModel modelDate, modelDateWork;
	JDatePickerImpl dateText, dateWorkText;
	Button btnAdd, btnReset;
	GridBagConstraints gbc;

	public CreateUpdateEmployeeDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Thêm mới nhân viên");
		setResizable(false);
		setSize(700,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.employee = new Employee();
		this.parent=(EmployeeFrameImp) parent;
		this.confirmed = false;

		initComponentAdd();
		addActionListener();
	}

	public CreateUpdateEmployeeDialog(Frame parent, boolean modal, Employee employee) {
		this(parent, modal);
		this.employee=employee;
		setTitle("Cập nhật nhân viên");

		initComponentUpdate();
		loadDataEmployee();
	}

	public void initComponentAdd() {
		inforEmployeePanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		inforEmployeePanel.setLayout(new GridBagLayout());
		inforEmployeePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		gbc= new GridBagConstraints();

		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.WEST;
		idLabel=new JLabel("Mã nhân viên"); idLabel.setFont(new Font("Segoe UI",0,16));
		inforEmployeePanel.add(idLabel, gbc);
		gbc.gridx=0; gbc.gridy=1;
		idText=new JTextField(10); idText.setFont(new Font("Segoe UI",0,16));
		idText.setEditable(false);
		inforEmployeePanel.add(idText, gbc);

		gbc.gridx=0; gbc.gridy=2; gbc.gridwidth = 2;
		nameLabel=new JLabel("Họ tên"); nameLabel.setFont(new Font("Segoe UI",0,16));
		inforEmployeePanel.add(nameLabel, gbc);
		gbc.gridx=0; gbc.gridy=3;
		nameText=new JTextField(20); nameText.setFont(new Font("Segoe UI",0,16));
		inforEmployeePanel.add(nameText, gbc);

		gbc.gridx=2; gbc.gridy=0;
		gbc.gridwidth = 1;
		dateLabel=new JLabel("Ngày sinh"); dateLabel.setFont(new Font("Segoe UI",0,16));
		inforEmployeePanel.add(dateLabel, gbc);
		gbc.gridx=2; gbc.gridy=1;
		modelDate=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelDate, p);
		dateText=new JDatePickerImpl(panelDate, new DateFormatter());
		dateText.setPreferredSize(new Dimension(150,25));
		modelDate.setValue(new Date());
		inforEmployeePanel.add(dateText, gbc);

		gbc.gridx=0; gbc.gridy=4; gbc.gridwidth = 2;
		phoneLabel=new JLabel("Số điện thoại"); phoneLabel.setFont(new Font("Segoe UI",0,16));
		inforEmployeePanel.add(phoneLabel, gbc);
		gbc.gridx=0; gbc.gridy=5;
		phoneText=new JTextField(20); phoneText.setFont(new Font("Segoe UI",0,16));
		inforEmployeePanel.add(phoneText, gbc);

		gbc.gridx=2; gbc.gridy=2; gbc.gridwidth = 1;
		genderLabel=new JLabel("Giới tính"); genderLabel.setFont(new Font("Segoe UI",0,16));
		inforEmployeePanel.add(genderLabel, gbc);
		gbc.gridx=2; gbc.gridy=3;
		genderText=new JComboBox(); genderText.setFont(new Font("Segoe UI",0,16));
		Gender[] gioitinhs= Gender.class.getEnumConstants();
		for(Gender g: gioitinhs) {
			genderText.addItem(g.getValue());
		}
		genderText.setPreferredSize(new Dimension(150,25));
		inforEmployeePanel.add(genderText, gbc);

		gbc.gridx=2; gbc.gridy=4; gbc.gridwidth = 1;
		dateWorkLabel=new JLabel("Ngày vào làm"); dateWorkLabel.setFont(new Font("Segoe UI",0,16));
		inforEmployeePanel.add(dateWorkLabel,gbc);
		gbc.gridx=2; gbc.gridy=5;
		modelDateWork=new UtilDateModel();
		JDatePanelImpl panelDateWork=new JDatePanelImpl(modelDateWork, p);
		dateWorkText=new JDatePickerImpl(panelDateWork, new DateFormatter());
		dateWorkText.setPreferredSize(new Dimension(150,25));
		modelDateWork.setValue(new Date());
		inforEmployeePanel.add(dateWorkText,gbc);

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
				inforEmployeePanel.add(addressLabel, gbc);

				gbc.gridx = col;
				gbc.gridy = 6 + row * 2 + 1;
				addressFields[index] = new JTextField(20);
				addressFields[index].setFont(new Font("Segoe UI", 0, 16));
				inforEmployeePanel.add(addressFields[index], gbc);
			}
		}
		add(inforEmployeePanel, BorderLayout.CENTER);

		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
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

	public void initComponentUpdate() {
		gbc.gridx=1; gbc.gridy=0;
		roleLabel=new JLabel("Vai trò"); roleLabel.setFont(new Font("Segoe UI",0,16));
		inforEmployeePanel.add(roleLabel, gbc);
		gbc.gridx=1; gbc.gridy=1;
		roleText=new JComboBox(); roleText.setFont(new Font("Segoe UI",0,16));
		Role[] roles= Role.class.getEnumConstants();
		for(Role v: roles) {
			roleText.addItem(v.getValue());
		}
		roleText.setPreferredSize(new Dimension(120,25));
		inforEmployeePanel.add(roleText, gbc);

		btnAdd.setText("Cập nhật");
	}

	public void addActionListener() {
		btnAdd.addActionListener(this);
		btnReset.addActionListener(this);
	}

	public void loadDataEmployee() {
		idText.setText(String.valueOf(employee.getId()));
		nameText.setText(employee.getName());
		phoneText.setText(employee.getContact().getPhone());
		addressFields[0].setText(employee.getAddress().getCity());
		addressFields[1].setText(employee.getAddress().getCountry());
		addressFields[2].setText(employee.getAddress().getDistrict());
		addressFields[3].setText(employee.getAddress().getNumber());
		addressFields[4].setText(employee.getAddress().getStreet());
		addressFields[5].setText(employee.getAddress().getWard());
		modelDate.setValue(Date.from(employee.getDateOfBirth().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		modelDateWork.setValue(Date.from(employee.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));

		for(int i=0;i< genderText.getItemCount();i++) {
			if(genderText.getItemAt(i).toString().equals(employee.getGender().getValue())) {
				genderText.setSelectedIndex(i);
				break;
			}
		}

		Role role = null;
		if(employee.getAccount() != null){
			for(Role r: Role.class.getEnumConstants()) {
				if(r.toString().equalsIgnoreCase(employee.getAccount().getRole().toString())) {
					role = r;
				}
			}
		} else {
			role = Role.NONE;
		}
		for(int i=0;i<roleText.getItemCount();i++) {
			if(roleText.getItemAt(i).toString().equalsIgnoreCase(role.getValue())) {
				roleText.setSelectedIndex(i);
				break;
			}
		}
		roleText.setEnabled(false);
		roleText.setFont(new Font("Segoe UI",1,16));
	}

	public void addUpdate(){
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
		String workDate = format.format(modelDateWork.getValue());
		String genderStr = (String) genderText.getSelectedItem();

		if (name.isEmpty() || phone.isEmpty() || city.isEmpty() || country.isEmpty()
				|| district.isEmpty() || number.isEmpty()
				|| street.isEmpty() || ward.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin");
		} else {
			var check=new FilterImp();
			try {

				if(check.checkName(name) && check.checkPhone(phone)
						&& check.checkBirthday(LocalDate.parse(birthDate))
						&& check.checkDateOfWork(LocalDate.parse(workDate))) {

					employee.setName(name);
					employee.setContact(new Contact(phone, ""));
					Address address = new Address(number, street, ward, district, city, country);
					employee.setAddress(address);
					employee.setDateOfBirth(LocalDate.parse(birthDate));
					employee.setStartDate(LocalDate.parse(workDate));
					for (Gender g : Gender.values()) {
						if (g.getValue().equalsIgnoreCase(genderStr)) {
							employee.setGender(g);
							break;
						}
					}

					confirmed = true;
					dispose();
				}

			} catch (checkName | checkPhone | checkBirthday | checkDateOfWork e){
				JOptionPane.showMessageDialog(rootPane, e.getMessage());
			}
		}
	}

	public void reset(){
		nameText.setText("");
		phoneText.setText("");
		addressFields[0].setText("");
		addressFields[1].setText("");
		addressFields[2].setText("");
		addressFields[3].setText("");
		addressFields[4].setText("");
		addressFields[5].setText("");
		genderText.setSelectedIndex(0);
		modelDate.setValue(new Date());
		modelDateWork.setValue(new Date());

		confirmed = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnAdd)) {
			addUpdate();
		}
		else if(obj.equals(btnReset)) {
			reset();
		}
	}
}
