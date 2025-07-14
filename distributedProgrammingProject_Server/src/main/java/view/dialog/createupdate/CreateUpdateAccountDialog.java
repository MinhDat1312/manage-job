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
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;

import entity.constant.Role;
import exception.checkUserEmail;
import exception.checkUserPass;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import lombok.Getter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import util.DateFormatter;
import dao.AccountDAO;
import entity.Employee;
import entity.Account;
import entity.constant.Gender;
import util.FilterImp;
import view.button.Button;
import view.panel.GradientPanel;

public class CreateUpdateAccountDialog extends JDialog implements ActionListener{

	@Getter
	private Employee employee;
	@Getter
	private Account account;
	@Getter
	private boolean confirmed;
	private Frame parent;

	GradientPanel inforAccountPanel, btnPanel;
	JLabel idLabel, nameLabel, phoneLabel, dateLabel, genderLabel, addressLabel, dateWorkLabel, roleLabel,
			usernameLabel, passwordLabel;
	JTextField idText, nameText, phoneText, addressText, usernameText, passwordText;
	JComboBox genderText, roleText;
	UtilDateModel modelDate, modelDateWork;
	JDatePickerImpl dateText, dateWorkText;
	Button btnAdd, btnReset;
	GridBagConstraints gbc;

	public CreateUpdateAccountDialog(Frame parent, boolean modal, Employee employee) {
		super(parent, modal);
		setTitle("Cấp tài khoản nhân viên");
		setResizable(false);
		setSize(750,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		this.employee=employee;
		this.parent=parent;
		this.account=new Account();
		this.confirmed=false;

		initComponent();
		addActionListener();
		loadDataEmployee();
	}

	public CreateUpdateAccountDialog(Frame parent, boolean modal, Employee employee, Account account) {
		this(parent, modal, employee);
		setTitle("Cập nhật tài khoản nhân viên");
		btnAdd.setText("Cập nhật");

		this.account=account;

		loadDataAccount();
	}

	public void initComponent() {
		inforAccountPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		inforAccountPanel.setBackground(Color.WHITE);
		inforAccountPanel.setLayout(new GridBagLayout());
		inforAccountPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		gbc= new GridBagConstraints();

//		Thông tin nhân viên
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.WEST;
		idLabel=new JLabel("Mã tài khoản"); idLabel.setFont(new Font("Segoe UI",0,16));
		inforAccountPanel.add(idLabel, gbc);
		gbc.gridx=0; gbc.gridy=1;
		idText=new JTextField(10); idText.setFont(new Font("Segoe UI",0,16));
		idText.setEditable(false);
		inforAccountPanel.add(idText, gbc);

		gbc.gridx=1; gbc.gridy=0;
		roleLabel =new JLabel("Vai trò"); roleLabel.setFont(new Font("Segoe UI",0,16));
		inforAccountPanel.add(roleLabel, gbc);
		gbc.gridx=1; gbc.gridy=1;
		roleText =new JComboBox(); roleText.setFont(new Font("Segoe UI",0,16));
		Role[] roles= Role.class.getEnumConstants();
		for(Role v: roles) {
			roleText.addItem(v.getValue());
		}
		roleText.setPreferredSize(new Dimension(150,25));
		inforAccountPanel.add(roleText, gbc);

		gbc.gridx=2; gbc.gridy=0;
		nameLabel =new JLabel("Họ tên nhân viên"); nameLabel.setFont(new Font("Segoe UI",0,16));
		inforAccountPanel.add(nameLabel, gbc);
		gbc.gridx=2; gbc.gridy=1;
		nameText =new JTextField(20); nameText.setFont(new Font("Segoe UI",0,16));
		nameText.setEditable(false);
		inforAccountPanel.add(nameText, gbc);

		gbc.gridx=0; gbc.gridy=2;
		dateLabel=new JLabel("Ngày sinh"); dateLabel.setFont(new Font("Segoe UI",0,16));
		inforAccountPanel.add(dateLabel, gbc);
		gbc.gridx=0; gbc.gridy=3;
		modelDate=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelDate, p);
		dateText=new JDatePickerImpl(panelDate, new DateFormatter());
		dateText.setPreferredSize(new Dimension(150,25));
		dateText.getComponent(1).setEnabled(false);
		modelDate.setValue(new Date());
		inforAccountPanel.add(dateText, gbc);

		gbc.gridx=1; gbc.gridy=2;
		genderLabel =new JLabel("Giới tính"); genderLabel.setFont(new Font("Segoe UI",0,16));
		inforAccountPanel.add(genderLabel, gbc);
		gbc.gridx=1; gbc.gridy=3;
		genderText =new JComboBox(); genderText.setFont(new Font("Segoe UI",0,16));
		Gender[] gioitinhs= Gender.class.getEnumConstants();
		for(Gender g: gioitinhs) {
			genderText.addItem(g.getValue());
		}
		genderText.setPreferredSize(new Dimension(150,25));
		inforAccountPanel.add(genderText, gbc);

		gbc.gridx=2; gbc.gridy=2;
		phoneLabel =new JLabel("Số điện thoại"); phoneLabel.setFont(new Font("Segoe UI",0,16));
		inforAccountPanel.add(phoneLabel, gbc);
		gbc.gridx=2; gbc.gridy=3;
		phoneText =new JTextField(20); phoneText.setFont(new Font("Segoe UI",0,16));
		phoneText.setEditable(false);
		inforAccountPanel.add(phoneText, gbc);

		gbc.gridx=0; gbc.gridy=4;
		addressLabel =new JLabel("Địa chỉ"); addressLabel.setFont(new Font("Segoe UI",0,16));
		inforAccountPanel.add(addressLabel, gbc);
		gbc.gridx=0; gbc.gridy=5; gbc.gridwidth=2;
		addressText =new JTextField(23); addressText.setFont(new Font("Segoe UI",0,16));
		addressText.setEditable(false);
		inforAccountPanel.add(addressText, gbc);

		gbc.gridx=2; gbc.gridy=4; gbc.gridwidth=1;
		dateWorkLabel=new JLabel("Ngày vào làm"); dateWorkLabel.setFont(new Font("Segoe UI",0,16));
		inforAccountPanel.add(dateWorkLabel,gbc);
		gbc.gridx=2; gbc.gridy=5;
		modelDateWork=new UtilDateModel();
		JDatePanelImpl panelDateWork=new JDatePanelImpl(modelDateWork, p);
		dateWorkText=new JDatePickerImpl(panelDateWork, new DateFormatter());
		dateWorkText.setPreferredSize(new Dimension(150,25));
		dateWorkText.getComponent(1).setEnabled(false);
		modelDateWork.setValue(new Date());
		inforAccountPanel.add(dateWorkText,gbc);

		gbc.gridx=0; gbc.gridy=6;
		usernameLabel =new JLabel("Tên đăng nhập"); usernameLabel.setFont(new Font("Segoe UI",0,16));
		inforAccountPanel.add(usernameLabel, gbc);
		gbc.gridx=0; gbc.gridy=7; gbc.gridwidth=2;
		usernameText =new JTextField(23); usernameText.setFont(new Font("Segoe UI",0,16));
		inforAccountPanel.add(usernameText, gbc);

		gbc.gridx=2; gbc.gridy=6; gbc.gridwidth=1;
		passwordLabel =new JLabel("Mật khẩu"); passwordLabel.setFont(new Font("Segoe UI",0,16));
		inforAccountPanel.add(passwordLabel, gbc);
		gbc.gridx=2; gbc.gridy=7;
		passwordText =new JTextField(20); passwordText.setFont(new Font("Segoe UI",0,16));
		inforAccountPanel.add(passwordText, gbc);

		add(inforAccountPanel, BorderLayout.CENTER);

//		Button
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 47));

		btnAdd =new Button("Cấp tài khoản"); btnAdd.setFont(new Font("Segoe UI",0,16));
		btnAdd.setPreferredSize(new Dimension(135,25));
		btnAdd.setBackground(new Color(0,102,102));
		btnAdd.setForeground(Color.WHITE);

		btnReset =new Button("Hủy"); btnReset.setFont(new Font("Segoe UI",0,16));
		btnReset.setPreferredSize(new Dimension(135,25));
		btnReset.setBackground(Color.WHITE);
		btnReset.setForeground(Color.BLACK);

		btnPanel.add(btnAdd); btnPanel.add(btnReset);

		add(btnPanel, BorderLayout.SOUTH);
	}

	public void loadDataEmployee() {
		nameText.setText(employee.getName());
		modelDate.setValue(Date.from(employee.getDateOfBirth().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		modelDateWork.setValue(Date.from(employee.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));

		for(int i=0;i< genderText.getItemCount();i++) {
			if(genderText.getItemAt(i).toString().equals(employee.getGender().getValue())) {
				genderText.setSelectedIndex(i);
				break;
			}
		}

		phoneText.setText(employee.getContact().getPhone());
		addressText.setText(employee.getAddress().getNumber() + " " + employee.getAddress().getStreet()
				+ " " + employee.getAddress().getCity());
	}

	public void loadDataAccount() {
		idText.setText(String.valueOf(account.getId()));
		usernameText.setText(account.getEmail());
		passwordText.setText(account.getPassword());

		for(int i=0;i<roleText.getItemCount();i++) {
			if(roleText.getItemAt(i).toString().equalsIgnoreCase(account.getRole().getValue())) {
				roleText.setSelectedIndex(i);
				break;
			}
		}
	}

	public void addUpdate(){
		String id=idText.getText();
		String username=usernameText.getText();
		String password=passwordText.getText();
		String role=roleText.getSelectedItem().toString();

		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin tài khoản");
		} else {
			var check=new FilterImp();
			try{
				if(check.checkUserEmail(username) && check.checkUserPass(password)){
					account.setEmail(username);
					account.setPassword(password);
					for (Role r : Role.values()) {
						if (r.getValue().equalsIgnoreCase(role)) {
							account.setRole(r);
							break;
						}
					}
					account.setEmployee(employee);
					employee.setAccount(account);
					confirmed = true;
					dispose();
				}
			} catch (checkUserEmail | checkUserPass e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(rootPane, e.getMessage());
			}
		}
	}

	public void reset(){
		roleText.setSelectedIndex(0);
		usernameText.setText("");
		passwordText.setText("");
		confirmed = false;
	}

	public void addActionListener() {
		btnAdd.addActionListener(this);
		btnReset.addActionListener(this);
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
